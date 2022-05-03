package com.example.OpenWeather;

public enum TimeType {
    MORNING("8am"),
    DAY("1pm"),
    EVENING("5pm"),
    NIGHT("11pm");

    public String getTime() {
        return value;
    }

    String value;
    TimeType(String value) {
        this.value = value;
    }
}
