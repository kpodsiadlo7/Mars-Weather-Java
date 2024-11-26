package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
