package com.example.demo;

import com.example.demo.dto.WeatherDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final NasaClient nasaClient;

    @GetMapping
    WeatherDetailsDto testGET() {
        return nasaClient.getWeatherDetails();
    }
}
