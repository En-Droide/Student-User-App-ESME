package com.example.esme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
        calendarView.setOnDateChangeListener((new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month+1)+"/"+dayOfMonth+"/"+year;
                Log.d(TAG,"onSelectedDayChange: mm/dd/yyyy:"+date);


                Intent intent = new Intent(CalendarActivity.this,CoursCalendarActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        }));


    }
}