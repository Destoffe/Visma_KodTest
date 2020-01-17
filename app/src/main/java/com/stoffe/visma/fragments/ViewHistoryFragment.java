package com.stoffe.visma.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.stoffe.visma.api.JSONWeatherParser;
import com.stoffe.visma.MainActivity;
import com.stoffe.visma.R;
import com.stoffe.visma.models.SharedViewModel;
import com.stoffe.visma.api.WeatherHTTPClient;
import com.stoffe.visma.databinding.FragmentViewHistoryBinding;
import com.stoffe.visma.models.Location;
import com.stoffe.visma.models.Weather;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ViewHistoryFragment extends Fragment implements View.OnClickListener {
    FragmentViewHistoryBinding mBinding;
    SharedViewModel model;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_history, container, false);
        view = mBinding.getRoot();
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        ImageButton backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        List<Location> location = MainActivity.hDatabase.myDao().getWeather();
        ListView list = view.findViewById(R.id.citiesList);
        ArrayList<String> cities = new ArrayList<>();
        for(Location loc : location)
        {
            String city = loc.getCity();
            cities.add(city);
        }

        ArrayAdapter adapter = new ArrayAdapter(view.getContext(),R.layout.list_weather_layout,cities);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String city = location.get(position).getCity();
            Log.d("LAND",city);
            WeaterTask task = new WeaterTask();
            task.execute(new String[]{city});
        });

        return view;
    }

    private class WeaterTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHTTPClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);
                // Let's retrieve the icon
                weather.iconData = ((new WeatherHTTPClient()).getImage(weather.currentCondition.getIcon()));
                weather.setBitmap();

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            model.select(weather);
            mBinding.setWeather(weather);
            Location loc = new Location();
            loc.setCity(weather.loc.getCity());
            loc.setTimeStamp(weather.currentCondition.timeStamp);
            ((MainActivity) getActivity()).setViewPager(1);

        }
    }

    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).setViewPager(0);

    }




}
