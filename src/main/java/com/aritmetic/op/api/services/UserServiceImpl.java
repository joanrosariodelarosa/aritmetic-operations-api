//package com.aritmetic.op.api.services;
//
//import com.aritmetic.op.api.repositories.UserRepository;
//import org.springframework.stereotype.Service;
//import com.aritmetic.op.api.models.User;
//
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserInterface {
//    private final UserRepository userRepository;
//
//    public UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//        public Optional<User> getUserDetails(String username, String password, User.Status status) {
//        return this.userRepository.findByUsernameAndPasswordAndStatus(username, password, status);
//    }
//}
