package com.example.myapp.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by agrasagar89 on 11/28/2015.
 */

public class AllEvents extends AppCompatActivity {

    List<String> eventsNames=new ArrayList<String>();
    List<String> eventsIds=new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_events);

        getEvents(this);



    }
    private void getEvents(final Context context) {
        Log.d("SAGAR", "1111111111111111111111111111");
        String url = "http://130.233.42.124:8080/events";

        // Request a string response
        JsonArrayRequest jsonRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SAGAR", "2222222222222222222222222222");

                        // Result handling
                        Date rdate = new Date();
                        Log.d("SAGAR", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String name = (String) response.getJSONObject(i).get("name");
                                String start_date = (String) response.getJSONObject(i).get("start_date");
                                String end_date = (String) response.getJSONObject(i).get("end_date");
                                eventsNames.add(name);
                                Log.d("Sagar", name);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("SAGAR", eventsNames.toString());

                        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context, R.layout.textforlist, eventsNames);
                        ListView myList = (ListView) findViewById(R.id.events);
                        myList.setAdapter(myAdapter);

                        addShowEventCallBack(myList, response);

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
        Volley.newRequestQueue(this).add(jsonRequest);
    }

    public void addShowEventCallBack(ListView myList, final JSONArray response){
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SAGAR", String.valueOf(id));
                Intent intent = new Intent(AllEvents.this, EditEvents.class);
                intent.putExtra("name", eventsNames.get((int) id));
                try {
                    intent.putExtra("event", response.getJSONObject((int) id).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
