package com.example.myapp.myapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by agrasagar89 on 11/28/2015.
 */
public class NewEvent extends AppCompatActivity {
    EditText nameShow;
    EditText start_dateShow;
    EditText end_dateShow;
    EditText descriptionShow;
    EditText startTimeShow;
    EditText endTimeShow;
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        Intent intent = getIntent();

        nameShow= (EditText)findViewById(R.id.TextName);

        String start="";
        start_dateShow= (EditText)findViewById(R.id.NewStart_Date);
        start_dateShow.setInputType(InputType.TYPE_NULL);
        start_dateShow.requestFocus();
        dateStartdate(start_dateShow, this, start);

        String end="";
        end_dateShow = (EditText)findViewById(R.id.NewEnd_Date);
        end_dateShow.setText(end);
        end_dateShow.setInputType(InputType.TYPE_NULL);
        end_dateShow.requestFocus();
        dateStartdate(end_dateShow, this, end);

        startTimeShow = (EditText)findViewById(R.id.NewStart_Time);

        // dateSetTime(startTimeShow, this, event.getString("start_time").split("T")[1]);

        endTimeShow = (EditText)findViewById(R.id.NewEnd_Time);
         //dateSetTime(endTimeShow, this, event.getString("end_time").split("T")[1]);


        descriptionShow= (EditText)findViewById(R.id.NewDescription);



    }
    public void dateStartdate(final EditText date_dateShow,Context context, String dateString){
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {


                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                date_dateShow.setText(sdf.format(myCalendar.getTime()));

            }

        };

        date_dateShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    ///////////////////////////////////////
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        public int hour;
        public int min;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            this.hour=hourOfDay;
            this.min=minute;

        }

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }


    /////////////////////////////////////////////

    public void updateEvent(View button) {
        // Do click handling here
        Log.d("SAGAR","HIIIIIIII");
        String u=nameShow.getText().toString();
        Log.d("SAGAR", u);
        Log.d("SAGAR","HIIIIIIIIIIIII");

        String url = "http://130.233.42.124:8080/events/";
        //Map<String, String> params = new HashMap<String, String>();
        JSONObject params = new JSONObject();
        try {
            params.put("name",nameShow.getText().toString());
            params.put("start_date", start_dateShow.getText().toString());
            params.put("end_date", end_dateShow.getText().toString());
            params.put("start_time", startTimeShow.getText().toString());
            params.put("end_time", endTimeShow.getText().toString());
            params.put("description", descriptionShow.getText().toString());
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
        startActivity(new Intent(this, MainActivity.class));
        this.finish();

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, AllEvents.class));
        this.finish();
    }
}


