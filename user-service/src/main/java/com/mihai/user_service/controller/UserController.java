package com.mihai.user_service.controller;

import com.mihai.user_service.dto.UserRequest;
import com.mihai.user_service.dto.UserResponse;
import com.mihai.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "CRUD operations on user profiles")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "List all users")
    public List<UserResponse> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a user")
    public UserResponse create(@Valid @RequestBody UserRequest req) {
        return userService.create(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UserRequest req) {
        return userService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user and unassign their devices")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
