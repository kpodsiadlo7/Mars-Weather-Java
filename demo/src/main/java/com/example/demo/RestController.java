package com.example.demo;

import com.example.demo.domain.MarsMapper;
import com.example.demo.domain.MarsService;
import com.example.demo.dto.MarsDailyWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final MarsService marsService;
    private final MarsMapper marsMapper;

    @GetMapping
    List<MarsDailyWeatherDto> getAllWeatherDetails() {
        return marsMapper.toDtoListFromEntityList(marsService.getWeatherDetailsFromDb());
    }
}
