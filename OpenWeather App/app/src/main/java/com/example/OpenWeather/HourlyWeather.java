package com.example.OpenWeather;

import java.io.Serializable;
import java.util.*;

public class HourlyWeather implements Serializable {

    private Integer temp;
    private Double pop;
    private Date date;
    private List<WeatherDetails> weatherList;

    public HourlyWeather(Date date, Integer temp, List<WeatherDetails> weatherDetailsList, Double pop) {
        this.date = date;
        this.temp = temp;
        this.weatherList = weatherDetailsList;
        this.pop = pop;
    }

    public Date getDate() {
        return date;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setWeatherDetailsList(List<WeatherDetails> list) {
        this.weatherList = list;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherList;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }
    public Double getPop() {
        return pop;
    }

}
