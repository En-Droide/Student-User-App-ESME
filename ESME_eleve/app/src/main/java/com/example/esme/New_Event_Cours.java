package com.example.esme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class New_Event_Cours extends Activity {

    Button TimeButtonD,TimeButtonF,DateButton;
    CheckBox chk;
    int hour,minute;
    private DatePickerDialog datePickerDialog;
    private String intitule,choix_date,heure_d,heure_f,importance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_cours);
        popDatePicker();
        TimeButtonD= findViewById(R.id.select_Hour_Debut_Event);
        TimeButtonF= findViewById(R.id.select_Hour_Fin_Event);
        DateButton= findViewById(R.id.Select_Date_Event);
        chk = (CheckBox) findViewById(R.id.checkBox_Importance);

    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            importance="Important";
                }
        else{
            importance="Non Important";
                }
    };


    private void popDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String date = String.format("%02d",(day))+"/"+String.format("%02d",(month+1))+"/"+year;
                DateButton.setText(date);
                choix_date=date;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog= new DatePickerDialog(this, style, dateSetListener, year, month, day );
    }

    public void popDatePicker(View view) {

        datePickerDialog.show();

    }

    public void popTimePickerDebut(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMin) {
                hour = selectHour;
                minute =selectMin;
                TimeButtonD.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                heure_d=String.format(Locale.getDefault(),"%02d:%02d",hour,minute);
            }
        };

        int style= AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Heure Debut");
        timePickerDialog.show();
    }

    public void popTimePickerFin(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMin) {
                hour = selectHour;
                minute =selectMin;
                TimeButtonF.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
                heure_f=String.format(Locale.getDefault(),"%02d:%02d",hour,minute);
            }
        };

        int style= AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("Heure Fin");
        timePickerDialog.show();
    }

}