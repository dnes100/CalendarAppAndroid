package com.example.myapp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;

/**
 * Created by agrasagar89 on 11/29/2015.
 */
public class ExportTo extends AppCompatActivity {

    String name;
    String start_date;
    String start_time;
    String description;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_to);

        Intent intent = getIntent();
        name= intent.getStringExtra("eventName");
        start_date=intent.getStringExtra("startDate");
        start_time=intent.getStringExtra("startTime");
        description= intent.getStringExtra("description");

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
        ListView myList = (ListView) findViewById(R.id.ExportEventsList);
        myList.setAdapter(myAdapter);
        addListViewCallBack(myList, calendars);


    }
    public void addListViewCallBack(ListView myList,  final List<Calendar> calendars){

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SAGAR", String.valueOf(id));
                Log.d("SAGAR", "Entering export.........................");

               /* String name;
                String start_date;
                String start_time;
                String description; */
                ///////////////////////////////

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");


                try {
                    Date newstartdate;
                    Date newenddate;
                    newstartdate = df.parse(start_date + " " + start_time );
                    GregorianCalendar mycalendar=new GregorianCalendar();
                    mycalendar.setTime(newstartdate);
                    GregorianCalendar myendcalendar=new GregorianCalendar();
                    //newenddate = df.parse(json_data.getString("end_timestamp").toString());
                   // myendcalendar.setTime(newenddate);

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events._ID, calendars.get((int) id).id)
                            .putExtra(CalendarContract.Events.ACCOUNT_NAME, calendars.get((int) id).accountName)
                            .putExtra(CalendarContract.Events._SYNC_ID, calendars.get((int) id).syncId)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, mycalendar.getTimeInMillis())
                          //  .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, myendcalendar.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, name)
                            .putExtra(CalendarContract.Events.DESCRIPTION, description);

                    startActivity(intent);

                }
                catch (ParseException e){

                }


                //////////////////////////////


                //redirect
               // startActivity(new Intent(ExportTo.this, AllEvents.class));
                //finish();

            }
        });
    }
}
