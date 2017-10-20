package com.dnkilic.bong.places;

import android.location.Address;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PlacesWebRequestManager {

    OkHttpClient client = new OkHttpClient();
    private PlacesCallback mPlacesCallback;

    PlacesWebRequestManager(PlacesCallback placesCallback) {
        mPlacesCallback = placesCallback;
    }

    public void makePlaceRequest(String place, Address currentAddress) {
        new RetrievePlaces(place, currentAddress).execute();
    }

    public void makePlaceInfoRequest(Place place) {
        new RetrievePlaceInfo(place).execute();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist / 1000);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private class RetrievePlaces extends AsyncTask<Void, Void, Void> {

        private Address mCurrentAddress;
        private String mPlace;

        public RetrievePlaces(String place, Address currentAddress) {
            mCurrentAddress = currentAddress;
            mPlace = place;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                run("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + mCurrentAddress.getLatitude() + "," + mCurrentAddress.getLongitude() + "&radius=1000&type=" + mPlace + "&key=AIzaSyCIhWNIp8IVISrVoQismM6cj3C3_WHWO6I", mCurrentAddress);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private String run(String url, final Address currentAddress) {
            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mPlacesCallback.onPlacesError("Çevrenizdeki yerler alınırken bir hata oluştu.");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String athena = response.body().string();
                    try {
                        JSONObject json = new JSONObject(athena);

                        if (json.get("status").equals("OK")) {
                            JSONArray jArrayResults = json.getJSONArray("results");
                            ArrayList<Place> places = new ArrayList<>();

                            for (int i = 0; i < jArrayResults.length(); i++) {
                                JSONObject item = jArrayResults.getJSONObject(i);
                                JSONObject location = item.getJSONObject("geometry").getJSONObject("location");
                                places.add(new Place(item.getString("place_id"), location.getString("lng"), location.getString("lat"), item.getString("name"), item.getString("vicinity")));
                            }

                            if (!places.isEmpty()) {
                                Double currentLatitude = currentAddress.getLatitude();
                                Double currentLongtitude = currentAddress.getLongitude();
                                new CalculateDistance(places, currentLatitude, currentLongtitude).execute();
                            } else {
                                mPlacesCallback.onPlacesError("Çevrenizde böyle bir yer bulamadım.");
                            }
                        } else {
                            mPlacesCallback.onPlacesError("Çevrenizdeki yerler alınırken bir hata oluştu.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }
    }

    private class RetrievePlaceInfo extends AsyncTask<Void, Void, Void> {

        private Place mPlace;

        public RetrievePlaceInfo(Place place) {
            mPlace = place;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                requestPlaceInfo("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + mPlace.getPlaceId() + "&key=AIzaSyCIhWNIp8IVISrVoQismM6cj3C3_WHWO6I");
            } catch (Exception e) {
                e.printStackTrace();
                mPlacesCallback.onPlacesError("Seçilen yer ayrıntıları alınırken bir hata oluştu.");
            }
            return null;
        }

        private void requestPlaceInfo(String url) {

            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mPlacesCallback.onPlacesError("Seçilen yer ayrıntıları alınırken bir hata oluştu.");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String athena = response.body().string();
                    try {
                        JSONObject json = new JSONObject(athena);

                        if (json.get("status").equals("OK")) {
                            JSONObject result = json.getJSONObject("result");

                            String phoneNumber = null;

                            if (result.has("international_phone_number")) {
                                phoneNumber = result.getString("international_phone_number");
                            }

                            mPlacesCallback.onPlacesInfoSuccess(mPlace, phoneNumber);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        mPlacesCallback.onPlacesError("Seçilen yer ayrıntıları alınırken bir hata oluştu.");
                    }
                }
            });
        }
    }

    private class CalculateDistance extends AsyncTask<Void, Void, Void> {

        private ArrayList<Place> mPlaces;
        private Double mCurrentLatitude;
        private Double mCurrentLongtitude;

        public CalculateDistance(ArrayList<Place> place, Double currentLatitude, Double currentLongtitude) {
            mPlaces = place;
            mCurrentLongtitude = currentLongtitude;
            mCurrentLatitude = currentLatitude;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                String destinations = "";

                for (Place place : mPlaces) {
                    if (mPlaces.indexOf(place) == mPlaces.size() - 1) {
                        destinations = destinations + "place_id:" + place.getPlaceId();
                    } else {
                        destinations = destinations + "place_id:" + place.getPlaceId() + "|";
                    }
                }

                requestDistanceMatrix("https://maps.googleapis.com/maps/api/distancematrix/json?&origins=" + mCurrentLatitude + "," + mCurrentLongtitude + "&destinations=" + destinations + "&key=AIzaSyCIhWNIp8IVISrVoQismM6cj3C3_WHWO6I");
            } catch (Exception e) {
                e.printStackTrace();
                mPlacesCallback.onPlacesError("Seçilen yer ayrıntıları alınırken bir hata oluştu.");
            }
            return null;
        }

        private void requestDistanceMatrix(String url) {

            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mPlacesCallback.onPlacesError("Seçilen yer ayrıntıları alınırken bir hata oluştu.");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String athena = response.body().string();
                    try {
                        JSONObject json = new JSONObject(athena);

                        if (json.get("status").equals("OK")) {
                            JSONArray rows = json.getJSONArray("rows");
                            JSONObject elementsObject = rows.getJSONObject(0);

                            JSONArray elementsArray = elementsObject.getJSONArray("elements");

                            ArrayList<String> distances = new ArrayList<>();

                            for (int i = 0; i < elementsArray.length(); i++) {
                                JSONObject element = elementsArray.getJSONObject(i);

                                if (element.getString("status").equals("OK")) {
                                    String distance = "";
                                    String duration = "";

                                    if (element.has("distance")) {
                                        JSONObject distanceObject = element.getJSONObject("distance");
                                        distance = distanceObject.getString("text");
                                    }

                                    if (element.has("duration")) {
                                        JSONObject durationObject = element.getJSONObject("duration");
                                        duration = durationObject.getString("text");
                                    }

                                    if (!distance.isEmpty() && !duration.isEmpty()) {
                                        distances.add(distance + ", " + duration);
                                    } else if (distance.isEmpty() && !duration.isEmpty()) {
                                        distances.add(duration);
                                    } else if (!distance.isEmpty() && duration.isEmpty()) {
                                        distances.add(distance);
                                    } else if (distance.isEmpty() && duration.isEmpty()) {
                                        distances.add("");
                                    }
                                } else {
                                    distances.add("");
                                }
                            }

                            for (int i = 0; i < mPlaces.size(); i++) {
                                mPlaces.get(i).setDistance(distances.get(i));
                            }

                            mPlacesCallback.onPlacesSuccess(mPlaces);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        mPlacesCallback.onPlacesError("Seçilen yer ayrıntıları alınırken bir hata oluştu.");
                    }
                }
            });
        }
    }
}
