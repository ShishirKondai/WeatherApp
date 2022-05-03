package com.example.OpenWeather;

import java.io.Serializable;

public enum UnitType implements Serializable {
    IMPERIAL("imperial"),
    METRIC("metric");

    public String getUnit() {
        return this.unit;
    }

    String unit;
    UnitType(String unit) {
        this.unit = unit;
    }

    public static String formatUnit(String unit) {
        return unit.equalsIgnoreCase(METRIC.getUnit()) ? "°C" : "°F";
    }

    public static UnitType getEnum(String unit) {
        if(unit.equalsIgnoreCase(IMPERIAL.toString())) {
            return IMPERIAL;
        } else {
            return METRIC;
        }
    }

    public static String formatUnit(UnitType unit) {
        return unit.equals(METRIC) ? "°C" : "°F";
    }

}
