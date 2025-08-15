package com.userService.service;

import com.userService.entity.Hotel;
import com.userService.entity.Rating;
import com.userService.entity.User;
import com.userService.exception.ResourceNotFoundException;
import com.userService.external.HotelService;
import com.userService.external.RatingService;
import com.userService.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final HotelService hotelService;
    private final RatingService ratingService;

    public UserServiceImpl(UserRepository userRepository, HotelService hotelService, RatingService ratingService) {
        this.userRepository = userRepository;
        this.hotelService = hotelService;
        this.ratingService = ratingService;
    }


    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUSers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("USer with given id is not found on server !! : " + userId));
        //fetch rating of the above user from rating service
        List<Rating> userRatings = ratingService.getUserRatings(userId);

        //fetch hotel details for each rating
        for(Rating rating: userRatings) {
            Hotel hotel = hotelService.getHotelById(rating.getHotelId());
            rating.setHotel(hotel);
        }
        user.setRatings(userRatings);
        return user;
    }
}
