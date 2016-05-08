package com.uvindex.scottauman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.uvindex.scottauman.backgrounds.DownloadResultReceiver;
import com.uvindex.scottauman.backgrounds.DownloadService;

public class MainActivity extends AppCompatActivity implements DownloadResultReceiver.Receiver{

    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    private DownloadResultReceiver mReceiver;
    public final String TAG = getClass().getName();

    private MainActivityFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id
                .uvfragment);

        //create and start BG sticky intent service to handle the location requests and weather
        // download
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        //create callback interface for lat/long
        Location.LocationCallback locationCallback = new Location.LocationCallback() {
            @Override
            public void newCords(double lats, double longs) {

                double latitude = lats;
                double longitude = longs;

                Log.d(TAG,latitude + "");
                Log.d(TAG,longitude + "");

                Intent intent = new Intent(Intent.ACTION_SYNC, null,MainActivity.this, DownloadService.class);

                /* Send optional extras to Download IntentService */
                intent.putExtra("receiver", mReceiver);
                intent.putExtra("requestId", 101);
                intent.putExtra(LATITUDE,latitude);
                intent.putExtra(LONGITUDE,longitude);

                //startService(intent);
            }
        };

        Location location = new Location(getApplicationContext(),locationCallback);
        location.registerLocationUpdates();

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                Log.d(TAG,resultData.getString(DownloadService.STATUS_UPDATE));
                break;
            case DownloadService.STATUS_FINISHED:
                if(resultData.containsKey(DownloadService.PAYLOAD)){
                    //extract the parcelable object from bundle
                    UVObject uvObject = (UVObject) resultData.getParcelable(DownloadService
                            .PAYLOAD);
                    Log.d(TAG,uvObject.getCity());
                    Log.d(TAG,uvObject.getState());
                    Log.d(TAG,uvObject.getCode());
                    fragment.getWeatherResults(uvObject);
                }
                break;
            case DownloadService.STATUS_ERROR:
                Log.d(TAG,resultData.getString(DownloadService.ERROR));
                break;
        }
    }


}
