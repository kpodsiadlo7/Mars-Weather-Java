package com.example.demo.repo;

import com.example.demo.model.MarsDailyWeather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarsWeatherRepo extends JpaRepository<MarsDailyWeather, Long> {
}
