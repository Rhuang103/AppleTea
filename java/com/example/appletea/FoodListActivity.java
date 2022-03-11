package com.example.appletea;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class FoodListActivity extends SingleFragmentActivity {
    public static final String LOCATION_DATA = "com.example.appletea.mCurrentLocation";
    private static final String TAG = "FoodTeaFragment";

    @Override
    protected Fragment createFragment() {
        return new FoodListFragment();
    }

    public static Intent newIntent(Context packageContext, Location mCurrentLocation) {
        Intent intent = new Intent(packageContext, FoodListActivity.class);
        intent.putExtra(LOCATION_DATA, mCurrentLocation);
        return intent;
    }



}
