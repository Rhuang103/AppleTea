package com.example.appletea;

import java.util.UUID;

public class Restaurant {

    private UUID mID;
    private String mTitle;
    private String mBestFood;
    private String mLatitude;
    private String mLongitude;

    public String getTitle() { return mTitle; }
    public void setTitle(String title) { mTitle = title; }

    public String getBestFood() { return mBestFood; }
    public void setBestFood(String food) { mBestFood = food; }

    public String getLatitude() { return mLatitude; }
    public void setLatitude(String latitude) { mLatitude = latitude; }

    public String getLongitude() { return mLongitude; }
    public void setLongitude(String longitude) { mLongitude = longitude; }



    public UUID getId() {return mID;}
    public Restaurant() { this(UUID.randomUUID()); }
    public Restaurant(UUID id) {
        mID = id;
    }
}
