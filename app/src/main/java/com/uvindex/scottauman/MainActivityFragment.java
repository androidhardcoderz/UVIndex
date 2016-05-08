package com.uvindex.scottauman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private LinearLayout layout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = (LinearLayout) view.findViewById(R.id.fragmentRelativeLayout);
        layout.setBackground(isDay() ? getActivity().getResources().getDrawable(R.drawable
                .day_background) : getActivity().getResources().getDrawable(R.drawable
                .night_background));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    public void getWeatherResults(UVObject uvObject) {
        //handle the uv index and data and place into UI

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
}
