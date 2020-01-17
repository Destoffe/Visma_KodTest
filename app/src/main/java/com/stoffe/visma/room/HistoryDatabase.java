package com.stoffe.visma.room;


import com.stoffe.visma.models.Location;
import com.stoffe.visma.room.WeatherDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Location.class},version = 6)
public abstract class HistoryDatabase extends RoomDatabase {

    public abstract WeatherDao myDao();
}
