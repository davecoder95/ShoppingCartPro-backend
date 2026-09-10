package com.niva.shopcartpro.mapper;

import com.niva.shopcartpro.dto.UserResponseDTO;
import com.niva.shopcartpro.model.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
}