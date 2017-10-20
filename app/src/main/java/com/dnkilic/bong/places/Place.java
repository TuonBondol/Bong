package com.dnkilic.bong.places;

public class Place {

    private String longtitude;
    private String latitude;
    private String name;
    private String vicinity;
    private String placeId;
    private String distance;

    public Place(String placeId, String longtitude, String latitude, String name, String vicinity) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.name = name;
        this.vicinity = vicinity;
        this.placeId = placeId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getLongtitude() {
        return longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }
}
