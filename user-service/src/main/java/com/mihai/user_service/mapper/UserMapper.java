package com.mihai.user_service.mapper;

import com.mihai.user_service.dto.UserRequest;
import com.mihai.user_service.dto.UserResponse;
import com.mihai.user_service.entity.User;

public final class UserMapper {

    private UserMapper() {}   // utility class, no instances

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFullName(),
                user.getEmail(), user.getAddress());
    }

    public static User toEntity(UserRequest req) {
        return User.builder()
                .username(req.username())
                .fullName(req.fullName())
                .email(req.email())
                .address(req.address())
                .build();
    }

    public static void updateEntity(User user, UserRequest req) {
        user.setUsername(req.username());
        user.setFullName(req.fullName());
        user.setEmail(req.email());
        user.setAddress(req.address());
    }
}
