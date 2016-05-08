package com.uvindex.scottauman;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Scott on 5/7/2016.
 */
public class Shape extends View {

    private final String TAG = getClass().getSimpleName();
    // setup initial color
    private final int paintColor = Color.YELLOW;
    // defines paint and canvas
    private Paint paint,textPaint;
    private Bitmap sun;

    public Shape(Context context) {
        super(context);
        init();
    }

    public Shape(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Shape(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Shape(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //no need to worry about this locked the screen orientation
        //and configchanges:orientation attribute to make android not redraw activity
    }

    private void init(){
        setFocusable(true);
        setFocusableInTouchMode(true);
        sun = BitmapFactory.decodeResource(getResources(), MainActivityFragment.isDay() ? R
                .drawable.sun: R.drawable.moon );
        sun = Bitmap.createScaledBitmap(sun,600,600,false);
        setupPaint();

    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);


        textPaint = new Paint();
        textPaint.setTypeface(Fonts.getCoolveticargFont(getContext()));
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(sun.getWidth() /2 - 50);
        textPaint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
    }

    public void addUVIndex(){
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = (canvas.getWidth()  - sun.getWidth()) /2;
        int centerY = (canvas.getHeight() - sun.getHeight()) /2;
        Log.d(TAG,"W:" + canvas.getWidth() / 2);
        Log.d(TAG,"H:" + canvas.getHeight() / 2);
        String gText = "7";
        canvas.drawBitmap(sun,centerX,centerY,paint);
        Canvas canvas1 = new Canvas(sun);
        // draw text to the Canvas center
        Rect bounds = new Rect();
        textPaint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (sun.getWidth() - bounds.width())/2;
        int y = (sun.getHeight() + bounds.height())/2;

        canvas1.drawText(gText, x, y, textPaint);
    }
}
