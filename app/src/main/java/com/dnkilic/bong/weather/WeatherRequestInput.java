package com.dnkilic.bong.weather;

public class WeatherRequestInput {
    private String city;
    private String date;

    public WeatherRequestInput(String city, String date) {
        this.city = city;
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }
}
