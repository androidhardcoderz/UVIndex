package com.uvindex.scottauman.backgrounds;

import android.os.AsyncTask;
import android.widget.LinearLayout;

/**
 * Created by Scott on 5/8/2016.
 */
public class CreateScrollableView extends AsyncTask<Void,Void,Void> {

    private LinearLayout layout;

    public CreateScrollableView(LinearLayout layout){
        this.layout = layout;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for(int i = 1; i < 12; i++){

        }
        return null;
    }
}
