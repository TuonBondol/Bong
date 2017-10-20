package com.dnkilic.bong.places;

import java.util.List;

public interface PlacesCallback {
    void onPlacesSuccess(List<Place> places);
    void onPlacesError(String errorMessage);

    void onPlaceSelected(Place place);

    void onPlacesInfoSuccess(Place place, String phone);
}
