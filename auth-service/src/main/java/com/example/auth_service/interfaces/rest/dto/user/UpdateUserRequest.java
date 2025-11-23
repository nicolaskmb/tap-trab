package com.example.auth_service.interfaces.rest.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(@NotBlank String name) {}