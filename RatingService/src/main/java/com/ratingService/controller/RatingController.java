package com.ratingService.controller;

import com.ratingService.entity.Rating;
import com.ratingService.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating rating1 = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getUserRatings(@PathVariable String userId) {
        List<Rating> ratingsByUserId = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.ok(ratingsByUserId);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getHotelRatings(@PathVariable String hotelId) {
        List<Rating> ratingsByHotelId = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.ok(ratingsByHotelId);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getRatings();
        return ResponseEntity.ok(ratings);
    }


}
