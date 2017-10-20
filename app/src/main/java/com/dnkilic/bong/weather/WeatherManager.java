package com.dnkilic.bong.weather;

import android.app.Activity;
import android.location.Address;
import android.util.Log;

import com.dnkilic.bong.ITask;
import com.dnkilic.bong.location.LocationService;
import com.dnkilic.bong.location.LocationUpdateListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import ai.api.model.AIResponse;

public class WeatherManager implements ITask, LocationUpdateListener, WeatherCallback {

    @Override
    public void onWeatherRequestSuccess(WeatherResult weather) {

    }

    @Override
    public void onWeatherRequestFailed(int code) {

    }

    @Override
    public void execute(AIResponse result) {

    }

    @Override
    public void onLocationUpdateError(int error) {

    }

    @Override
    public void onLocationUpdate(Address address) {

    }
/*

    private static final int WEATHER_CLOTH_THRESHOLD = 18;

    private Map<String, ArrayList<String>> slotValues;
    private CallSteeringResult callSteeringResult;
    private String city = null;
    private ArrayList<String> clothes = null;
    private WeatherResult returnedWeatherValue;

    private Activity mActivity;

    public WeatherManager(CallSteeringResult callSteeringResult, Activity activity) {
        this.callSteeringResult = callSteeringResult;
        slotValues = callSteeringResult.getSlotValues();
        mActivity = activity;
    }

    @Override
    public void Execute() throws Exception {
        getWeatherInfo(slotValues);
    }

    public void getWeatherInfo(Map<String, ArrayList<String>> slotValues) {
        if(slotValues != null && slotValues.get("@city") != null && !slotValues.get("@city").isEmpty()){
            city = slotValues.get("@city").get(0);
            executeWeatherTask(slotValues);
        }else{
            new LocationService(mActivity, this).connectLocationService();
        }
    }

    private void executeWeatherTask(Map<String, ArrayList<String>> slotValues) {
        WeatherWebRequestManager task = new WeatherWebRequestManager(this);
        task.execute(new WeatherRequestInput(city, getDate(slotValues)));
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
    }

    private String getDate(Map<String, ArrayList<String>> slotValues) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("TR_tr"));
        String date;

        if(slotValues != null && slotValues.get("@date") != null && !slotValues.get("@date").isEmpty()){
            date = slotValues.get("@date").get(0);
        }else{
            date = dateFormat.format(new Date());
        }

        return date;
    }

    @Override
    public void onLocationUpdate(Address address) {
        city = address.getAdminArea();
        executeWeatherTask(slotValues);
    }

    @Override
    public void onWeatherRequestSuccess(WeatherResult weather) {
        returnedWeatherValue = weather;
        String announce;

        try
        {
            if(slotValues != null && slotValues.get("@clothes") != null && !slotValues.get("@clothes").isEmpty()){
                this.clothes = slotValues.get("@clothes");
            }

            if(clothes == null){
                announce = announceReplacement(null);
            }else if(clothes.contains("şemsiye") || clothes.contains("yağmurluk")){
                if(returnedWeatherValue.getDescription().contains("yağmur")){
                    announce = announceReplacement("olabilir");
                }else{
                    announce = announceReplacement("yok");
                }
            }else{
                if(returnedWeatherValue.getTemperature() > WEATHER_CLOTH_THRESHOLD){
                    announce = announceReplacement("yok");
                }else{
                    announce = announceReplacement("olabilir");
                }
            }
            Announcer.makeTTS(new Announce.Builder(announce)
                    .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                    .build());
        }
        catch (Exception e)
        {
            FirebaseCrash.logcat(Log.ERROR, "onWeatherRequestSuccess", "Exception caught");
            FirebaseCrash.report(e);
            e.printStackTrace();
            onWeatherRequestFailed(1001);
        }
    }

    @Override
    public void onWeatherRequestFailed(int code) {
        switch (code)
        {
            case WeatherWebRequestManager.ERROR_RESULT_IS_NULL:
                Announcer.makeTTS(new Announce.Builder("İstenilen hava durumu bilgisi bulunamadı.")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case WeatherWebRequestManager.ERROR_HTTP_RESULT_IS_NOT_OK:
                Announcer.makeTTS(new Announce.Builder("Hava durumu alınırken bir hata oluştu. Internet bağlantınızı kontrol ediniz.")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case WeatherWebRequestManager.ERROR_IO_EXCEPTION:
                Announcer.makeTTS(new Announce.Builder("Hava durumu alınırken bir hata oluştu. Servis sağlayıcınızla iletişime geçiniz.")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            case WeatherWebRequestManager.ERROR_JSON_READ_EXCEPTION:
                Announcer.makeTTS(new Announce.Builder("Hava durumu verileri işlenirken bir hata oluştu.")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
            default:
                Announcer.makeTTS(new Announce.Builder("Anons oluşturulurken bir hata oluştu.")
                        .addDialogType(DialogAdapter.TYPE_DIALOG_RECEIVED)
                        .build());
                break;
        }
    }

    private String announceReplacement(String result){
        String announce = callSteeringResult.getAnnounceText();

        if(!announce.isEmpty()){
            if(announce.contains("=temperature")){
                announce = announce.replace("=temperature", String.valueOf(returnedWeatherValue.getTemperature()));
            }
            if(announce.contains("=weather")){
                announce = announce.replace("=weather", String.valueOf(returnedWeatherValue.getDescription()).toLowerCase());
            }
            if(announce.contains("=result")){
                if(result != null){
                    announce = announce.replace("=result",result);
                }
            }
        }else{
            announce = "";
        }

        return announce;
    }

    @Override
    public void execute(AIResponse result) {

    }
*/
}
