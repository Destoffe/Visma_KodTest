package com.stoffe.visma.models;
/**
 * Modellen jag använder för ROOM för att kunna spara historik
 * Som sagt denna är lite simplare konstruerad än Weather så lättare att hantera.
 */

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

    public Location(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}