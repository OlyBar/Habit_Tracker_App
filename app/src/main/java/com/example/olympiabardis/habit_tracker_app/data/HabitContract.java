package com.example.olympiabardis.habit_tracker_app.data;

import android.provider.BaseColumns;

/**
 * Created by olympiabardis on 5/8/17.
 */

public final class HabitContract{

    private HabitContract(){}

    //Constant values for habit tracker database table

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habit";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_HABIT_NAME = "name";

        public final static String COLUMN_HABIT_FREQUENCY = "frequency";

        public final static String COLUMN_HABIT_STATUS = "status";

        public static final int HABIT_STATUS_UNKNOWN = 0;

        public static final int HABIT_STATUS_DONE = 1;

        public static final int HABIT_STATUS_NOT_DONE = 2;
    }
}
