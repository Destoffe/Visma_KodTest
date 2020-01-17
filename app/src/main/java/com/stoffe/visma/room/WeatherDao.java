package com.stoffe.visma.room;

import com.stoffe.visma.models.Location;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WeatherDao {

    @Insert
    public void addWeather(Location weather);

    @Query("select * from weathers order by id")
    public List<Location> getWeather();

    @Delete
    public void deleteWeater(Location location);

}
