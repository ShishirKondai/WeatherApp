package com.example.OpenWeather;

import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeweather.R;

public class DailyWeatherViewHolder extends RecyclerView.ViewHolder {
    TextView dailyTime, dailyTempRange, dailyWeatherDescription, dailyPrecipitation, dailyUVIndex;
    TextView dailyMorningTemperature, dailyDayTemperature, dailyEveningTemperature, dailyNightTemperature;
    TextView dailyMorningTime, dailyDayTime, dailyEveningTime, dailyNightTime;
    ImageView dailyWeatherIcon;

    public DailyWeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        this.dailyTime = itemView.findViewById(R.id.time_daily);
        this.dailyTempRange = itemView.findViewById(R.id.temp_range);
        this.dailyWeatherDescription = itemView.findViewById(R.id.daily_description);
        this.dailyPrecipitation = itemView.findViewById(R.id.daily_precip);
        this.dailyUVIndex = itemView.findViewById(R.id.daily_uv_index);

        this.dailyMorningTemperature = itemView.findViewById(R.id.daily_morning_temp);
        this.dailyDayTemperature = itemView.findViewById(R.id.day_temp_daily);
        this.dailyEveningTemperature = itemView.findViewById(R.id.daily_evening_temp);
        this.dailyNightTemperature = itemView.findViewById(R.id.daily_night_temp);

        this.dailyMorningTime = itemView.findViewById(R.id.daily_morning_time_8am);
        this.dailyDayTime = itemView.findViewById(R.id.daily_time_1pm);
        this.dailyEveningTime = itemView.findViewById(R.id.daily_time_5pm);
        this.dailyNightTime = itemView.findViewById(R.id.daily_time_11pm);

        this.dailyWeatherIcon = itemView.findViewById(R.id.daily_weather_icon);
    }
}
