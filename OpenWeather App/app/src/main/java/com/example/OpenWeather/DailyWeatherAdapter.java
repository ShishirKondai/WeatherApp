package com.example.OpenWeather;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import android.view.*;

import com.example.homeweather.R;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherViewHolder> {

    private DailyWeatherActivity dailyForecastActivity;
    private UnitType unit;
    private Weather weather;
    private final List<DailyWeather> dailyList;

    public DailyWeatherAdapter(List<DailyWeather> dailyForecastList, DailyWeatherActivity dailyForecastActivity, UnitType unit, Weather weather) {
        this.dailyList = dailyForecastList;
        this.dailyForecastActivity = dailyForecastActivity;
        this.unit = unit;
        this.weather = weather;
    }

    @NonNull
    @Override
    public DailyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View inflatedLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_weather, parent, false);
        return new DailyWeatherViewHolder(inflatedLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DailyWeatherViewHolder holder, int position) {
        String daily_temp_range, daily_precipitation, daily_uv_index, icon_code, daily_morning_temp, daily_day_temp,
                daily_evening_temp, daily_night_temp;

        DailyWeather dailyForecast = this.dailyList.get(position);

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(dailyForecast.getDt().getTime() +
                        weather.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        DateTimeFormatter date_timeFormatter = DateTimeFormatter.ofPattern("EEEE, MM/dd", Locale.getDefault());
        holder.dailyTime.setText(date_timeFormatter.format(localDateTime));

        holder.dailyDayTime.setText(TimeType.DAY.value);
        holder.dailyNightTime.setText(TimeType.NIGHT.value);
        holder.dailyMorningTime.setText(TimeType.MORNING.value);
        holder.dailyEveningTime.setText(TimeType.EVENING.value);

        daily_temp_range = String.format("%s / %s", dailyForecast.getTemperature().getMaximum() + UnitType.formatUnit(unit), dailyForecast.getTemperature().getMinimum() + UnitType.formatUnit(unit));
        holder.dailyTempRange.setText(daily_temp_range);

        holder.dailyWeatherDescription.setText(Utilities.capitalizeFirstCharacter(dailyForecast.getWeatherDetailsList().get(0).getDescription()));

        daily_precipitation = String.format("(%s%% " + " precip.)", dailyForecast.getPop());
        holder.dailyPrecipitation.setText(daily_precipitation);

        daily_uv_index = String.format("UV Index: " + "%s", dailyForecast.getUvi());
        holder.dailyUVIndex.setText(daily_uv_index);

        icon_code = "_" + dailyForecast.getWeatherDetailsList().get(0).getIcon();
        holder.dailyWeatherIcon.setImageResource(dailyForecastActivity.getResources().getIdentifier(icon_code, "drawable", dailyForecastActivity.getPackageName()));

        daily_day_temp = String.format("%s%s", dailyForecast.getTemperature().getDay(), UnitType.formatUnit(unit));
        holder.dailyDayTemperature.setText(daily_day_temp);

        daily_night_temp = String.format("%s%s", dailyForecast.getTemperature().getNight(), UnitType.formatUnit(unit));
        holder.dailyNightTemperature.setText(daily_night_temp);

        daily_morning_temp = String.format("%s%s", dailyForecast.getTemperature().getMorning(), UnitType.formatUnit(unit));
        holder.dailyMorningTemperature.setText(daily_morning_temp);

        daily_evening_temp = String.format("%s%s", dailyForecast.getTemperature().getEvening(), UnitType.formatUnit(unit));
        holder.dailyEveningTemperature.setText(daily_evening_temp);
    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
}
