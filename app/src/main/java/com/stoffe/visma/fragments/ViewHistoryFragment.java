package com.stoffe.visma.fragments;
/**
 * Här är fragmenten där man ser historiken.
 * Väldigt identisk till huvudfragmenten.
 * Som sagt vill egentligen läsa in all datan till historiken
 * i startsidan så datan kommer in direkt och den inte ploppar upp efter 1 sekund
 * Men i mån av tid pga skola hinner jag ej detta.
 */

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.stoffe.visma.WeatherListAdapter;
import com.stoffe.visma.api.JSONWeatherParser;
import com.stoffe.visma.MainActivity;
import com.stoffe.visma.R;
import com.stoffe.visma.api.WeatherHTTPClient;
import com.stoffe.visma.models.Location;
import com.stoffe.visma.models.Weather;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ViewHistoryFragment extends Fragment implements View.OnClickListener {
    private ArrayList<Weather> testWeather;
    private ListView list;
    private int counter = 0;
    private int max;

    private static final String TAG = "WeatherViewFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"WeatherFragment Started");
        View view =  inflater.inflate(R.layout.view_history_fragment, container, false);
        ImageButton backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
        List<Location> location = MainActivity.hDatabase.myDao().getWeather();
        list = view.findViewById(R.id.citiesList);
        testWeather= new ArrayList<>();
        max = location.size();
        counter = 0;
        for(Location loc : location) {
            WeaterTask task2 = new WeaterTask();
            task2.execute(loc.getCity());
        }
        return view;
    }

    /**
     * Samma process som i startsidan
     */
    private class WeaterTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            Weather weather= new Weather();
                String data = ((new WeatherHTTPClient()).getWeatherData(params[0]));
                counter++;
                for (int y = 0; y < 5; y++) {
                    try {
                        weather = JSONWeatherParser.getWeather(data);
                        weather.iconData = ((new WeatherHTTPClient()).getImage(weather.currentCondition.getIcon()));
                        weather.setBitmap();
                        if (weather.bitmap != null) {
                            testWeather.add(weather);
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            return weather;

            }

        /**
         * På den sista lägger vi in det i listan.
         * @param weather
         */
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            if(counter >= max){
               WeatherListAdapter adapter2 = new WeatherListAdapter(Objects.requireNonNull(getActivity()),R.layout.adapter_view_layout,testWeather);
               list.setAdapter(adapter2);
            }
        }
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(new WeatherFragment(),"fade");
    }
}
