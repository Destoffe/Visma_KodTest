package com.stoffe.visma.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Weather> selected = new MutableLiveData<>();

    public void select(Weather weather) {
        selected.setValue(weather);
    }
    public LiveData<Weather>getSelected(){
        return selected;
    }

}
