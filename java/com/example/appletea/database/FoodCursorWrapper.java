package com.example.appletea.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.appletea.Restaurant;

import java.util.UUID;

/**
 * To save the trouble of typing the cursor command for everytime we use the database
 */

public class FoodCursorWrapper extends CursorWrapper {
    public FoodCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Restaurant getRestaurant() {
        String uuidString = getString(getColumnIndex(FoodDbSchema.FoodTable.Cols.UUID));
        String title = getString(getColumnIndex(FoodDbSchema.FoodTable.Cols.TITLE));
        String details = getString(getColumnIndex(FoodDbSchema.FoodTable.Cols.DETAILS));
        String latitude = getString(getColumnIndex(FoodDbSchema.FoodTable.Cols.LAT));
        String longitude = getString(getColumnIndex(FoodDbSchema.FoodTable.Cols.LONGT));

        Restaurant restaurant = new Restaurant(UUID.fromString(uuidString));
        restaurant.setTitle(title);
        restaurant.setDetails(details);
        restaurant.setLatitude(latitude);
        restaurant.setLongitude(longitude);

        return restaurant;
    }

    

}


