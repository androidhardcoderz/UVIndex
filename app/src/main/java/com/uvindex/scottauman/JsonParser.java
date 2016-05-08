package com.uvindex.scottauman;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Scott on 5/7/2016.
 * Parses the JSON feed from the API Rest Call
 */
public class JsonParser {
    private Context context;
    private UVObject uvObject;

    public JsonParser(Context context){
        this.context = context;
    }

    /**Parses the weather json string feed and retreiving specific contents
     * NOTE: can throw a JSONException
     * @param string
     * @return
     */
    public UVObject parseResultString(String string){
        uvObject = new UVObject();
        try {
            taskLocationStrings(string);
            taskUVIndex(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return uvObject;
    }

    /**Extracts the UV index code from the weather json feed
     * @param string
     * @throws JSONException
     */
    private void taskUVIndex(String string) throws JSONException {
        JSONObject object = new JSONObject(string).getJSONObject("current_observation");
        uvObject.setCode(object.getString("UV"));
        uvObject.setLast_update("observation_time");
        uvObject.setWeather("weather");
    }

    /**Extracts the location city/state from the weather json feed
     * @param string
     * @throws JSONException
     */
    private void taskLocationStrings(String string) throws JSONException {
            JSONObject object = new JSONObject(string).getJSONObject("location");
            uvObject.setCity(object.getString("city"));
            uvObject.setState(object.getString("state"));

    }

}
