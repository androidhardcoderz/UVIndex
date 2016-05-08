package com.uvindex.scottauman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private LinearLayout layout;
    private Shape shapeView;
    private ImageButton settings;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        shapeView = (Shape) view.findViewById(R.id.shapeView);
        settings = (ImageButton) view.findViewById(R.id.settingsImageButton);
        settings.setOnClickListener(settingsClicker);
        return view;
    }

    View.OnClickListener settingsClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = (LinearLayout) view.findViewById(R.id.fragmentRelativeLayout);
        new LoadBitmapBG().execute(isDay());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    public void getWeatherResults(UVObject uvObject) {
        //handle the uv index and data and place into UI
        shapeView.setUvIndex(uvObject.getCode());
        shapeView.invalidate(); //force redraw
    }

    /**determines by the current date and time
     * if the hours fall from 7 am - 8pm
     * if the hours fall between those values return true
     * otherwise return false
     * @return boolean
     */
    public static boolean isDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay > 6 && hourOfDay < 18) {
            //day time show the sunny background
            return true;
        }

        return false;
    }

    /**determines the color of the uv index code
     * each uv code has a corresponding color
     * colors taken from https://www.epa.gov/sunsafety/uv-index-scale-1
     * @param string
     * @return string color
     */
    public static String getUVColorBasedOnIndex(String string){
        int code = 0;
        try{
            code = Integer.parseInt(string);

            if(code == 1 || code == 2){
                return "#00FF00";
            }else if(code == 3 || code == 4 || code == 5){
                return "#FFFF00";
            }else if(code == 6){
                return "#F85900";
            }else if(code == 7){
                return "#F85900";
            }else if(code == 8){
                return "#D80010";
            }else if(code == 9){
                return "#D80010";
            }else if(code == 10){
                return "#D80010";
            }else if(code > 10){
                return "#6B49C8";
            }else{
                return "#000000";
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return "#000000";
    }

    class LoadBitmapBG extends AsyncTask<Boolean,Void,BitmapDrawable> {

        private LoadBitmapBG() {
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmap) {
            super.onPostExecute(bitmap);
            layout.setBackground(bitmap);
        }

        @Override
        protected BitmapDrawable doInBackground(Boolean... params) {
            if(params[0]){
                //true it is daytime
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.day_background);
                return new BitmapDrawable(getResources(),bitmap);
            }else{
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.night_background);
                return new BitmapDrawable(getResources(),bitmap);
            }
        }
    }
}
