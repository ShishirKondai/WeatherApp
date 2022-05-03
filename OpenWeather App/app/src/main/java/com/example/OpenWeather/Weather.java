package com.example.OpenWeather;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.*;

class Weather implements Serializable {
    private Double latitude, longitude;
    private String timeZone;
    private Long timeZoneOffset;
    private CurrentWeather current;
    private List<HourlyWeather> hourlyList;
    private List<DailyWeather> dailyForecastList;
    private Bitmap bitmap;

    public Weather(Double latitude, Double longitude, String timeZone, Long timeZoneOffset, CurrentWeather current, List<HourlyWeather> hourlyList, List<DailyWeather> dailyForecastList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
        this.timeZoneOffset = timeZoneOffset;
        this.current = current;
        this.hourlyList = hourlyList;
        this.dailyForecastList = dailyForecastList;
        this.bitmap = bitmap;
    }
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Long getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public List<HourlyWeather> getHourlyList() {
        return hourlyList;
    }

    public List<DailyWeather> getDailyList() {
        return dailyForecastList;
    }

    public void setDailyList(List<DailyWeather> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }

}
