package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MarsDailyWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String terrestrialDate;
    private String sol;
    private String ls;
    private String season;
    private String min_temp;
    private String max_temp;
    private String sunset;
    private String sunrise;
    private String min_gts_temp;
    private String max_gts_temp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarsDailyWeather that = (MarsDailyWeather) o;
        return Objects.equals(terrestrialDate, that.terrestrialDate) && Objects.equals(sol, that.sol) && Objects.equals(ls, that.ls) && Objects.equals(season, that.season) && Objects.equals(min_temp, that.min_temp) && Objects.equals(max_temp, that.max_temp) && Objects.equals(sunset, that.sunset) && Objects.equals(sunrise, that.sunrise) && Objects.equals(min_gts_temp, that.min_gts_temp) && Objects.equals(max_gts_temp, that.max_gts_temp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terrestrialDate, sol, ls, season, min_temp, max_temp, sunset, sunrise, min_gts_temp, max_gts_temp);
    }
}
