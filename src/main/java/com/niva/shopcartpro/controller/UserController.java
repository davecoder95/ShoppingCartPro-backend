package com.niva.shopcartpro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.niva.shopcartpro.dto.UserRequestDTO;
import com.niva.shopcartpro.dto.UserResponseDTO;
import com.niva.shopcartpro.mapper.UserMapper;
import com.niva.shopcartpro.model.User;
import com.niva.shopcartpro.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserRequestDTO request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        User savedUser = userService.createUser(user);

        return UserMapper.toDTO(savedUser);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        List<User> users = userService.getAllUsers();

        List<UserResponseDTO> response = new ArrayList<>();

        for (User user : users) {
            response.add(UserMapper.toDTO(user));
        }

        return response;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return UserMapper.toDTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}