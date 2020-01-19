package com.stoffe.visma.fragments;
/*
  Startsidans fragment. Skickar datan från denna fragmenten till resultet med LiveData.
  Hade velar göra likadant för historiken så den kommer upp instant men i mån av tid gjorde jag ej detta.
   Var lite jobbigt att handera bitdatan för just det.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private WeatherFragmentBinding mBinding;
    private SharedViewModel model;
    private EditText countryText;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false);
        Log.d(TAG,"WeatherFragment Started");
        View view = mBinding.getRoot();

        view.getWidth();

        countryText= view.findViewById(R.id.editCountry);
        ImageButton viewButton = view.findViewById(R.id.viewBtn);
        viewButton.setOnClickListener(this);

        Button historyBtn = view.findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(this);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        return view;
    }

    /**
     * Läser in datan från APIN.
     * Har en for-loop här för att en av de alla bilderna strulade och
     * codecen klarade nästan aldrig av den på första försöken
     * (Var någon bugg i emulatorerna enligt stackoverflow)
     * Efter datan är inläst och bilden är gjort till en bitmap
     * så sparar jag datan i min LiveData samt lägger in datan i min databinding
     * När datan är klar byter jag fragment
     */
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

        /**
         * Här kollar jag även hur mycket som är i min historik. Har jag redan 5st tar jag
         * bort den sista och lägger in den nya i min databas.
         * @param weather Tar in vädret från tasken och sparar den i sql
         */
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            model.select(weather);
            mBinding.setWeather(weather);
            Location loc = new Location();
            try {
                loc.setCity(weather.loc.getCity());

                List<Location> location = MainActivity.hDatabase.myDao().getWeather();
                if(location.size() > 4){
                    MainActivity.hDatabase.myDao().deleteWeater(location.get(0));
                }
                MainActivity.hDatabase.myDao().addWeather(loc);
                ((MainActivity)getActivity()).replaceFragment(new WeatherViewFragment(),"slide");
            } catch (NullPointerException e){
                Toast.makeText(getActivity(),"Invalid City or Country!",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.viewBtn:
                String city = countryText.getText().toString();
                if (!city.isEmpty()) {
                    hideKeyboard(getContext());
                    WeaterTask task = new WeaterTask();
                    task.execute(city);
                }
                break;

            case R.id.historyBtn:

                ((MainActivity)getActivity()).replaceFragment(new ViewHistoryFragment(),"fade");
                break;
        }
    }

    private void hideKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((MainActivity) mContext).getWindow()
                .getCurrentFocus().getWindowToken(), 0);
    }
}