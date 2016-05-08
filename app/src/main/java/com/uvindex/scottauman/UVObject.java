package com.uvindex.scottauman;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 5/7/2016.
 */
public class UVObject implements Parcelable {

    private String code;
    private String city;
    private String state;
    private String last_update;
    private String weather;

    public UVObject(){
        //empty for building the objects
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    protected UVObject(Parcel in) {
        code = in.readString();
        city = in.readString();
        state = in.readString();
        last_update = in.readString();
        weather = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(last_update);
        dest.writeString(weather);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UVObject> CREATOR = new Parcelable.Creator<UVObject>() {
        @Override
        public UVObject createFromParcel(Parcel in) {
            return new UVObject(in);
        }

        @Override
        public UVObject[] newArray(int size) {
            return new UVObject[size];
        }
    };
}