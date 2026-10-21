package com.mihai.device_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String address;

    @Column(nullable = false)
    private Double maxConsumption;   // kWh

    private UUID userId;              // null = unassigned
}
