package com.ecommerce.user.service;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.BadRequestException;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int userId) throws BadRequestException {
        return userRepository.findById(userId).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserById(int userId, User user) {
        User updateUser = userRepository.findById(userId).orElseThrow();
        updateUser.setUserName(user.getUserName());
        updateUser.setEmail(user.getEmail());
        updateUser.setEmail(user.getEmail());
        updateUser.setRole(user.getRole());
        return userRepository.save(updateUser);
    }


    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }
}
