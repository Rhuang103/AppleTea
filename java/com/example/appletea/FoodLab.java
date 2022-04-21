package com.example.appletea;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A singleton class use for testing
 * It can generate a list of restuarants to test the app
 */

public class FoodLab {
    private static FoodLab sFoodLab;

    private List<Restaurant> mRestaurants;

    public static FoodLab get(Context context) {
        if(sFoodLab == null) {
            sFoodLab = new FoodLab(context);
        }
        return sFoodLab;
    }

    private FoodLab(Context context) {
        mRestaurants = new ArrayList<>();

        /**
         * Generates a list of filler restaurants
         */
        for(int i = 0; i < 100; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setTitle("Restaurant #" + i);
            mRestaurants.add(restaurant);
        }
    }

    public List<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public Restaurant getRestaurant(UUID id) {
        for (Restaurant restaurant : mRestaurants) {
            if(restaurant.getId().equals(id)) {
                return restaurant;
            }
        }

        return null;
    }
}
