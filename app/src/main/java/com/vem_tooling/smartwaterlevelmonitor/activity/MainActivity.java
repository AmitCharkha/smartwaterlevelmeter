package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.scroll_view.MultiScrollNumber;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView refreshTextView,errorTextView;
    private RelativeLayout relativeLayoutOne,relativeLayoutTwo,relativeLayoutThree,relativeLayoutFour,relativeLayoutFive,relativeLayoutSix;
    private MultiScrollNumber scroll_number1,scroll_number2,scroll_number3,scroll_number4,scroll_number5,scroll_number6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        refreshTextView = (TextView) findViewById(R.id.refreshTextView);
        errorTextView = (TextView) findViewById(R.id.error);

        relativeLayoutOne = (RelativeLayout) findViewById(R.id.relativeLayoutOne);
        relativeLayoutTwo = (RelativeLayout) findViewById(R.id.relativeLayoutTwo);
        relativeLayoutThree = (RelativeLayout) findViewById(R.id.relativeLayoutThree);
        relativeLayoutFour = (RelativeLayout) findViewById(R.id.relativeLayoutFour);
        relativeLayoutFive = (RelativeLayout) findViewById(R.id.relativeLayoutFive);
        relativeLayoutSix = (RelativeLayout) findViewById(R.id.relativeLayoutSix);

        scroll_number1 = (MultiScrollNumber) findViewById(R.id.scroll_number1);
        scroll_number2 = (MultiScrollNumber) findViewById(R.id.scroll_number2);
        scroll_number3 = (MultiScrollNumber) findViewById(R.id.scroll_number3);
        scroll_number4 = (MultiScrollNumber) findViewById(R.id.scroll_number4);
        scroll_number5 = (MultiScrollNumber) findViewById(R.id.scroll_number5);
        scroll_number6 = (MultiScrollNumber) findViewById(R.id.scroll_number6);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(new SmartDeviceSharedPreferences(getApplicationContext()).getIsAdmin() == 1){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_user_drawer);
        }
        navigationView.setNavigationItemSelectedListener(this);

        refreshTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntVal();
            }
        });

        getIntVal();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(MainActivity.this, SettingScreen.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(new SmartDeviceSharedPreferences(getApplicationContext()).getIsAdmin() == 1) {
            if (id == R.id.history) {
                Intent intent = new Intent(MainActivity.this, HistoryTemp.class);
                startActivity(intent);
            } else if (id == R.id.calibrate_sensor) {
                Intent intent = new Intent(MainActivity.this, CalibrateSensor.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityName", "Dashboard");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else{
            if (id == R.id.history) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setValue(int no,int val){

        switch (no){
            case  1:
                scroll_number1.setNumber(val);
                scroll_number1.setTextSize(80);
                break;
            case  2:
                scroll_number2.setNumber(val);
                scroll_number2.setTextSize(80);
                break;
            case  3:
                scroll_number3.setNumber(val);
                scroll_number3.setTextSize(80);
                break;
            case  4:
                scroll_number4.setNumber(val);
                scroll_number4.setTextSize(80);
                break;
            case  5:
                scroll_number5.setNumber(val);
                scroll_number5.setTextSize(80);
                break;
            case  6:
                scroll_number6.setNumber(val);
                scroll_number6.setTextSize(80);
                break;

        }
    }

    void getIntVal(){
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        try{
            progress.setMessage("Please Wait...");
            progress.show();
            refreshTextView.setEnabled(true);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_WATER_LEVEL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if(response == null || response.equalsIgnoreCase("")){
                            if(progress != null){
                                progress.cancel();
                            }
                            Toast.makeText(getApplicationContext(),"No response from API", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String test1[] = response.split(",");
                        StringBuilder str2 = new StringBuilder(test1[0].trim());
                        test1[0] = str2.deleteCharAt(str2.indexOf("(")).toString();
                        str2 = new StringBuilder(test1[11].trim());
                        test1[11] = str2.deleteCharAt(str2.indexOf(")")).toString();
                        if(Integer.parseInt(test1[1].trim()) != 0){
                            relativeLayoutOne.setVisibility(View.VISIBLE);
                            setValue(Integer.parseInt(test1[0].trim()),Integer.parseInt(test1[1].trim()));
                        }else{
                            relativeLayoutOne.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[3].trim()) != 0){
                            relativeLayoutTwo.setVisibility(View.VISIBLE);
                            setValue(Integer.parseInt(test1[2].trim()),Integer.parseInt(test1[3].trim()));
                        }else{
                            relativeLayoutTwo.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[5].trim()) != 0){
                            relativeLayoutThree.setVisibility(View.VISIBLE);
                            setValue(Integer.parseInt(test1[4].trim()),Integer.parseInt(test1[5].trim()));
                        }else{
                            relativeLayoutThree.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[7].trim()) != 0){
                            relativeLayoutFour.setVisibility(View.VISIBLE);
                            setValue(Integer.parseInt(test1[6].trim()),Integer.parseInt(test1[7].trim()));
                        }else{
                            relativeLayoutFour.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[9].trim()) != 0){
                            relativeLayoutFive.setVisibility(View.VISIBLE);
                            setValue(Integer.parseInt(test1[8].trim()),Integer.parseInt(test1[9].trim()));
                        }else{
                            relativeLayoutFive.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[11].trim()) != 0){
                            relativeLayoutSix.setVisibility(View.VISIBLE);
                            setValue(Integer.parseInt(test1[10].trim()),Integer.parseInt(test1[11].trim()));
                        }else{
                            relativeLayoutSix.setVisibility(View.GONE);
                        }

                        if(progress != null){
                            progress.cancel();
                        }
                        refreshTextView.setEnabled(true);
                    }catch (Exception e){
                        if(progress != null){
                            progress.cancel();
                        }
                        errorTextView.setText(e.toString() + " \n\n " + response );
                        Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
                        refreshTextView.setEnabled(true);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(progress != null){
                        progress.cancel();
                    }
                    Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
                    error.getMessage();
                    errorTextView.setText( error.getMessage());
                    refreshTextView.setEnabled(true);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch (Exception e){
            if(progress != null){
                progress.cancel();
            }
            errorTextView.setText(e.toString());
            Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
            refreshTextView.setEnabled(true);
        }
    }


    void getIntValue(){

        try {
            ProgressDialog progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Please Wait...");
            progress.show();
            String response = "( 1,25,2, 50,3,75, 4,0,5, 0,6, 0)";
            errorTextView.setText(response);
            String test1[] = response.split(",");
            StringBuilder str2 = new StringBuilder(test1[0].trim());
            test1[0] = str2.deleteCharAt(str2.indexOf("(")).toString();
            str2 = new StringBuilder(test1[11].trim());
            test1[11] = str2.deleteCharAt(str2.indexOf(")")).toString();
            if (Integer.parseInt(test1[1].trim()) != 0) {
                relativeLayoutOne.setVisibility(View.VISIBLE);
                setValue(Integer.parseInt(test1[0].trim()), Integer.parseInt(test1[1].trim()));
            } else {
                relativeLayoutOne.setVisibility(View.GONE);
            }

            if (Integer.parseInt(test1[3].trim()) != 0) {
                relativeLayoutTwo.setVisibility(View.VISIBLE);
                setValue(Integer.parseInt(test1[2].trim()), Integer.parseInt(test1[3].trim()));
            } else {
                relativeLayoutTwo.setVisibility(View.GONE);
            }

            if (Integer.parseInt(test1[5].trim()) != 0) {
                relativeLayoutThree.setVisibility(View.VISIBLE);
                setValue(Integer.parseInt(test1[4].trim()), Integer.parseInt(test1[5].trim()));
            } else {
                relativeLayoutThree.setVisibility(View.GONE);
            }

            if (Integer.parseInt(test1[7].trim()) != 0) {
                relativeLayoutFour.setVisibility(View.VISIBLE);
                setValue(Integer.parseInt(test1[6].trim()), Integer.parseInt(test1[7].trim()));
            } else {
                relativeLayoutFour.setVisibility(View.GONE);
            }

            if (Integer.parseInt(test1[9].trim()) != 0) {
                relativeLayoutFive.setVisibility(View.VISIBLE);
                setValue(Integer.parseInt(test1[8].trim()), Integer.parseInt(test1[9].trim()));
            } else {
                relativeLayoutFive.setVisibility(View.GONE);
            }

            if (Integer.parseInt(test1[11].trim()) != 0) {
                relativeLayoutSix.setVisibility(View.VISIBLE);
                setValue(Integer.parseInt(test1[10].trim()), Integer.parseInt(test1[11].trim()));
            } else {
                relativeLayoutSix.setVisibility(View.GONE);
            }

            if (progress != null) {
                progress.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
