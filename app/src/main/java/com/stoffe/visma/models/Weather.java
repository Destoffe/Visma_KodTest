package com.stoffe.visma.models;
/**
 *Min main model för att ta hand om all databinding genom programmet. Sköter allt förutom stadens namn
 * som jag har i location istället för att lättare spara i SQLLite
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.Ignore;


public class Weather {

    @Ignore
    public Location loc = new Location();
    @Ignore
    public CurrentCondition currentCondition = new CurrentCondition();

    @Ignore
    public byte[] iconData;
    @Ignore
    public Bitmap bitmap;

    public Location getLoc() {
        return loc;
    }

    public CurrentCondition getCurrentCondition() {
        return currentCondition;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public class CurrentCondition{

        @Ignore
        private long timeStamp;
        private float temp = 0;

        private String descr;
        private String icon;

        private String day;
        private String dayNum;

        public String getDay() {
            return day;
        }

        public String getDayNum() {
            return dayNum;
        }

        public int getTemp() {
            return (int) Math.round((temp - 273.15));
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

    public void setBitmap() {
        if (iconData != null && iconData.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(iconData, 0, iconData.length);
        }
    }

}
