package com.example.OpenWeather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.homeweather.R;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;

import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.time.*;
import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String weatherURL = "https://api.openweathermap.org/data/2.5/onecall";

    private static final String apiKey = "ce4295954ba7183fa4d484f28434b96a";

    private final List<HourlyWeather> hourlyList = new ArrayList<HourlyWeather>();
    private RequestQueue queue;
    private MenuItem mainMenuToggleUnit;

    private RecyclerView recycler;
    private HourlyWeatherAdapter hourlyAdapter;
    private UnitType unit = UnitType.METRIC;
    private Weather weather;
    private String locName = "";
    private SwipeRefreshLayout swiper;
    private Double latitude = 41.8675766, longitude = -87.616232;

    private TextView location, dateTime, feelsLike, temperature, uvIndex, weather_description, humidity;
    private TextView wind, visibility, snow_rain;
    private TextView morningTemp, eveningTemp, dayTemp, nightTemp;
    private TextView morningTime, eveningTime, dayTime, nightTime;
    private TextView sunriseTime, sunsetTime;
    private ImageView weather_icon;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.main_activity_title);
        recycler = findViewById(R.id.hourly_recycle_view);
        getUserSetting();

        this.location = findViewById(R.id.location);
        this.dateTime = findViewById(R.id.date_time);
        this.temperature = findViewById(R.id.temp);
        this.feelsLike = findViewById(R.id.feels_like);
        this.weather_description = findViewById(R.id.description);
        this.wind = findViewById(R.id.wind);
        this.humidity = findViewById(R.id.humid_percent);
        this.uvIndex = findViewById(R.id.uv_index);
        this.snow_rain = findViewById(R.id.snow_or_rain);
        this.visibility = findViewById(R.id.visibility);
        this.morningTemp = findViewById(R.id.morning_temp);
        this.dayTemp = findViewById(R.id.day_temp);
        this.eveningTemp = findViewById(R.id.evening_temp);
        this.nightTemp = findViewById(R.id.night_temp);
        this.dayTime = findViewById(R.id.day_time);
        this.morningTime = findViewById(R.id.morning_time);
        this.eveningTime = findViewById(R.id.evening_time);
        this.nightTime = findViewById(R.id.night_time);
        this.sunriseTime = findViewById(R.id.sunrise_time);
        this.sunsetTime = findViewById(R.id.sunset_time);
        this.weather_icon = findViewById(R.id.icon);

        this.swiper = findViewById(R.id.swipeRefresh);
        this.swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRefresh() {
                getWeatherData();
                swiper.setRefreshing(false);
            }
        });

        getWeatherData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int position_click = recycler.getChildLayoutPosition(view);
        HourlyWeather hourly = this.hourlyList.get(position_click);

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(hourly.getDate().getTime() + weather.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        Calendar c = new GregorianCalendar();
        c.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");

        builder.appendPath(Long.toString(c.getTime().getTime()));
        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateWeatherData(Weather weatherData) {
        if (weatherData != null) {
            setWeatherData(weatherData);
            weather = weatherData;
            hourlyAdapter = new HourlyWeatherAdapter(weather.getHourlyList(), this, unit, weatherData);
            recycler.setAdapter(hourlyAdapter);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            setHourlyRecyclerViewData(weatherData.getHourlyList());
        }
    }

    public void downloadFailed() {
        hourlyList.clear();
        hourlyAdapter.notifyItemRangeChanged(0, hourlyList.size());
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWeatherData(Weather weatherData) {
        if (weatherData == null) {
            Toast.makeText(this, R.string.invalid_city, Toast.LENGTH_SHORT).show();
            return;
        }

        CurrentWeather current_data = weatherData.getCurrent();

        DailyWeather daily = weatherData.getDailyList().get(0);

        LocalDateTime date_time = LocalDateTime.ofEpochSecond(current_data.getDate().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        String iconCode = "_" + current_data.getWeatherDetailsList().get(0).getIcon();

        locName = Utilities.getLocationName(this, weatherData.getLatitude(),
                weatherData.getLongitude());
        location.setText(locName);

        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy", Locale.getDefault());

        dateTime.setText(date_time.format(dtFormat));
        temperature.setText(String.format("%s%s", current_data.getTemp(), UnitType.formatUnit(unit)));
        feelsLike.setText(String.format("Feels Like " + "%s%s", current_data.getFeelLike(), UnitType.formatUnit(unit)));
        weather_description.setText(String.format("%s (%s clouds)", Utilities.capitalizeFirstCharacter(current_data.getWeatherDetailsList().get(0).getDescription()), current_data.getCloud() + "%"));

        humidity.setText(String.format("Humidity: " + "%s%%", current_data.getHumidity()));
        uvIndex.setText(String.format("UV Index: " + "%s", current_data.getUv()));

        weather_icon.setImageResource(getResources().getIdentifier(iconCode, "drawable", getPackageName()));

        String sFormat = Utilities.speedFormat(this.unit);
        wind.setText(String.format("Winds: " + "%s at %s %s", Utilities.getDirection(current_data.getDegrees()), current_data.getSpeed(), sFormat) + (current_data.getGust() != null ?
                String.format(" gusting to %s %s", current_data.getGust(), sFormat) : ""));

        String snowOrRain = "";
        if(current_data.getSnow() != null) {
            snowOrRain = String.format("Last Hour Snow " + "%s mm", current_data.getSnow());
        }

        if(current_data.getRain() != null) {
            snowOrRain = String.format("Last Hour Rain " + "%s mm", current_data.getRain());
        }
        snow_rain.setText(snowOrRain);

        visibility.setText("Visibility: " + Utilities.rangeFormat(unit, current_data.getVisibility()));

        morningTemp.setText(String.format("%s%s", daily.getTemperature().getMorning(), UnitType.formatUnit(unit)));
        dayTemp.setText(String.format("%s%s", daily.getTemperature().getDay(), UnitType.formatUnit(unit)));
        eveningTemp.setText(String.format("%s%s", daily.getTemperature().getEvening(), UnitType.formatUnit(unit)));
        nightTemp.setText(String.format("%s%s", daily.getTemperature().getNight(), UnitType.formatUnit(unit)));

        morningTime.setText(TimeType.MORNING.value);
        dayTime.setText(TimeType.DAY.value);
        eveningTime.setText(TimeType.EVENING.value);
        nightTime.setText(TimeType.NIGHT.value);

        DateTimeFormatter sunrise_sunsetFormat = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());

        LocalDateTime sunrise_Time = LocalDateTime.ofEpochSecond(current_data.getSunriseTime().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        LocalDateTime sunset_Time = LocalDateTime.ofEpochSecond(current_data.getSunsetTime().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        sunriseTime.setText(String.format("Sunrise: " + "%s", sunrise_Time.format(sunrise_sunsetFormat)));
        sunsetTime.setText(String.format("Sunset: " + "%s", sunset_Time.format(sunrise_sunsetFormat)));

        hourlyAdapter = new HourlyWeatherAdapter(weatherData.getHourlyList(), MainActivity.this, unit, weatherData);
        recycler.setAdapter(hourlyAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        setHourlyRecyclerViewData(weatherData.getHourlyList());
    }

    private void setHourlyRecyclerViewData(List<HourlyWeather> hourlyList) {
        this.hourlyList.addAll(hourlyList);
        this.hourlyAdapter.notifyItemRangeChanged(0, hourlyList.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getWeatherData() {
        if (Utilities.isNetworkConnectionAvailable(this)) {
            queue = Volley.newRequestQueue(getApplicationContext());

            Uri.Builder buildURL = Uri.parse(weatherURL).buildUpon();
            buildURL.appendQueryParameter("lat", String.format("%s", latitude));
            buildURL.appendQueryParameter("lon", String.format("%s", longitude));
            buildURL.appendQueryParameter("units", this.unit.toString().toLowerCase());
            buildURL.appendQueryParameter("appid", apiKey);
            buildURL.appendQueryParameter("lang", "en");
            buildURL.appendQueryParameter("exclude", "minutely");
            String urlToUse = buildURL.build().toString();

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET, urlToUse,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            weather = WeatherJSON.parseJSON(response.toString());

                            setWeatherData(weather);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            queue.add(jsonObjectRequest);
        }
        else {
            weather_icon.setImageResource(0);
            location.setText("");
            dateTime.setText(String.format("%s", "No network connection"));
            temperature.setText("");
            feelsLike.setText("");
            weather_description.setText("");
            wind.setText("");
            humidity.setText("");
            uvIndex.setText("");
            snow_rain.setText("");
            visibility.setText("");
            morningTemp.setText("");
            dayTemp.setText("");
            eveningTemp.setText("");
            nightTemp.setText("");
            dayTime.setText("");
            morningTime.setText("");
            eveningTime.setText("");
            nightTime.setText("");
            sunriseTime.setText("");
            sunsetTime.setText("");

            hourlyList.clear();
            hourlyAdapter = new HourlyWeatherAdapter(hourlyList, this, unit, weather);
            recycler.setAdapter(hourlyAdapter);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mainMenuToggleUnit = menu.findItem(R.id.temp_icon);
        Utilities.setUnits(mainMenuToggleUnit, unit);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.temp_icon:
                this.unit = unit.equals(UnitType.METRIC) ? UnitType.IMPERIAL : UnitType.METRIC;
                setUserSetting();
                Utilities.setUnits(mainMenuToggleUnit, this.unit);
                getWeatherData();
                break;
            case R.id.daily_icon:
                openDailyActivity();
                break;
            case R.id.location_icon:
                locationChangeDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void locationChangeDialog() {
        if(Utilities.isNetworkConnectionAvailable(this)) {
            LayoutInflater inflater = LayoutInflater.from(this);
            final View view = inflater.inflate(R.layout.change_location, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.change_location_title);
            builder.setMessage(R.string.change_location_message);
            builder.setView(view);
            builder.setPositiveButton(R.string.ok_button, (dialog, id) -> {

                EditText alertDialogLocation = view.findViewById(R.id.change_location);
                double[] latLongArray = Utilities.getLatLon(alertDialogLocation.getText().toString(), this);
                if (latLongArray != null) {
                    latitude = latLongArray[0];
                    longitude = latLongArray[1];
                    setUserSetting();
                    getWeatherData();
                }
                else {
                    Toast.makeText(this, R.string.no_loc_change, Toast.LENGTH_SHORT).show();
                }

            });
            builder.setNegativeButton(R.string.cancel_button, (dialog, id) -> { });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Toast.makeText(this, R.string.no_network_connection, Toast.LENGTH_SHORT).show();
        }
    }

    public void setUserSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(String.valueOf(R.string.unit), unit.getUnit());
        sharedPreferencesEditor.putString(String.valueOf(R.string.latitude), latitude.toString());
        sharedPreferencesEditor.putString(String.valueOf(R.string.longitude), longitude.toString());
        sharedPreferencesEditor.apply();
    }

    public void getUserSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        String unit = sharedPreferences.getString(String.valueOf(R.string.unit), getString(R.string.metric));
        this.unit = UnitType.getEnum(unit);
        String lat = sharedPreferences.getString(String.valueOf(R.string.latitude), this.latitude.toString());
        this.latitude = Double.parseDouble(lat);
        String longt = sharedPreferences.getString(String.valueOf(R.string.longitude), this.longitude.toString());
        this.longitude = Double.parseDouble(longt);
    }

    public void openDailyActivity() {
        if(Utilities.isNetworkConnectionAvailable(this)) {
            Intent intent = new Intent(this, DailyWeatherActivity.class);
            intent.putExtra(String.valueOf(R.string.weather), weather);
            intent.putExtra(String.valueOf(R.string.unit), unit);
            intent.putExtra(String.valueOf(R.string.locale), locName);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }
}