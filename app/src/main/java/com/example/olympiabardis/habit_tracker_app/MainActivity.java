package com.example.olympiabardis.habit_tracker_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.olympiabardis.habit_tracker_app.data.HabitContract;
import com.example.olympiabardis.habit_tracker_app.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);
    }

    public Cursor readDb(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitContract.HabitEntry.COLUMN_HABIT_STATUS
        };

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private void displayDatabaseInfo(Cursor cursor){
        TextView displayView = (TextView) findViewById(R.id.habit_text_view);

        try{
            displayView.setText("The table contains " + cursor.getCount() + " exercises.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_STATUS + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int frequencyColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY);
            int statusColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_STATUS);

            while (cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentFrequency = cursor.getInt(frequencyColumnIndex);
                int currentStatus = cursor.getInt(statusColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentFrequency + " - " +
                        currentStatus));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertHabit(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, "Elliptical exercise");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_FREQUENCY, 6);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_STATUS, HabitContract.HabitEntry.HABIT_STATUS_DONE);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.insert_dummy_data:
                insertHabit();
                Cursor c = readDb();
                displayDatabaseInfo(c);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
