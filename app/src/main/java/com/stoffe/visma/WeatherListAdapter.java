package com.stoffe.visma;
/*
  Sätter in datan i min listview. Var osäker på om det var detta ni menade med tableView då det inte finns något
  som exakt heter det i android hoppas detta är OK. Läser in datan och sätter ihop det med databinding.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.stoffe.visma.databinding.AdapterViewLayoutBinding;
import com.stoffe.visma.models.Weather;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


public class WeatherListAdapter extends ArrayAdapter<Weather> {
    private Context mContext;
    private int mResource;
    private ArrayList<Weather> weatherList;
    public WeatherListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Weather> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        weatherList = objects;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AdapterViewLayoutBinding binding;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        binding  = DataBindingUtil.bind(convertView);
        convertView.setTag(binding);

        if (binding != null) {
            binding.setWeather(weatherList.get(position));
        }

        return binding.getRoot();

    }

}
