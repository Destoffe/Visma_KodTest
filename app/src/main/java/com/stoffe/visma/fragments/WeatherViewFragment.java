package com.stoffe.visma.fragments;
/**
 * Fragmenten när man har sökt på en stad som visar resultet.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.stoffe.visma.MainActivity;
import com.stoffe.visma.R;
import com.stoffe.visma.databinding.ViewWeatherFragmentBinding;
import com.stoffe.visma.models.SharedViewModel;
import com.stoffe.visma.models.Weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class WeatherViewFragment extends Fragment implements View.OnClickListener{

    private ImageButton viewButton;
    private ViewWeatherFragmentBinding wBinding;
    private static final String TAG = "WeatherViewFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"WeatherViewFragment Started");
        wBinding = DataBindingUtil.inflate(inflater, R.layout.view_weather_fragment, container, false);
        View view = wBinding.getRoot();
        viewButton = view.findViewById(R.id.viewBtn);
        viewButton.setOnClickListener(this);

        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        Weather weather = model.getSelected().getValue();
        wBinding.setWeather(weather);
        return view;
    }

    /**
     * Borde ha en switch case, men har bara 1 knapp så kan inte få fel event.
     * @param v
     */
    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).replaceFragment(new WeatherFragment(),"fade");
    }
}