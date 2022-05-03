package com.example.OpenWeather;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;

import com.example.homeweather.R;

import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherViewHolder> {
    private final List<HourlyWeather> hourlyArrayList;
    private UnitType unit;
    private Weather weather;
    private MainActivity mainActivity;

    public HourlyWeatherAdapter(List<HourlyWeather> hourlyList, MainActivity mainActivity, UnitType unit, Weather weather) {
        this.hourlyArrayList = hourlyList;
        this.mainActivity = mainActivity;
        this.unit = unit;
        this.weather = weather;
    }

    @NonNull
    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather, parent, false);

        itemView.setOnClickListener(mainActivity);
        return new HourlyWeatherViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position) {
        String hourlyIconCode;
        HourlyWeather hourly = hourlyArrayList.get(position);

        String pattern = "hh:00 a", timePattern = "EEEE";
        SimpleDateFormat simple = new SimpleDateFormat("EEEE");

        LocalDateTime date = LocalDateTime.ofEpochSecond(hourly.getDate().getTime() + weather.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(timePattern, Locale.getDefault());

        Date d = new Date();
        String dayOfTheWeek = simple.format(d);
        if(dayOfTheWeek.equals(date.format(timeFormat))){
            holder.hourlyDay.setText(R.string.today_text);
        }
        else {
            holder.hourlyDay.setText(date.format(timeFormat));
        }
        holder.hourlyTime.setText(date.format(format));
        holder.hourlyTemperature.setText(String.format("%s%s", hourly.getTemp(), UnitType.formatUnit(unit)));
        holder.hourlyDescription.setText(Utilities.capitalizeFirstCharacter(hourly.getWeatherDetailsList().get(0).getDescription()));

        hourlyIconCode = "_" + hourly.getWeatherDetailsList().get(0).getIcon();
        int iconResId = mainActivity.getResources().getIdentifier(hourlyIconCode, "drawable", mainActivity.getPackageName());
        holder.hourlyIcon.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return hourlyArrayList.size();
    }
}