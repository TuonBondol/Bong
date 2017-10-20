package com.dnkilic.bong.weather;

public interface WeatherCallback {
    void onWeatherRequestSuccess(WeatherResult weather);
    void onWeatherRequestFailed(int code);
}
