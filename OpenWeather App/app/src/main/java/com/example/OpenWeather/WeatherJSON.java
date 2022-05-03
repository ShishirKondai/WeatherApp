package com.example.OpenWeather;

import java.util.*;
import org.json.*;

public class WeatherJSON {

    private static Weather weatherObj;

    public static Weather parseJSON(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);

            double lat = jsonObject.getDouble("lat");
            double lon = jsonObject.getDouble("lon");

            String timezone = jsonObject.getString("timezone");
            long timezoneOffset = jsonObject.getLong("timezone_offset");
            CurrentWeather current = null;
            List<HourlyWeather> hourly = new ArrayList<>();
            List<DailyWeather> dailyForecast = new ArrayList<>();

            if (jsonObject.has("current")) {
                JSONObject currentObj = jsonObject.getJSONObject("current");
                JSONArray currentWeatherJsonArray = currentObj.getJSONArray("weather");

                List<WeatherDetails> currentWeatherInfo = new ArrayList<>();
                if (currentWeatherJsonArray.length() > 0) {
                    JSONObject currentWeather = currentWeatherJsonArray.getJSONObject(0);
                    WeatherDetails weatherInfo = new WeatherDetails(
                            currentWeather.getLong("id"),
                            currentWeather.getString("main"),
                            currentWeather.getString("description"),
                            currentWeather.getString("icon")
                    );
                    currentWeatherInfo.add(weatherInfo);
                }

                Double gust = null;
                if(currentObj.has("wind_gust")) {
                    gust = currentObj.getDouble("wind_gust");
                }

                Double rain = null;
                if(currentObj.has("rain")) {
                    JSONObject rainObject = currentObj.getJSONObject("rain");
                    rain = rainObject.getDouble("1h");
                }

                Double snow = null;
                if(currentObj.has("snow")) {
                    JSONObject snowObject = currentObj.getJSONObject("snow");
                    snow = snowObject.getDouble("1h");
                }

                current = new CurrentWeather(
                        new Date(currentObj.getLong("dt")),
                        new Date(currentObj.getLong("sunrise")),
                        new Date(currentObj.getLong("sunset")),
                        currentObj.getInt("temp"),
                        currentObj.getInt("feels_like"),
                        currentObj.getInt("pressure"),
                        currentObj.getInt("humidity"),
                        currentObj.getInt("dew_point"),
                        currentObj.getInt("uvi"),
                        currentObj.getInt("clouds"),
                        currentObj.getLong("visibility"),
                        currentObj.getDouble("wind_speed"),
                        currentObj.getDouble("wind_deg"),
                        gust,
                        rain,
                        snow,
                        currentWeatherInfo
                );
            }

            if (jsonObject.has("daily")) {
                JSONArray dailyJsonArray = jsonObject.getJSONArray("daily");

                for (int i = 0; i < dailyJsonArray.length(); i++) {
                    JSONObject jo = dailyJsonArray.getJSONObject(i);
                    JSONArray dailyWeatherJsonArray = jo.getJSONArray("weather");

                    List<WeatherDetails> dailyWeatherInfo = new ArrayList<>();
                    if (dailyWeatherJsonArray.length() > 0) {
                        JSONObject dailyWeather = dailyWeatherJsonArray.getJSONObject(0);
                        WeatherDetails weatherInfo = new WeatherDetails(
                                dailyWeather.getLong("id"),
                                dailyWeather.getString("main"),
                                dailyWeather.getString("description"),
                                dailyWeather.getString("icon")
                        );
                        dailyWeatherInfo.add(weatherInfo);
                    }

                    TemperatureTime temperature = null;
                    if (jo.has("temp")) {
                        JSONObject j = jo.getJSONObject("temp");
                        temperature = new TemperatureTime(
                                j.getInt("day"),
                                j.getInt("min"),
                                j.getInt("max"),
                                j.getInt("night"),
                                j.getInt("eve"),
                                j.getInt("morn")
                        );
                    }
                    DailyWeather d = new DailyWeather(
                            new Date(jo.getLong("dt")),
                            jo.getInt("pop"),
                            jo.getInt("uvi"),
                            temperature,
                            dailyWeatherInfo
                    );
                    dailyForecast.add(i, d);
                }
            }
            if (jsonObject.has("hourly")) {
                JSONArray hourlyJsonArray = jsonObject.getJSONArray("hourly");

                for (int i = 0; i < hourlyJsonArray.length(); i++) {
                    JSONObject jo = hourlyJsonArray.getJSONObject(i);
                    JSONArray hourlyWeatherJsonArray = jo.getJSONArray("weather");

                    List<WeatherDetails> hourlyWeatherInfo = new ArrayList<>();
                    if (hourlyWeatherJsonArray.length() > 0) {
                        JSONObject hourlyWeather = hourlyWeatherJsonArray.getJSONObject(0);
                        WeatherDetails weatherInfo = new WeatherDetails(
                                hourlyWeather.getLong("id"),
                                hourlyWeather.getString("main"),
                                hourlyWeather.getString("description"),
                                hourlyWeather.getString("icon")
                        );
                        hourlyWeatherInfo.add(weatherInfo);
                    }

                    HourlyWeather h = new HourlyWeather(
                            new Date(jo.getLong("dt")),
                            jo.getInt("temp"),
                            hourlyWeatherInfo,
                            jo.getDouble("pop")
                    );

                    hourly.add(i, h);
                }
            }
           return  weatherObj = new Weather(lat, lon, timezone, timezoneOffset, current, hourly, dailyForecast);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
