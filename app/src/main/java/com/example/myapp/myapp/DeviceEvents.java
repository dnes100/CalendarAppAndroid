package com.example.myapp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;

/**
 * Created by agrasagar89 on 11/29/2015.
 */
public class DeviceEvents extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_events);

        Log.d("SAGAR", "Device events List");
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        Log.d("SAGAR", id);

        CalendarProvider calendarProvider = new CalendarProvider(this);

        List<Event> events = (List<Event>) calendarProvider.getEvents(Long.parseLong(id)).getList();
        Log.d("SAGAR", String.valueOf(events.toString()));

        List<String> eventNames = new ArrayList<String>();

        for (int i = 0; i < events.size(); i++) {
            String name = events.get(i).title;
            eventNames.add(name);
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.textforlist, eventNames);
        ListView myList = (ListView) findViewById(R.id.deviceEventsList);
        myList.setAdapter(myAdapter);
        addListViewCallBack(myList, events);


    }
    public void addListViewCallBack(ListView myList, final List<Event> allEvents){

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SAGAR", String.valueOf(id));
                Intent intent = new Intent(DeviceEvents.this, eachDeviceEventDisplay.class);
                Event event=allEvents.get((int) id);
                Log.d("SAGAR", "blablabla");
                //Log.d("SAGAR",event.toString());
                intent.putExtra("eventName", event.title);
                intent.putExtra("startDate", String.valueOf(event.dTStart));
                intent.putExtra("description", event.description);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ImportFrom.class));
        this.finish();
    }
}
