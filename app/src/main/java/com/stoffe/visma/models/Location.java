package com.stoffe.visma.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weathers")
public class Location{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="city")
    private String city = "Enter City";
    private String country;

    public String day;
    public String dayNum;

    private long timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimeStamp(long timeStamp) {
        Date date = new java.util.Date(timeStamp*1000L);
        DateFormat df = new SimpleDateFormat("EEE");
        DateFormat df2 = new SimpleDateFormat("dd");
        df.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
        df2.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
        day = df.format(date).toUpperCase();
        dayNum = df2.format(date);
        this.timeStamp = timeStamp;
    }

}