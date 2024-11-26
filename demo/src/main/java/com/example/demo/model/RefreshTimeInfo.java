package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class RefreshTimeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final LocalDateTime lastRefreshTime;

    public RefreshTimeInfo() {
        this.lastRefreshTime = LocalDateTime.now();
    }
}
