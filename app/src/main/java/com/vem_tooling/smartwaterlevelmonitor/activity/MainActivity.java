package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.adapter.TankAdapter;
import com.vem_tooling.smartwaterlevelmonitor.db.SmartDeviceDB;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryRequestVO;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryVO;
import com.vem_tooling.smartwaterlevelmonitor.vo.TankVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

//import static com.vem_tooling.smartwaterlevelmonitor.R.id.error;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LatoRegularTextView refreshTextView,lastSyncTextView;
    //private RelativeLayout relativeLayoutOne,relativeLayoutTwo,relativeLayoutThree,relativeLayoutFour,relativeLayoutFive,relativeLayoutSix;
    //private MultiScrollNumber scroll_number1,scroll_number2,scroll_number3,scroll_number4,scroll_number5,scroll_number6;

    private int tankNo = 1;
    private int startValue, endValue;
    private RecyclerView recyclerView;
    private TankAdapter tankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        refreshTextView = (LatoRegularTextView) findViewById(R.id.refreshTextView);
        lastSyncTextView = (LatoRegularTextView) findViewById(R.id.lastSyncTextView);
        recyclerView = (RecyclerView)findViewById(R.id.tankList);
        //errorTextView = (TextView) findViewById(error);

       /* relativeLayoutOne = (RelativeLayout) findViewById(R.id.relativeLayoutOne);
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
        scroll_number6 = (MultiScrollNumber) findViewById(R.id.scroll_number6);*/

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
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                    getActualValue();
                }else{
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops..")
                            .setContentText("You are not connected with tank wifi. Please connect and retry.")
                            .show();
                }
            }
        });

        updateSyncTextView();

        if(Constant.checkInternetConnection(getApplicationContext())) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)){
                getActualValue();
            }else {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Info")
                        .setContentText("You are not connected with tank wifi.")
                        .show();

                getDBValue();
            }
            //doTesting();
        }else{
            Toast.makeText(getApplicationContext(),"Connect with tank wifi to get updated information",Toast.LENGTH_LONG).show();
            getDBValue();
        }
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
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
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

    TankVO setValue(int no,int val){

        /*switch (no){
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

        }*/

        TankVO tankVO = new TankVO();
        tankVO.setTankNo(no);
        tankVO.setPercentage(val);

        return tankVO;
    }

    void getActualValue(){
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        try{
            progress.setMessage("Please Wait...");
            progress.show();
            refreshTextView.setEnabled(true);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_WATER_LEVEL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        List<TankVO> tankVOs = new ArrayList<>();
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
                        //if(Integer.parseInt(test1[1].trim()) != 0){
                        //relativeLayoutOne.setVisibility(View.VISIBLE);
                        tankVOs.add(setValue(Integer.parseInt(test1[0].trim()),Integer.parseInt(test1[1].trim())));
                        /*}else{
                            relativeLayoutOne.setVisibility(View.GONE);
                        }*/

                        //if(Integer.parseInt(test1[3].trim()) != 0){
                        //relativeLayoutTwo.setVisibility(View.VISIBLE);
                        tankVOs.add(setValue(Integer.parseInt(test1[2].trim()),Integer.parseInt(test1[3].trim())));
                        /*}else{
                            relativeLayoutTwo.setVisibility(View.GONE);
                        }*/

                        //if(Integer.parseInt(test1[5].trim()) != 0){
                        //relativeLayoutThree.setVisibility(View.VISIBLE);
                        tankVOs.add(setValue(Integer.parseInt(test1[4].trim()),Integer.parseInt(test1[5].trim())));
                        /*}else{
                            relativeLayoutThree.setVisibility(View.GONE);
                        }*/

                        //if(Integer.parseInt(test1[7].trim()) != 0){
                        //relativeLayoutFour.setVisibility(View.VISIBLE);
                        tankVOs.add(setValue(Integer.parseInt(test1[6].trim()),Integer.parseInt(test1[7].trim())));
                        /*}else{
                            relativeLayoutFour.setVisibility(View.GONE);
                        }
*/
                        //if(Integer.parseInt(test1[9].trim()) != 0){
                        //relativeLayoutFive.setVisibility(View.VISIBLE);
                        tankVOs.add(setValue(Integer.parseInt(test1[8].trim()),Integer.parseInt(test1[9].trim())));
                       /* }else{
                            relativeLayoutFive.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[11].trim()) != 0){*/
                        //relativeLayoutSix.setVisibility(View.VISIBLE);
                        tankVOs.add(setValue(Integer.parseInt(test1[10].trim()),Integer.parseInt(test1[11].trim())));
                        /*}else{
                            relativeLayoutSix.setVisibility(View.GONE);
                        }*/


                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        tankAdapter = new TankAdapter(getApplicationContext(), tankVOs);
                        recyclerView.setAdapter(tankAdapter);

                        String notification = "";
                        for (TankVO tankVO: tankVOs) {
                            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                            smartDeviceDB.updateTankPercentage(tankVO);

                            if(tankVO.getPercentage() <= 70){
                                if(tankVO.getPercentage() <= 25){
                                    if(smartDeviceDB.isAlarmOn(tankVO.getTankNo(),25) == 1) {
                                        if (!notification.isEmpty()) {
                                            notification = notification + " \nTank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                                        } else {
                                            notification = "Tank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                                        }
                                    }
                                }else{
                                    if(smartDeviceDB.isAlarmOn(tankVO.getTankNo(),70) == 1) {
                                        if (!notification.isEmpty()) {
                                            notification = notification + " \nTank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                                        } else {
                                            notification = "Tank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                                        }
                                    }
                                }
                            }
                        }

                        if(!notification.isEmpty()) {
                            final Notification.Builder builder = new Notification.Builder(MainActivity.this);
                            builder.setStyle(new Notification.BigTextStyle()
                                    .bigText(notification)
                                    .setBigContentTitle("Albero water tank level")
                                    .setSummaryText("Please use water sparingly!"))
                                    .setSmallIcon(R.drawable.vem_logo);

                            final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            nm.notify(0, builder.build());
                        }

                        /*long synTime = Calendar.getInstance().getTimeInMillis() - (1000*60*10); // 10 minutes
                        if(new SmartDeviceSharedPreferences(getApplicationContext()).getLastSync() < synTime) {
                            new SmartDeviceSharedPreferences(getApplicationContext()).setLastSync();
                            HistoryRequestVO historyRequestVO = new SmartDeviceDB(getApplicationContext()).getTankHistoryRequest(1);
                            startValue = historyRequestVO.getStartValue();
                            endValue = historyRequestVO.getEndValue();
                            getHistory();
                        }*/
                        if(progress != null){
                            progress.cancel();
                        }


                        refreshTextView.setEnabled(true);


                    }catch (Exception e){
                        if(progress != null){
                            progress.cancel();
                        }
                        //errorTextView.setText(e.toString() + " \n\n " + response);
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
                    //errorTextView.setText( error.getMessage());
                    refreshTextView.setEnabled(true);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return Constant.TIME_OUT;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 0;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
        }catch (Exception e){
            if(progress != null){
                progress.cancel();
            }
            //errorTextView.setText(e.toString());
            Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
            refreshTextView.setEnabled(true);
        }
    }

    void getDBValue(){
        try {
            ProgressDialog progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Please Wait...");
            progress.show();

            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
            List<TankVO> tankVOs = smartDeviceDB.getTankPercentage();

            for (TankVO tankVO:tankVOs) {
                setValue(tankVO.getTankNo(),tankVO.getPercentage());
            }

            if (progress != null) {
                progress.cancel();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void doTesting(){

        try {
            ProgressDialog progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Please Wait...");
            progress.show();
            String response = "( 1,25,2, 50,3,75, 4,0,5, 0,6, 0)";
            //errorTextView.setText(response);
            String test1[] = response.split(",");
            StringBuilder str2 = new StringBuilder(test1[0].trim());
            test1[0] = str2.deleteCharAt(str2.indexOf("(")).toString();
            str2 = new StringBuilder(test1[11].trim());
            test1[11] = str2.deleteCharAt(str2.indexOf(")")).toString();
            //if(Integer.parseInt(test1[1].trim()) != 0){
            List<TankVO> tankVOs = new ArrayList<>();
            //relativeLayoutOne.setVisibility(View.VISIBLE);
            tankVOs.add(setValue(Integer.parseInt(test1[0].trim()),Integer.parseInt(test1[1].trim())));
                        /*}else{
                            relativeLayoutOne.setVisibility(View.GONE);
                        }*/

            //if(Integer.parseInt(test1[3].trim()) != 0){
            //relativeLayoutTwo.setVisibility(View.VISIBLE);
            tankVOs.add(setValue(Integer.parseInt(test1[2].trim()),Integer.parseInt(test1[3].trim())));
                        /*}else{
                            relativeLayoutTwo.setVisibility(View.GONE);
                        }*/

            //if(Integer.parseInt(test1[5].trim()) != 0){
            //relativeLayoutThree.setVisibility(View.VISIBLE);
            tankVOs.add(setValue(Integer.parseInt(test1[4].trim()),Integer.parseInt(test1[5].trim())));
                        /*}else{
                            relativeLayoutThree.setVisibility(View.GONE);
                        }*/

            //if(Integer.parseInt(test1[7].trim()) != 0){
            //relativeLayoutFour.setVisibility(View.VISIBLE);
            tankVOs.add(setValue(Integer.parseInt(test1[6].trim()),Integer.parseInt(test1[7].trim())));
                        /*}else{
                            relativeLayoutFour.setVisibility(View.GONE);
                        }
*/
            //if(Integer.parseInt(test1[9].trim()) != 0){
            //relativeLayoutFive.setVisibility(View.VISIBLE);
            tankVOs.add(setValue(Integer.parseInt(test1[8].trim()),Integer.parseInt(test1[9].trim())));
                       /* }else{
                            relativeLayoutFive.setVisibility(View.GONE);
                        }

                        if(Integer.parseInt(test1[11].trim()) != 0){*/
            //relativeLayoutSix.setVisibility(View.VISIBLE);
            tankVOs.add(setValue(Integer.parseInt(test1[10].trim()),Integer.parseInt(test1[11].trim())));
                        /*}else{
                            relativeLayoutSix.setVisibility(View.GONE);
                        }*/



            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            tankAdapter = new TankAdapter(getApplicationContext(), tankVOs);
            recyclerView.setAdapter(tankAdapter);
            String notification = "";
            for (TankVO tankVO: tankVOs) {
                SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                smartDeviceDB.updateTankPercentage(tankVO);

                if(tankVO.getPercentage() <= 70){
                    if(tankVO.getPercentage() <= 25){
                        if(smartDeviceDB.isAlarmOn(tankVO.getTankNo(),25) == 1) {
                            if (!notification.isEmpty()) {
                                notification = notification + " \nTank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                            } else {
                                notification = "Tank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                            }
                        }
                    }else{
                        if(smartDeviceDB.isAlarmOn(tankVO.getTankNo(),70) == 1) {
                            if (!notification.isEmpty()) {
                                notification = notification + " \nTank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                            } else {
                                notification = "Tank no " + tankVO.getTankNo() + " level is at " + tankVO.getPercentage() + "%";
                            }
                        }
                    }
                }
            }

            if(!notification.isEmpty()) {
                final Notification.Builder builder = new Notification.Builder(MainActivity.this);
                builder.setStyle(new Notification.BigTextStyle()
                        .bigText(notification)
                        .setBigContentTitle("Albero water tank level")
                        .setSummaryText("Please use water sparingly!"))
                        .setSmallIcon(R.drawable.vem_logo);

                final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(0, builder.build());
            }

            if (progress != null) {
                progress.cancel();
            }

            new SmartDeviceSharedPreferences(getApplicationContext()).setCurrentValueLastSync();

            getHistoryTest();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    void getHistory(){
        try {

            if (endValue == 0 || endValue == 1 || endValue == 1001 || endValue == 1000) {
                startValue = 1;
                endValue = 100;
            } else {
                int rem = endValue % 100;
                rem = 100 - rem;
                startValue = endValue;
                endValue = startValue + rem;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_HISTORY + tankNo + "/" + startValue + "/" + endValue + "/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (!response.equals("(0)")) {
                            response = response.replace("(", "");
                            response = response.replace(")", "");
                            String args[] = response.split(":");

                            String args1[] = args[0].split(",");

                            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                            String res = "More";
                            for (int i = 0; i < args1.length; i = i + 2) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
                                String date = args1[i].trim() + "-00";
                                Date d = formatter.parse(date);
                                HistoryVO historyVO = new HistoryVO();
                                historyVO.setDateTime(d.getTime());
                                historyVO.setTankNo(tankNo);
                                historyVO.setPercentage(Integer.parseInt(args1[i + 1].trim()));
                                res = smartDeviceDB.insertTankHistory(historyVO);
                                if (res.equals("Change")) {
                                    // It should be minus to but we are starting from zero that's why minus one
                                    // When we find change it will access value which is repeated to keep end value as last new value we need to minus i by 2.
                                    // i / 2 is required as we have 2 item for one value item 1 : date and item 2 : percentage
                                    i = i - 1;
                                    endValue = startValue + (i / 2);
                                    break;
                                }
                            }
                            if (res.equals("More")) {
                                startValue = Integer.parseInt(args[1]);
                                endValue = Integer.parseInt(args[1]);
                                getHistory();
                            } else {
                                HistoryRequestVO historyRequestVO1 = new HistoryRequestVO();
                                historyRequestVO1.setTankNo(tankNo);
                                historyRequestVO1.setStartValue(startValue);
                                historyRequestVO1.setEndValue(endValue + 1);
                                smartDeviceDB.updateTankHistoryRequest(historyRequestVO1);
                                if (tankNo < 6) {
                                    tankNo = tankNo + 1;
                                    HistoryRequestVO historyRequestVO = new SmartDeviceDB(getApplicationContext()).getTankHistoryRequest(1);
                                    startValue = historyRequestVO.getStartValue();
                                    endValue = historyRequestVO.getEndValue();
                                    getHistory();
                                }else{
                                    new SmartDeviceSharedPreferences(getApplicationContext()).setLastSync();
                                }
                            }
                        } else {
                            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                            HistoryRequestVO historyRequestVO1 = new HistoryRequestVO();
                            historyRequestVO1.setTankNo(tankNo);
                            historyRequestVO1.setStartValue(startValue);
                            historyRequestVO1.setEndValue(endValue + 1);
                            smartDeviceDB.updateTankHistoryRequest(historyRequestVO1);
                            if (tankNo < 6) {
                                tankNo = tankNo + 1;
                                HistoryRequestVO historyRequestVO = new SmartDeviceDB(getApplicationContext()).getTankHistoryRequest(1);
                                startValue = historyRequestVO.getStartValue();
                                endValue = historyRequestVO.getEndValue();
                                getHistory();
                            }else{
                                new SmartDeviceSharedPreferences(getApplicationContext()).setLastSync();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }catch (Exception e){
            Toast.makeText(MainActivity.this,"Error occurred",Toast.LENGTH_SHORT).show();
        }
    }

    void getHistoryTest(){
        try {
            String response = "(01-06-17 18-09, 30,01-06-17 18-11, 35,01-06-17 18-14, 50,01-06-17 18-16, 60,01-06-17 18-20, 65,01-06-17 18-23, 70,01-06-17 18-26, 85,01-06-17 18-29, 90,01-06-17 18-32, 95:215)";
            response = response.replace("(", "");
            response = response.replace(")", "");
            String args[] = response.split(":");

            String args1[] = args[0].split(",");

            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
            for (int i = 0; i < args1.length; i = i + 2) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
                String date = args1[i].trim() + "-00";
                Date d = formatter.parse(date);
                HistoryVO historyVO = new HistoryVO();
                historyVO.setDateTime(d.getTime());
                historyVO.setTankNo(tankNo);
                historyVO.setPercentage(Integer.parseInt(args1[i+1].trim()));
                smartDeviceDB.insertTankHistory(historyVO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void updateSyncTextView(){
        if(new SmartDeviceSharedPreferences(getApplicationContext()).getCurrentValueLastSync() > 0){
            Date date = new Date(new SmartDeviceSharedPreferences(getApplicationContext()).getCurrentValueLastSync());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            lastSyncTextView.setText("Last refreshed time : " + formatter.format(date).toString());
        }else{
            lastSyncTextView.setText("");
        }
    }


}
