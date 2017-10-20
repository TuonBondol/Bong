package com.dnkilic.bong.places;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.util.Log;

import com.dnkilic.bong.ITask;
import com.dnkilic.bong.caller.Caller;
import com.dnkilic.bong.location.LocationService;
import com.dnkilic.bong.location.LocationUpdateListener;

import java.util.List;

import ai.api.model.AIResponse;

public class PlacesManager implements ITask, LocationUpdateListener, PlacesCallback {

    @Override
    public void onPlacesSuccess(List<Place> places) {

    }

    @Override
    public void onPlacesError(String errorMessage) {

    }

    @Override
    public void onPlaceSelected(Place place) {

    }

    @Override
    public void onPlacesInfoSuccess(Place place, String phone) {

    }

    @Override
    public void onLocationUpdateError(int error) {

    }

    @Override
    public void onLocationUpdate(Address address) {

    }

    @Override
    public void execute(AIResponse result) {

    }
/*

    private CallSteeringResult mCallSteeringResult;
    private Activity mActivity;
    private Place mPlace;
    private String mSelectedPhoneNumber;
    private Place mSelectedPlace;

    public PlacesManager(CallSteeringResult callSteeringResult, Activity activity) {
        mCallSteeringResult = callSteeringResult;
        mActivity = activity;
    }

    @Override
    public void Execute() throws Exception {
        Log.i(MainActivity.TAG, "PlacesManager is started.");
        new LocationService(mActivity, this).connectLocationService();
    }

    @Override
    public void onLocationUpdateError(int error) {

        switch (error) {
            case LocationService.LOCATION_SERVICE_ERROR:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_location_service_error_while_getting_location))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case LocationService.LOCATION_SERVICE_NOT_AVAILABLE:
                Announcer.makeTTS(new Announce.Builder(Utilities.getRandomStringFromResources(R.array.announce_location_service_is_not_available))
                        .record()
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
        }

        Log.i(MainActivity.TAG, "PlacesManager is ended with error.");
    }

    @Override
    public void onLocationUpdate(Address address) {
        String query = mCallSteeringResult.getSlotValues().get("@poi").get(0);
        PlacesWebRequestManager placesWebRequestManager = new PlacesWebRequestManager(this);
        placesWebRequestManager.makePlaceRequest(query, address);
    }

    @Override
    public void ExecuteWithAdditionalValues(String dictation) throws Exception {
    }

    @Override
    public void ExecuteFromOtherApplication() throws Exception {
        openNavigation(mPlace);
    }

    @Override
    public void ExecuteWithConfirmationTreeDecision(int isConfirmed) throws Exception {

        switch (isConfirmed) {
            case ConfirmationStatus.CONFIRMED:
                Caller caller = new Caller(mActivity);
                caller.callTheNumber(mSelectedPhoneNumber);
                break;
            case ConfirmationStatus.NOT_CONFIRMED:
                openNavigation(mSelectedPlace);
                break;
            case ConfirmationStatus.NULL:
                Announcer.makeTTS(new Announce.Builder("Burayı sizin için aramamı ister misiniz?")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .addCommandType(Announce.CONFIRMATION_ANNOUNCE)
                        .record()
                        .build());
                break;
        }
    }

    @Override
    public void onPlacesSuccess(List<Place> places) {

        if (places.isEmpty()) {
            Announcer.makeTTS(new Announce.Builder("Yakınlarda " + mCallSteeringResult.getSlotValues().get("@poi").get(0) + " bulamadım.")
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .record()
                    .build());
        } else {
            if (mCallSteeringResult.getCommandType() == CommandType.SHOW_PLACE) {
                Announcer.makeTTS(new Announce.Builder("İşte arama sonuçları.")
                        .addDialogType(DialogAdapter.TYPE_PLACE)
                        .clearDialog()
                        .addPlaceList(places)
                        .setPlacesListener(this)
                        .build());
            } else if (mCallSteeringResult.getCommandType() == CommandType.SHOW_NEAREST_PLACE) {
                Place nearest = places.get(0);

                for (Place place : places) {
                    if (place.getDistance().compareTo(nearest.getDistance()) < 0) {
                        nearest = place;
                    }
                }

                PlacesWebRequestManager placesWebRequestManager = new PlacesWebRequestManager(this);
                placesWebRequestManager.makePlaceInfoRequest(nearest);
            }
        }
    }

    @Override
    public void onPlacesError(String text) {
        Announcer.makeTTS(new Announce.Builder(text)
                .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                .record()
                .build());
    }

    @Override
    public void onPlaceSelected(Place place) {
        PlacesWebRequestManager placesWebRequestManager = new PlacesWebRequestManager(this);
        placesWebRequestManager.makePlaceInfoRequest(place);
    }

    @Override
    public void onPlacesInfoSuccess(Place place, String phoneNumber) {

       // if(phoneNumber == null || phoneNumber.isEmpty())
       // {
            mPlace = place;
            Announcer.makeTTS(new Announce.Builder("Haritayı açıyorum.")
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .addCommandType(Announce.LAUNCH_ANOTHER_APP_ANNOUNCE)
                    .record()
                    .build());
       // }
       // else
       */
/* {
            mSelectedPhoneNumber = phoneNumber;
            mSelectedPlace = place;

            Announcer.makeTTS(new Announce.Builder("Burayı sizin için aramamı ister misiniz?")
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .addCommandType(Announce.CONFIRMATION_ANNOUNCE)
                    .record()
                    .build());
        }*//*

    }

    private void openNavigation(Place place) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + place.getLatitude() + "," + place.getLongtitude() + "&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        mActivity.startActivity(mapIntent);
    }
*/
}
