package com.example.demo.domain;

import com.example.demo.dto.MarsDailyWeatherDto;
import com.example.demo.model.MarsDailyWeather;
import org.springframework.stereotype.Service;

@Service
public class MarsMapper {

    MarsDailyWeather toEntityFromDto(MarsDailyWeatherDto source){
        return new MarsDailyWeather(
                null, // ID encji jest generowane przez bazę danych
                source.terrestrialDate(),
                source.sol(),
                source.ls(),
                source.season(),
                source.min_temp(),
                source.max_temp(),
                source.sunset(),
                source.sunrise(),
                source.min_gts_temp(),
                source.max_gts_temp()
        );
    }
}
