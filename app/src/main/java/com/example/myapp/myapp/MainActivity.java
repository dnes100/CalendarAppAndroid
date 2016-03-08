package com.example.myapp.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private HashMap<Date, Integer> backgroundColorMap = new HashMap<Date, Integer>();
    private HashMap<Date, Integer> textColorMap = new HashMap<Date, Integer>();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Calendar App", Toast.LENGTH_SHORT).show();
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //calendar


        //Caldroid fragment deleted from here and put in getevents


        Log.d("SAGAR", "000000000000000000000000000000*!1!!");
        getEvents();
        Log.d("SAGAR", "333333333333333333333333!!!");
        //calendar---

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                aboutUs();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Select One!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
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
       /* if (id == R.id.action_settings) {
            return true;
        }*/
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems() {
        String[] osArray = {"All Events", "New Events", "Import From Device", "Export To Device", "Exit"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Calendar App", Toast.LENGTH_SHORT).show();
                Log.d("SAGAR", String.valueOf(id));
                Log.d("SAGAR", "parent: " + parent.toString());
                Log.d("SAGAR", "view: " + view.toString());

                if ((id == 0) || (id == 3)) {
                    Toast.makeText(MainActivity.this, "All Events", Toast.LENGTH_SHORT).show();
                    Log.d("SAGAR", String.valueOf(id));
                    Log.d("SAGAR", "BBBBBB");
                    Intent intent = new Intent(MainActivity.this, AllEvents.class);
                    startActivity(intent);
                    finish();
                }
                if (id == 1) {
                    Toast.makeText(MainActivity.this, "New Events", Toast.LENGTH_SHORT).show();
                    Log.d("SAGAR", String.valueOf(id));
                    Log.d("SAGAR", "BBBBBB");
                    Intent intent = new Intent(MainActivity.this, NewEvent.class);
                    startActivity(intent);
                    finish();
                }
                if (id == 2) {
                    Toast.makeText(MainActivity.this, "Importing", Toast.LENGTH_SHORT).show();
                    Log.d("SAGAR", String.valueOf(id));
                    Log.d("SAGAR", "BBBBBB");
                    Intent intent = new Intent(MainActivity.this, ImportFrom.class);
                    startActivity(intent);
                    finish();
                }
                if (id == 4) {
                    Toast.makeText(MainActivity.this, "Exit" +
                            "" +
                            "" +
                            "", Toast.LENGTH_SHORT).show();
                    Log.d("SAGAR", String.valueOf(id));
                    Log.d("SAGAR", "BBBBBB");
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void getEvents() {
        Log.d("SAGAR", "1111111111111111111111111111");
        String url = "http://130.233.42.124:8080/events";

        // Request a string response
        JsonArrayRequest  jsonRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SAGAR", "2222222222222222222222222222");
                        // Result handling
                        Date rdate = new Date();
                        Log.d("SAGAR", response.toString());
                        for (int i = 0; i <= response.length(); i++) {

                            try {
                                Log.d("SAGAR", "2015-11-" + (String) response.getJSONObject(i).toString());
                                Log.d("SAGAR", "2015-11-" + (String) response.getJSONObject(i).get("start_date"));
                                rdate = df.parse((String) response.getJSONObject(i).get("start_date"));

                                Log.d("SAGAR", "2015-11-" + Integer.toString(i + 2));
                                Log.d("SAGAR", rdate.toString());
                                backgroundColorMap.put(rdate, R.color.blue);
                                textColorMap.put(rdate, R.color.dark);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("SAGAR", backgroundColorMap.toString());
                        }

                        //caldroid fragment
                        CaldroidFragment caldroidFragment = new CaldroidFragment();
                        Bundle args = new Bundle();
                        Calendar cal = Calendar.getInstance();
                        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
                        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
                        caldroidFragment.setArguments(args);

                        caldroidFragment.setBackgroundResourceForDates(backgroundColorMap);
                        caldroidFragment.setTextColorForDates(textColorMap);

                        setCaldroidCallback(caldroidFragment);

                        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                        t.replace(R.id.calendar1, caldroidFragment);
                        t.commit();



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
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.myapp.myapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.myapp.myapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void setCaldroidCallback(CaldroidFragment caldroidFragment){
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                //Toast.makeText(getApplicationContext(), formatter.format(date),
                        //Toast.LENGTH_SHORT).show();
                Log.d("Sagar", "Inside onSelectDate");
                Log.d("Sagar", "this" + date.toString());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String dateString = df.format(date);
                Log.d("Sagar", "this" + dateString);
                Intent intent = new Intent(MainActivity.this, onSelect.class);
                intent.putExtra("date",dateString);
                startActivity(intent);
                finish();

            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;

               // Toast.makeText(getApplicationContext(), text,
                    //    Toast.LENGTH_SHORT).show();
                Log.d("Sagar", "Inside onChangeMonth");
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                //Toast.makeText(getApplicationContext(),
                      //  "Long click " + formatter.format(date),
                       // Toast.LENGTH_SHORT).show();
               Log.d("Sagar", "Inside OnlongClickDate");
            }


            @Override
            public void onCaldroidViewCreated() {
                Toast.makeText(getApplicationContext(),
                        "Welcome",
                        Toast.LENGTH_SHORT).show();
            }

        };

        caldroidFragment.setCaldroidListener(listener);

    }

    public void aboutUs(){
        Intent intent = new Intent(MainActivity.this, aboutUs.class);
        startActivity(intent);
        finish();
    }

}