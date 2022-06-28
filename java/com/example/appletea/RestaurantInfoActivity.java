package com.example.appletea;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.fragment.app.Fragment;

import java.util.UUID;

/**
 * This activity calls the fragment RestaurantInfoFragment
 *
 * The fragment shows a full screen detail of the restaurant
 */

public class RestaurantInfoActivity extends SingleFragmentActivity {
    public static final String LOCATION_DATA = "com.example.appletea.mCurrentLocation";
    public static final String EXTRA_RESTAURANT_ID = "com.example.appletea.restaurant_id";

    public static final String EXTRA_ACTIVITY_INFO = "com.example.appletea.activity_info";

    private static final String TAG = "RestaurantInfoFragment";

    @Override
    protected Fragment createFragment() { return new RestaurantInfoFragment(); }

    /**
     * If we pass create a new Restaurant object, need to pass in location data
     */
    public static Intent newIntent(Context packageContext, UUID restaurantId, Location mCurrentLocation,
                                   String activityInfo ) {
        Intent intent = new Intent(packageContext, RestaurantInfoActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, restaurantId);
        intent.putExtra(LOCATION_DATA, mCurrentLocation);
        intent.putExtra(EXTRA_ACTIVITY_INFO, activityInfo);
        return intent;
    }

    /**
     * This it to pull up an existing Restaurant entry. No need to pass in the Location Data
     */
    public static Intent pullIntent(Context packageContext, UUID restaurantId,
                                    String activityInfo) {
        Intent intent = new Intent(packageContext, RestaurantInfoActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, restaurantId);
        intent.putExtra(EXTRA_ACTIVITY_INFO, activityInfo);
        return intent;
    }
}
