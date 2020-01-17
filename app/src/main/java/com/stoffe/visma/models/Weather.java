package com.stoffe.visma.models;

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

    public class CurrentCondition{
        public long timeStamp;
        public float temp = 0;

        private String descr;
        private String icon;
        public String day;
        public String dayNum;

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
