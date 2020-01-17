package com.stoffe.visma.fragments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class WeatherViewFragment extends Fragment implements View.OnClickListener{

    ImageButton viewButton;
    ViewWeatherFragmentBinding wBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        wBinding = DataBindingUtil.inflate(inflater, R.layout.view_weather_fragment, container, false);
        View view = wBinding.getRoot();
        viewButton = view.findViewById(R.id.viewBtn);
        viewButton.setOnClickListener(this);

        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.getSelected().observe(this, item ->{
            Log.d("ANDRAS","ANDRAS");

            wBinding.setWeather(item);

        });
        return view;
    }


    @Override
    public void onClick(View v) {

        ((MainActivity)getActivity()).setViewPager(0);
    }
}