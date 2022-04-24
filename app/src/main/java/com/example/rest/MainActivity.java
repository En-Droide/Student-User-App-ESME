package com.example.rest;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

=======
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< Updated upstream
        ArrayList<Product> listData = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, listData));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Product upload = (Product) o;
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("id", upload.getId());
                intent.putExtra("name", upload.getName());
                intent.putExtra("type", upload.getType());
                intent.putExtra("price", upload.getPrice());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Product> getListData(){
        ArrayList<Note> listData = getListData();
        final ListView listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(new CustomListAdapterNotes(this, listData));






    }
    public ArrayList<Note> getListData(){
>>>>>>> Stashed changes
        try{
            ConnectionRest connectionRest = new ConnectionRest();
            connectionRest.execute("GET");
            String listJsonObjs = connectionRest.get();
            if(listJsonObjs != null) {
<<<<<<< Updated upstream
                return connectionRest.parse(listJsonObjs);
=======
                return parse(listJsonObjs);
>>>>>>> Stashed changes
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
<<<<<<< Updated upstream

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
=======
    public ArrayList<Note> parse(final String json) {
        try {
            final ArrayList<Note> notes = new ArrayList<>();
            final JSONArray jNoteArray = new JSONArray(json);
            for (int i = 0; i < jNoteArray.length(); i++) {
                notes.add(new Note(jNoteArray.optJSONObject(i)));
            }
            return notes;
        } catch (JSONException e) {
            Log.v("TAG","[JSONException] e : " + e.getMessage());
        }
        return null;
>>>>>>> Stashed changes
    }
}