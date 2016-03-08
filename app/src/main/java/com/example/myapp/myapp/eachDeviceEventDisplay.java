package com.example.myapp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by agrasagar89 on 11/29/2015.
 */
public class eachDeviceEventDisplay extends AppCompatActivity {

    String myFormat = "yyyy-MM-dd"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    SimpleDateFormat tdf = new SimpleDateFormat("HH:mm", Locale.US);
    String name;
    String dateString;
    String timeString;
    String description;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_event_show);

        Intent intent = getIntent();
        name= intent.getStringExtra("eventName");
        String sdate=intent.getStringExtra("startDate");
        Date date = new Date(Long.parseLong(sdate));
        dateString = sdf.format(date);
        timeString = tdf.format(date);
        Log.d("SAGAR",date.toString());
        description= intent.getStringExtra("description");

        Log.d("SAGAR", "deviceeeeeeeeeeeeeeeeeevent");
        //Log.d("SAGAR", eventString);

            //Map<String, String> event = (HashMap<String, String>)eventString;

            TextView namesh= (TextView)findViewById(R.id.nameInput);
            namesh.setText(name.toString());

           TextView startDate= (TextView)findViewById(R.id.startInput);
           startDate.setText(dateString);

           TextView descriptionShow= (TextView)findViewById(R.id.descriptionInput);
            descriptionShow.setText(description);

            TextView startTime= (TextView)findViewById(R.id.timeInput);
            startTime.setText(timeString);



    }
    public void ImportToDb(View button){
        String url = "http://130.233.42.124:8080/events/";
        //Map<String, String> params = new HashMap<String, String>();
        JSONObject params = new JSONObject();
        try {
            params.put("name",name.toString());
            params.put("start_date", dateString);
            params.put("start_time", timeString);
            params.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a string response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,url, params,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("SAGAR","mOMOMOMOMOMO");
                Log.d("SAGAR",response.toString());


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
        startActivity(new Intent(this, AllEvents.class));
        this.finish();



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DeviceEvents.class));
        this.finish();
    }
}
