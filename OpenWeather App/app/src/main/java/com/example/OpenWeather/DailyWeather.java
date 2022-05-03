package com.example.OpenWeather;

import java.io.Serializable;
import java.util.*;

public class DailyWeather implements Serializable {

    private Integer pop, uvi;
    private Date date;
    private TemperatureTime temperature;
    private List<WeatherDetails> weatherList;

    public DailyWeather(Date dt, Integer pop, Integer uvi, TemperatureTime temp, List<WeatherDetails> wList) {
        this.date = dt;
        this.pop = pop;
        this.uvi = uvi;
        this.temperature = temp;
        this.weatherList = wList;
    }

    public Date getDt() {
        return date;
    }

    public Integer getPop() {
        return pop;
    }

    public Integer getUvi() {
        return uvi;
    }

    public TemperatureTime getTemperature() {
        return temperature;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherList;
    }

}
