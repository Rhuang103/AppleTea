package com.example.appletea.database;

/**
 * Class to describe the database schema for Restaurant Objects
 */

public class FoodDbSchema {
    public static final class FoodTable {
        public static final String NAME = "restaurants";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DETAILS = "details";
            public static final String LAT = "Latitude";
            public static final String LONGT = "Longitude";
        }
    }
}


