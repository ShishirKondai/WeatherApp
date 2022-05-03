package com.example.OpenWeather;

import java.io.Serializable;
import java.util.*;

public class CurrentWeather implements Serializable {
    private Integer temp, feelLike, humidity, uv, cloud, pressure, dewPoint;
    private Double snow, rain, deg, gust, speed;
    private Long visibility;
    private Date date, sunriseTime, sunsetTime;
    private List<WeatherDetails> weatherDetailsList;

    public CurrentWeather(Date date, Date sunriseTime, Date sunsetTime, Integer temp, Integer feelLike,
                          Integer pressure, Integer humidity, Integer dewPoint, Integer uv,
                          Integer cloud, Long visibility, Double speed, Double deg,
                          Double gust, Double rain, Double snow,
                          List<WeatherDetails> weatherDetailsList) {
        this.temp = temp;
        this.feelLike = feelLike;
        this.pressure = pressure;
        this.deg = deg;
        this.gust = gust;
        this.rain = rain;
        this.snow = snow;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uv = uv;
        this.cloud = cloud;
        this.visibility = visibility;
        this.speed = speed;
        this.date = date;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.weatherDetailsList = weatherDetailsList;
    }

    public Integer getTemp() {
        return temp;
    }

    public Integer getFeelLike() {
        return feelLike;
    }

    public Double getDegrees() {
        return deg;
    }

    public Double getGust() {
        return gust;
    }

    public Date getSunriseTime() {
        return sunriseTime;
    }

    public Date getSunsetTime() {
        return sunsetTime;
    }

    public Double getRain() {
        return rain;
    }

    public Double getSnow() {
        return snow;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Integer getUv() {
        return uv;
    }

    public Integer getCloud() {
        return cloud;
    }

    public Long getVisibility() {
        return visibility;
    }

    public Double getSpeed() {
        return speed;
    }

    public Date getDate() {
        return date;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherDetailsList;
    }
}