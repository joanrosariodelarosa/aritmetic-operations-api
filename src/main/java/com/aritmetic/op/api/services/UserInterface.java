package com.aritmetic.op.api.services;

import com.aritmetic.op.api.models.User;

import java.util.Optional;

public interface UserInterface {
    Optional<User> getUserDetails(String username, String password, User.Status status);
}
