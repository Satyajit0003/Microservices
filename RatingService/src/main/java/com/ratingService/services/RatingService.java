package com.ratingService.services;

import com.ratingService.entity.Rating;

import java.util.List;

public interface RatingService {

    Rating createRating(Rating rating);

    Rating getRatingById(String ratingId);

    //get all by userId
    List<Rating> getRatingsByUserId(String userId);

    //get all by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);

    List<Rating> getRatings();
}
