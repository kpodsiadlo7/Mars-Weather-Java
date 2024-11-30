package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MarsDailyWeatherDto(@JsonProperty("terrestrial_date") String terrestrialDate, String sol, String ls, String season, String min_temp,
                                  String max_temp, String pressure, String pressure_string, String atmo_opacity,
                                  String sunrise, String sunset, String min_gts_temp, String max_gts_temp) {
}
