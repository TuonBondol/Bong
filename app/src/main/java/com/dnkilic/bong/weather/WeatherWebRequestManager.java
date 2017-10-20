package com.dnkilic.bong.weather;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherWebRequestManager extends AsyncTask<WeatherRequestInput, Void, WeatherResult> {

    private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private final static String LANGUAGE_CODE = "tr";
    private final static String WEATHER_API_KEY = "9922194ecf32e70c92aee6b3a4882534";

    public final static int ERROR_HTTP_RESULT_IS_NOT_OK = 0;
    public final static int ERROR_JSON_READ_EXCEPTION = 1;
    public final static int ERROR_IO_EXCEPTION = 2;
    public final static int ERROR_RESULT_IS_NULL  = 3;

    private WeatherCallback callback;
    private int errorCode = -1;

    public WeatherWebRequestManager(WeatherCallback callback) {
        this.callback = callback;
    }

    @Override
    protected WeatherResult doInBackground(WeatherRequestInput... params) {
        WeatherRequestInput input = params[0];

        WeatherResult weatherResult = null;

        String url = FORECAST_URL + input.getCity() + "&units=metric" + "&cnt=7" + "&lang=" + LANGUAGE_CODE + "&APPID=" + WEATHER_API_KEY;

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if(response.code() == HttpURLConnection.HTTP_OK)
            {
                String responseString = response.body().string();

                JSONObject result = new JSONObject(responseString);
                JSONArray jsonArray = result.getJSONArray("list");

                weatherResult = new WeatherResult();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", new Locale("TR_tr"));

                for (int i = 0 ; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    long dateTimeStamp = jsonObj.getLong("dt");
                    Date dateTime = new Date(dateTimeStamp * 1000);

                    String fomattedDateString = sdf.format(dateTime);
                    if(fomattedDateString.equals(input.getDate())){
                        JSONObject temperature = jsonObj.getJSONObject("temp");

                        Calendar calendar = Calendar.getInstance();
                        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

                        if(hourOfDay >= 5 && hourOfDay <12)
                        {
                            weatherResult.setTemperature((int)Math.round(temperature.getDouble("morn")));
                        }
                        else if(hourOfDay >= 12 && hourOfDay < 18)
                        {
                            weatherResult.setTemperature((int)Math.round(temperature.getDouble("day")));
                        }
                        else if(hourOfDay >= 18 && hourOfDay < 22)
                        {
                            weatherResult.setTemperature((int)Math.round(temperature.getDouble("eve")));
                        }
                        else
                        {
                            weatherResult.setTemperature((int)Math.round(temperature.getDouble("night")));
                        }

                        weatherResult.setDescription(jsonObj.getJSONArray("weather").getJSONObject(0).getString("description"));
                    }
                }
            }
            else
            {
                errorCode = ERROR_HTTP_RESULT_IS_NOT_OK;
            }
        } catch (IOException e) {
            errorCode = ERROR_IO_EXCEPTION;
            e.printStackTrace();
        } catch (JSONException e) {
            errorCode = ERROR_JSON_READ_EXCEPTION;
            e.printStackTrace();
        }

        return weatherResult;
    }

    protected void onPostExecute(WeatherResult weatherResult) {
        if (weatherResult == null)
        {
            if(errorCode != -1)
            {
                callback.onWeatherRequestFailed(errorCode);
            }
            else
            {
                errorCode = ERROR_RESULT_IS_NULL;
                callback.onWeatherRequestFailed(errorCode);
            }
        }
        else
        {
            callback.onWeatherRequestSuccess(weatherResult);
        }
    }
}
