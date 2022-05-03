package com.example.OpenWeather;

import java.io.Serializable;

public class TemperatureTime implements Serializable {
    private Integer day, night, morning, evening;
    private Integer minimum, maximum;

    public TemperatureTime(Integer day, Integer min, Integer max, Integer night, Integer evening, Integer morning) {
        this.minimum = min;
        this.maximum = max;
        this.day = day;
        this.night = night;
        this.morning = morning;
        this.evening = evening;
    }
    public Integer getMinimum() {
        return minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getNight() {
        return night;
    }

    public Integer getMorning() {
        return morning;
    }

    public Integer getEvening() {
        return evening;
    }
}
