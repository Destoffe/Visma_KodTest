package com.stoffe.visma.api;
/*
  JSON Parser för min data jag får från APIet
  Lägger in det i en weather model som jag sedan tar ut i mina fragments.
 */

import com.stoffe.visma.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

    public static Weather getWeather(String data) throws JSONException  {
        Weather weather = new Weather();
        if(data != null) {
            JSONObject jObj = new JSONObject(data);

            JSONObject sysObj = getObject("sys", jObj);
            weather.loc.setCountry(getString("country", sysObj));
            weather.loc.setCity(getString("name", jObj));
            weather.currentCondition.setTimeStamp(getInt("dt", jObj));

            JSONArray jArr = jObj.getJSONArray("weather");
            JSONObject jArr2 = getObject("main", jObj);

            JSONObject JSONWeather = jArr.getJSONObject(0);
            weather.currentCondition.setDescr(getString("description", JSONWeather));
            weather.currentCondition.setIcon(getString("icon", JSONWeather));
            weather.currentCondition.setTemp(getFloat("temp", jArr2));
            return weather;
        }
        return null;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}