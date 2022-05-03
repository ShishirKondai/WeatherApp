package com.example.OpenWeather;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.homeweather.R;

import java.util.*;

public class DailyWeatherActivity extends AppCompatActivity {

    private List<DailyWeather> dailyList = new ArrayList<DailyWeather>();
    private RecyclerView recycler;
    private DailyWeatherAdapter dailyForecastAdapter;
    private Weather weather;
    private UnitType unit;
    private String locale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        recycler = findViewById(R.id.dailyForecast);

        Intent intent = getIntent();
        if(intent.hasExtra(String.valueOf(R.string.weather)) && intent.hasExtra(String.valueOf(R.string.locale)) && intent.hasExtra(String.valueOf(R.string.unit))) {
            this.weather = (Weather) intent.getSerializableExtra(String.valueOf(R.string.weather));
            this.unit = (UnitType) intent.getSerializableExtra(String.valueOf(R.string.unit));
            this.locale = intent.getStringExtra(String.valueOf(R.string.locale));
            this.setTitle(locale);

            this.dailyList.addAll(weather.getDailyList());
            this.dailyForecastAdapter = new DailyWeatherAdapter(dailyList,
                    this, unit, weather);

            recycler.setAdapter(dailyForecastAdapter);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
    }
}
