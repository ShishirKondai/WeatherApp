package com.example.OpenWeather;

import java.io.Serializable;

public class WeatherDetails implements Serializable {

    private String main, description, icon;
    private Long id;

    public WeatherDetails(Long id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
