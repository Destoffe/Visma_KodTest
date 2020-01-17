package com.stoffe.visma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.stoffe.visma.fragments.ViewHistoryFragment;
import com.stoffe.visma.fragments.WeatherFragment;
import com.stoffe.visma.fragments.WeatherViewFragment;
import com.stoffe.visma.room.HistoryDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    public static HistoryDatabase hDatabase;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: Started");

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        mViewPager.setOnTouchListener((v, event) -> true);

        hDatabase = Room.databaseBuilder(getApplicationContext(),HistoryDatabase.class,"weatherdb").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WeatherFragment(),"HomeScreen");
        adapter.addFragment(new WeatherViewFragment(),"ResultScreen");
        adapter.addFragment(new ViewHistoryFragment(),"HistoryScreen");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

}
