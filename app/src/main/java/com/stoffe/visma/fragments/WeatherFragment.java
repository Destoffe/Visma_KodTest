package com.stoffe.visma.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.stoffe.visma.MainActivity;
import com.stoffe.visma.R;
import com.stoffe.visma.api.JSONWeatherParser;
import com.stoffe.visma.api.WeatherHTTPClient;
import com.stoffe.visma.databinding.WeatherFragmentBinding;
import com.stoffe.visma.models.Location;
import com.stoffe.visma.models.SharedViewModel;
import com.stoffe.visma.models.Weather;

import org.json.JSONException;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class WeatherFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeScreen";
    WeatherFragmentBinding mBinding;
    ImageButton viewButton;
    SharedViewModel model;
    EditText countryText;
    Button historyBtn;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false);
        view = mBinding.getRoot();


        countryText= view.findViewById(R.id.editCountry);
        viewButton = view.findViewById(R.id.viewBtn);
        viewButton.setOnClickListener(this);

        historyBtn = view.findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(this);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        return view;
    }



    private class WeaterTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHTTPClient()).getWeatherData(params[0]));
                for(int i=0; i<5; i++) {
                    try {
                        weather = JSONWeatherParser.getWeather(data);
                        weather.iconData = ((new WeatherHTTPClient()).getImage(weather.currentCondition.getIcon()));
                        weather.setBitmap();
                        if(weather.bitmap !=null) {
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

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            model.select(weather);
            mBinding.setWeather(weather);
            Location loc = new Location();
            try {
                loc.setCity(weather.loc.getCity());
                loc.setTimeStamp(weather.currentCondition.timeStamp);

                List<Location> location = MainActivity.hDatabase.myDao().getWeather();
                if(location.size() > 4){
                    MainActivity.hDatabase.myDao().deleteWeater(location.get(0));
                }
                Log.d("STORLEK",Integer.toString(location.size()));
                MainActivity.hDatabase.myDao().addWeather(loc);
                ((MainActivity) getActivity()).setViewPager(1);
            } catch (NullPointerException e){
                Toast.makeText(getActivity(),"Invalid City or Country!",Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.viewBtn:
                Log.d("Knappl","knapp1");
                String city = countryText.getText().toString();
                if (city != null && !city.isEmpty()) {
                    Log.d("NOTNULL", city);
                    WeaterTask task = new WeaterTask();
                    task.execute(new String[]{city});
                }
                break;

            case R.id.historyBtn:
                Log.d("Knappl","knapp2");
                ((MainActivity) getActivity()).setViewPager(2);
                break;
        }
    }
}