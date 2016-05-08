package com.uvindex.scottauman.backgrounds;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.uvindex.scottauman.JsonParser;
import com.uvindex.scottauman.MainActivity;
import com.uvindex.scottauman.R;

import java.io.IOException;

public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    public static final String STATUS_UPDATE = "update";
    public static final String ERROR = "error";
    public static final String PAYLOAD = "payload";
    private static final String TAG = "DownloadService";

    private double latitude,longitude;

    public DownloadService() {
        super(DownloadService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        Bundle bundle = new Bundle();

        if(intent.getExtras() != null) {
            latitude = intent.getDoubleExtra(MainActivity.LATITUDE, 0.0);
            longitude = intent.getDoubleExtra(MainActivity.LONGITUDE, 0.0);
        }else{
            bundle.putString(ERROR,"NO LOCATION VALUES PASSED INTO SERVICE");
            receiver.send(STATUS_ERROR,bundle);
            this.stopSelf();
        }

        /* Update UI: Download Service is Running */
        receiver.send(STATUS_RUNNING,createWeatherFindBundle());
        //create weather API_REST Call and download the data

        //convert stream provided into JSON String
        DownloadURLData downloadURLData = new DownloadURLData();
        String serverResult = null;
        try {
            serverResult = new InputStreamConverter(getApplicationContext()).convertInputStreamToString(
            downloadURLData.downloadData(WeatherAPIBuilder
                    .buildWeatherAPIString(getApplicationContext(),latitude,longitude)));
        } catch (DownloadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(serverResult == null || serverResult.equals("")){
            try {
                throw new DownloadException("Server Returned No Result");
            } catch (DownloadException e) {
                e.printStackTrace();
                bundle.putString(ERROR,e.toString());
                receiver.send(STATUS_ERROR,bundle);
            }
        }else{
            //we got a response! Let's parse the response
            System.out.println(serverResult);
            JsonParser jsonParser = new JsonParser(getApplicationContext());
            bundle.putParcelable(PAYLOAD,jsonParser.parseResultString(serverResult));
            receiver.send(STATUS_FINISHED,bundle);
        }

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    private Bundle createLocationFindBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(STATUS_UPDATE,"Searching For Your Location..");
        return bundle;
    }

    private Bundle createWeatherFindBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(STATUS_UPDATE,"Looking Up Weather Conditions");
        return bundle;
    }

     static class WeatherAPIBuilder {

        public static String buildWeatherAPIString(Context context, double la, double lng){
            return context.getString(R.string.WEATHER_API_REST_CALL) +
                la + "," + lng + ".json";
        }
    }
}
