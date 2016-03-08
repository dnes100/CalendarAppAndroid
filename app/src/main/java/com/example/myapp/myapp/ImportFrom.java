package com.example.myapp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;

/**
 * Created by agrasagar89 on 11/29/2015.
 */
public class ImportFrom extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list);

        CalendarProvider calendarProvider = new CalendarProvider(this);
        Log.d("SAGAR", "Calendar List");
        List<Calendar> calendars = calendarProvider.getCalendars().getList();
        Log.d("SAGAR", calendars.toString());

        List<String> calendarNames=new ArrayList<String>();
        List<String> calendarIds=new ArrayList<String>();
        for (int i = 0; i < calendars.size(); i++) {
            String name = (String) calendars.get(i).name;
            String id = String.valueOf(calendars.get(i).id);

            calendarNames.add(name);
            calendarIds.add(id);
        }
        Log.d("Sagar", calendarNames.toString());
        Log.d("Sagar", calendarIds.toString());

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.textforlist, calendarNames);
        ListView myList = (ListView) findViewById(R.id.calendarList);
        myList.setAdapter(myAdapter);
        addListViewCallBack(myList, calendars);

    }

    public void addListViewCallBack(ListView myList, final List<Calendar> calendars){
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SAGAR", String.valueOf(id));
                Intent intent = new Intent(ImportFrom.this, DeviceEvents.class);
                intent.putExtra("id", String.valueOf(calendars.get((int) id).id));
                startActivity(intent);
                finish();
            }
        });
    }
}
