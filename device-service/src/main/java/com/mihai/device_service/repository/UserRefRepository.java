package com.mihai.device_service.repository;

import com.mihai.device_service.entity.UserRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRefRepository extends JpaRepository<UserRef, UUID> {
}
