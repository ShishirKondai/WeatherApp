package com.example.OpenWeather;

import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeweather.R;

public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {
    TextView hourlyDay, hourlyTime, hourlyTemperature, hourlyDescription;
    ImageView hourlyIcon;

    public HourlyWeatherViewHolder(@NonNull View itemView, TextView hourlyDay, TextView hourlyTime, ImageView hourlyIcon, TextView hourlyTemperature, TextView hourlyDescription) {
        super(itemView);
        this.hourlyDay = hourlyDay;
        this.hourlyTime = hourlyTime;
        this.hourlyTemperature = hourlyTemperature;
        this.hourlyDescription = hourlyDescription;
        this.hourlyIcon = hourlyIcon;
    }

    public HourlyWeatherViewHolder(View view) {
        super(view);
        hourlyDay = view.findViewById(R.id.day);
        hourlyTime = view.findViewById(R.id.time_am_pm);
        hourlyTemperature = view.findViewById(R.id.temperature_hourly);
        hourlyDescription = view.findViewById(R.id.description_weather);
        hourlyIcon = view.findViewById(R.id.icon_hourly);
    }
}

