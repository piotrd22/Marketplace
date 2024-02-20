package com.example.marketplace.services.user;

import com.example.marketplace.models.User;

public interface UserService {
    User addUser(User user);
    User getUserByUsername(String username);
}
