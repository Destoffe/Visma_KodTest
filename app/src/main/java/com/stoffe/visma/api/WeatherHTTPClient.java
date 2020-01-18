package com.stoffe.visma.api;

import android.os.Build;
import android.util.Log;

import com.stoffe.visma.BuildConfig;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHTTPClient {
    private static String BASE_URL = BuildConfig.ApiUrl;
    private static String IMG_URL = "http://openweathermap.org/img/wn/";
    private static String APPID = BuildConfig.ApiKey;
    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASE_URL+"=" + location + "&APPID=" + APPID)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ( (line = br.readLine()) != null ) {
                buffer.append(line + "rn");
            }

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code +"@2x.png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            Log.d("WeatherResponce",(IMG_URL + code +"@2x.png"));
            is = con.getInputStream();

            byte[] buffer = new byte[4096];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }
}