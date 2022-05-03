package com.example.OpenWeather;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;

import com.example.homeweather.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Utilities {

    public static boolean isNetworkConnectionAvailable(Activity activity) {
        ConnectivityManager connectivityManager = activity.getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public static String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }

    public static void setUnits(MenuItem homeMenuToggleUnits, UnitType unit) {
        if (unit.equals(UnitType.METRIC)) {
            homeMenuToggleUnits.setIcon(R.drawable.units_c);
        } else {
            homeMenuToggleUnits.setIcon(R.drawable.units_f);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String capitalizeFirstCharacter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays.stream(text.split(" ")).map(word -> word == null || word.isEmpty()
                        ? word
                        : word.substring(0, 1).toUpperCase() + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public static String speedFormat(UnitType unit) {
        String m_per_s = "m/s";
        String m_p_h = "mph";
        if (unit.equals(UnitType.METRIC)) {
            return m_per_s;
        }
        return m_p_h;
    }

    public static String rangeFormat(UnitType unit, double value) {
        return unit.equals(UnitType.METRIC) ? String.format("%.1f km", value / 1000) : String.format("%.1f mi", value / 1609.34);
    }

    public static double[] getLatLon(String userProvidedLocation, Activity activity) {
        Geocoder geocoder = new Geocoder(activity);
        double lat, lon;
        try {
            List<Address> address = geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                return null;
            }
            lat = address.get(0).getLatitude();
            lon = address.get(0).getLongitude();
            return new double[]{lat, lon};
        }
        catch (IOException e) {
            return null;
        }
    }

    public static String getLocationName(Activity activity, double lat, double lon) {
        Geocoder geocoder = new Geocoder(activity);
        try {
            List<Address> address =
                    geocoder.getFromLocation(lat, lon, 1);
            if (address == null || address.isEmpty()) {
                return null;
            }
            String country = address.get(0).getCountryCode();
            String p1, p2;
            String USA = "US";
            if (country.equals(USA)) {
                p1 = address.get(0).getLocality();
                p2 = address.get(0).getAdminArea();
            }
            else {
                p1 = address.get(0).getLocality();
                if (p1 == null) {
                    p1 = address.get(0).getSubAdminArea();
                }
                p2 = address.get(0).getCountryName();
            }
            String locationName = p1 + ", " + p2;
            return locationName;
        }
        catch (IOException e) {
            return null;
        }
    }
}
