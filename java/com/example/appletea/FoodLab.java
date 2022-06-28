package com.example.appletea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appletea.database.FoodBaseHelper;
import com.example.appletea.database.FoodCursorWrapper;
import com.example.appletea.database.FoodDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A singleton class use for testing
 * It can generate a list of restuarants to test the app
 */

public class FoodLab {
    private static FoodLab sFoodLab;

    //Creates the database
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static FoodLab get(Context context) {
        if(sFoodLab == null) {
            sFoodLab = new FoodLab(context);
        }
        return sFoodLab;
    }

    private FoodLab(Context context) {
        //creates the database
        mContext = context.getApplicationContext();
        mDatabase = new FoodBaseHelper(mContext).getWritableDatabase();
    }

    public void addRestaurant(Restaurant r) {
        ContentValues values = getContentValues(r);

        mDatabase.insert(FoodDbSchema.FoodTable.NAME, null, values);
    }

    public List<Restaurant> getRestaurants() {
        //return new ArrayList<>();
        List<Restaurant> restaurants = new ArrayList<>();

        FoodCursorWrapper cursor = queryFood(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                restaurants.add(cursor.getRestaurant());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return restaurants;
    }

    public Restaurant getRestaurant(UUID id) {
        //return null;
        FoodCursorWrapper cursor = queryFood(
                FoodDbSchema.FoodTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if(cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getRestaurant();
        } finally {
            cursor.close();
        }
    }

    public void updateRestaurant(Restaurant restaurant) {
        String uuidString = restaurant.getId().toString();
        ContentValues values = getContentValues(restaurant);

        mDatabase.update(FoodDbSchema.FoodTable.NAME, values,
                FoodDbSchema.FoodTable.Cols.UUID + " =?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Restaurant restaurant) {
        ContentValues values = new ContentValues();
        values.put(FoodDbSchema.FoodTable.Cols.UUID, restaurant.getId().toString());
        values.put(FoodDbSchema.FoodTable.Cols.TITLE, restaurant.getTitle());
        values.put(FoodDbSchema.FoodTable.Cols.DETAILS, restaurant.getDetails());
        values.put(FoodDbSchema.FoodTable.Cols.LAT, restaurant.getLatitude());
        values.put(FoodDbSchema.FoodTable.Cols.LONGT, restaurant.getLongitude());

        return values;
    }

    private FoodCursorWrapper queryFood(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                FoodDbSchema.FoodTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new FoodCursorWrapper(cursor);
    }
}
