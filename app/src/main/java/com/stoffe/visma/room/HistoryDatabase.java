package com.stoffe.visma.room;

/**
 * Sätter upp databasen.
 */

import com.stoffe.visma.models.Location;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Location.class},version = 12)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract WeatherDao myDao();
}
