package com.hotelService.service;

import com.hotelService.entity.Hotel;

import java.util.List;

public interface HotelService {

    Hotel createHotel(Hotel hotel);

    List<Hotel> getAllHotel();

    Hotel getHotel(String hotelId);
}
