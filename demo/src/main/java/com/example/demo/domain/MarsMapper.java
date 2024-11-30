package com.example.demo.domain;

import com.example.demo.dto.MarsDailyWeatherDto;
import com.example.demo.dto.WeatherDetailsDto;
import com.example.demo.model.MarsDailyWeather;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MarsDailyWeatherDto> toDtoListFromEntityList(List<MarsDailyWeather> weatherDetailsFromDb) {
        return weatherDetailsFromDb.stream().map(this::toDtoFromEntity).toList();
    }

    private MarsDailyWeatherDto toDtoFromEntity(MarsDailyWeather weatherDetails){
        return MarsDailyWeatherDto.builder()
                .terrestrialDate(weatherDetails.getTerrestrialDate())
                .sol(weatherDetails.getSol())
                .ls(weatherDetails.getLs())
                .season(weatherDetails.getSeason())
                .min_temp(weatherDetails.getMin_temp())
                .max_temp(weatherDetails.getMax_temp())
                .sunset(weatherDetails.getSunset())
                .sunrise(weatherDetails.getSunrise())
                .min_gts_temp(weatherDetails.getMin_gts_temp())
                .max_gts_temp(weatherDetails.getMax_gts_temp())
                .build();
    }
}
