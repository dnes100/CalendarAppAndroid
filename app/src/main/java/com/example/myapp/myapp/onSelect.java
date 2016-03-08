package com.example.myapp.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by agrasagar89 on 11/29/2015.
 */
public class onSelect extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_events);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        Log.d("Sagar", "this" + date);
        getEvents(date, this);

    }

    private void getEvents(final String date,final Context context) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


       //final List<String> eventsIds=new ArrayList<String>();


        String url = "http://130.233.42.124:8080/events";

        // Request a string response
        JsonArrayRequest  jsonRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> eventNames=new ArrayList<String>();
                        Log.d("SAGAR", "2222222222222222222222222222");
                        // Result handling
                        Date rdate = new Date();
                        Log.d("SAGAR", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Log.d("SAGAR",date);

                                String d = response.getJSONObject(i).get("start_date").toString().split("T")[0];
                                Log.d("SAGAR", d.toString());
                                Log.d("SAGAR","IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
                                if(date.toString().compareTo(d.toString())==0){
                                    Log.d("SAGAR", "inside if");

                                    Log.d("SAGAR", response.getJSONObject(i).get("name").toString());
                                    eventNames.add(response.getJSONObject(i).get("name").toString());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        Log.d("SAGAR",eventNames.toString());
                        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context, R.layout.textforlist, eventNames);
                        ListView myList = (ListView) findViewById(R.id.events);
                        myList.setAdapter(myAdapter);
                        //addShowEventCallBack(myList, response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SAGAR", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                // Error handling
                Log.d("SAGAR", error.toString());
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });

        // Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);








    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

}


