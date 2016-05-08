package com.uvindex.scottauman;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Scott on 5/8/2016.
 */
public class Fonts {

    public static Typeface getCoolveticargFont(Context context){
        return Typeface.createFromAsset(context.getAssets(),"fonts/" + "coolveticarg.ttf");
    }
}
