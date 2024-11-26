package com.example.demo.repo;

import com.example.demo.model.RefreshTimeInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DataRefreshRepo extends CrudRepository<RefreshTimeInfo, Long> {
    Optional<RefreshTimeInfo> findFirstByOrderByLastRefreshTimeDesc();
}
