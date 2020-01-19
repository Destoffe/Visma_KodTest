package com.stoffe.visma;
/*
  MainActivity, sätter in start sidans fragment.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;


import com.stoffe.visma.fragments.WeatherFragment;

import com.stoffe.visma.room.HistoryDatabase;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FragmentManager fragmentManager;
    public static HistoryDatabase hDatabase;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: Started");

        hDatabase = Room.databaseBuilder(getApplicationContext(),HistoryDatabase.class,"weatherdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        fragmentManager = getSupportFragmentManager();
        replaceFragment(new WeatherFragment(),"fade");

    }

    /**
     * Byter fragment, "valfri" animation.
     * @param fragment Lägg i den fragmenten du vill byta till
     * @param anim vilken typ av animation? Fade eller slide
     */
    public void replaceFragment(Fragment fragment,String anim){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(anim){
            case "fade":
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case "slide":
                fragmentTransaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_in_right);
                break;
            case"none":
                break;
        }
        fragmentTransaction.add(android.R.id.content,fragment);
        fragmentTransaction.commit();
    }

}
