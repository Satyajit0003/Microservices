package com.userService.service;

import com.userService.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUSers();

    User getUser(String userId);

}
