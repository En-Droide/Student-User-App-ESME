package com.example.esme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends Activity {

    private static final String TAG = "CalendarActivity";
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy", Locale.FRANCE);
    Date date1 ;
    String datechoisie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
        calendarView.setOnDateChangeListener((new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+(month+1)+"/"+year;

                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    datechoisie= sdf.format(date1);
                    System.err.println(datechoisie);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d(TAG,"onSelectedDayChange: mm/dd/yyyy:"+datechoisie);


                Intent intent = new Intent(CalendarActivity.this,CoursCalendarActivity.class);
                intent.putExtra("date",datechoisie);
                startActivity(intent);
            }
        }));


    }
}