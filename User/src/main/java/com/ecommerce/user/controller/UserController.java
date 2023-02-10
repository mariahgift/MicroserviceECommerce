package com.ecommerce.user.controller;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.BadRequestException;
import com.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/getUserById/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.findUserById(userId);
    }

    @GetMapping(path = "/getAllUsers")
    public List<User> findAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping(path = "/updateUser/{userId}")
    public User updateUserById(@PathVariable int userId, @RequestBody User user) {
        user.setUserId(userId);
        return userService.updateUserById(userId, user);
    }

    @DeleteMapping(path = "/deleteUser/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
    }
}
