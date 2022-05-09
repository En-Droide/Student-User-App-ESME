package com.example.esme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Locale;

public class NewCoursActivity extends Activity {
    Button TimeButtonD,TimeButtonF,DateButton,ValidButton,AnnulButton;
    EditText Intitul;
    CheckBox chk;
    int hour,minute;
    private DatePickerDialog datePickerDialog;
    private String filepath;
    private File file_event;
    private String intitule,choix_date,heure_d,heure_f,importance;
    public static Eleve eleve=CalendarActivity.eleve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_event_cours);

        popDatePicker();
        Intitul=findViewById(R.id.Intitule_Event);
        TimeButtonD= findViewById(R.id.select_Hour_Debut_Event);
        TimeButtonF= findViewById(R.id.select_Hour_Fin_Event);
        DateButton= findViewById(R.id.Select_Date_Event);
        ValidButton=findViewById(R.id.button_valid);
        AnnulButton=findViewById(R.id.button_annul);
        chk = (CheckBox) findViewById(R.id.checkBox_Importance);

        Intitul.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus&&Intitul.getText().toString().equals("Intitulé")) {
                    Intitul.setText("");
                }
            }
        });
        ValidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intitule = Intitul.getText().toString();
                /*System.out.println((intitule==null));*/
                System.out.println("intitulé: "+intitule+" choix date: "+choix_date+" heure début: "+heure_d+" heure fin "+heure_f+"importance "+importance);
               if (intitule.equals("") || choix_date==null || choix_date.equals("") || heure_d==null ||heure_d.equals("")
                       || heure_f.equals("")|| heure_f==null || importance.equals("")|| importance==null) {
               }
               else {
                   class ObjectIOExample {
                       public void WriteObjectToFile(Cours serObj) {
                           try {
                               System.out.println("WriteObjectToFile");
                               FileOutputStream fileOut = new FileOutputStream(file_event);
                               ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                               objectOut.writeObject(serObj);
                               objectOut.close();
                               System.out.println("The Object  was succesfully written to a file");
                           } catch (Exception ex) {
                               ex.printStackTrace();
                           }
                       }
                       public Cours ReadObjectFromFile(String filepath) {
                           try {
                               FileInputStream fileIn = new FileInputStream(filepath);
                               ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                               Cours obj = (Cours)objectIn.readObject();

                               System.out.println("The Object has been read from the file");
                               objectIn.close();
                               return obj;
                           } catch (Exception ex) {
                               ex.printStackTrace();
                               return null;
                           }
                       }
                   }
                   filepath= getFilesDir().getAbsolutePath();
                   System.out.println(getFilesDir().getParent());
                   file_event = new File(filepath,"Test_Event.txt");

                   System.out.println("Writing_file_event");

                   ObjectIOExample objectIO = new ObjectIOExample();

                   Cours event = new Cours();
                   event.matiere=intitule;
                   event.date=choix_date;
                   event.heureDebut=heure_d;
                   event.heureFin=heure_f;
                   event.importance=importance;
                   event.estPerso=true;
                   event.salle="";
                   event.intitule="";

                   objectIO.WriteObjectToFile(event);
                    Intent intent = new Intent(NewCoursActivity.this, CalendarActivity.class);
                    startActivity(intent);
               }
            }
        });
        AnnulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCoursActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
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