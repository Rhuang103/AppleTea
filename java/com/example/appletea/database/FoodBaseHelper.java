package com.example.appletea.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appletea.database.FoodDbSchema.FoodTable;

/**
 * Helper class that helps create or load an existing database for you
 *
 * For prototyping, its better to delete the existing database and recreate it then coding out
 * onUpgrade()
 */

public class FoodBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME="foodBase.db";

    public FoodBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FoodTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                FoodTable.Cols.UUID + ", " +
                FoodTable.Cols.TITLE + ", " +
                FoodTable.Cols.DETAILS + ", " +
                FoodTable.Cols.LAT + ", " +
                FoodTable.Cols.LONGT +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
