package com.userService.controller;

import com.userService.entity.User;
import com.userService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    int retryCount = 1;

    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "RatingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "RatingHotelRetry", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "UserRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        log.info("Retry count: {}", retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        //log.info("Fallback is executed because service is down: {}", ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created as a fallback response")
                .userId("123456")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUSers = userService.getAllUSers();
        return ResponseEntity.ok(allUSers);
    }
}
