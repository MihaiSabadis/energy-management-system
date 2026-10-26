package com.mihai.user_service.service;

import com.mihai.user_service.client.DeviceClient;
import com.mihai.user_service.dto.UserRequest;
import com.mihai.user_service.dto.UserResponse;
import com.mihai.user_service.entity.User;
import com.mihai.user_service.exception.ConflictException;
import com.mihai.user_service.exception.NotFoundException;
import com.mihai.user_service.mapper.UserMapper;
import com.mihai.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeviceClient deviceClient;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
    }

    public UserResponse findById(UUID id) {
        return UserMapper.toResponse(getOrThrow(id));
    }

    @Transactional
    public UserResponse create(UserRequest req) {
        if (userRepository.existsByUsername(req.username()))
            throw new ConflictException("Username already taken");
        if (userRepository.existsByEmail(req.email()))
            throw new ConflictException("Email already in use");

        User saved = userRepository.save(UserMapper.toEntity(req));
        deviceClient.notifyUserCreated(saved.getId());
        return UserMapper.toResponse(saved);
    }

    @Transactional
    public UserResponse update(UUID id, UserRequest req) {
        User user = getOrThrow(id);
        if (!user.getUsername().equals(req.username()) && userRepository.existsByUsername(req.username()))
            throw new ConflictException("Username already taken");
        if (!user.getEmail().equals(req.email()) && userRepository.existsByEmail(req.email()))
            throw new ConflictException("Email already in use");

        UserMapper.updateEntity(user, req);
        return UserMapper.toResponse(user);
    }

    @Transactional
    public void delete(UUID id) {
        User user = getOrThrow(id);
        deviceClient.notifyUserDeleted(id);
        userRepository.delete(user);
    }

    private User getOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User " + id + " not found"));
    }
}