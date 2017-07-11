package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.db.SmartDeviceDB;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.CorisandeBoldTextView;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoLightItalicTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryRequestVO;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryVO;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by amit on 15/6/17.
 */

public class SettingScreen extends AppCompatActivity {

    @BindView(R.id.alarmSetting)
    CorisandeBoldTextView alarmSetting;

    @BindView(R.id.setRtc)
    CorisandeBoldTextView setRtc;

    @BindView(R.id.line1)
    TextView line1;

    @BindView(R.id.line2)
    TextView line2;

    @BindView(R.id.line4)
    TextView line4;

    @BindView(R.id.backTextView)
    LatoLightItalicTextView backTextView;

    @BindView(R.id.refreshHistory)
    CorisandeBoldTextView refreshHistory;

    @BindView(R.id.clearHistory)
    CorisandeBoldTextView clearHistory;

    @BindView(R.id.niceSpinner)
    NiceSpinner niceSpinner;

    private int tankNo = 1;
    private int tankNoForClear = 1;
    private int startValue, endValue;

    @BindView(R.id.errorMessage)
    TextView errorMessage;

    ProgressDialog progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        progress = new ProgressDialog(SettingScreen.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        }

        if(new SmartDeviceSharedPreferences(getApplicationContext()).getIsAdmin() == 1){
            setRtc.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            clearHistory.setVisibility(View.VISIBLE);
            line4.setVisibility(View.VISIBLE);
            niceSpinner.setVisibility(View.VISIBLE);
        }else{
            setRtc.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
            clearHistory.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);
            niceSpinner.setVisibility(View.INVISIBLE);
        }

        setRtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)){
                    /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                    Toast.makeText(getApplicationContext(),formatter.format(new Date(Calendar.getInstance().getTimeInMillis())).toString(),Toast.LENGTH_LONG).show();*/
                    setRtc();
                }else {
                    new SweetAlertDialog(SettingScreen.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops..")
                            .setContentText("You are not connected with tank wifi. Please connect and retry.")
                            .show();
                }
            }
        });

        alarmSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingScreen.this,AlarmSettingActivity.class);
                startActivity(intent);
            }
        });

        refreshHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)){
                    HistoryRequestVO historyRequestVO = new SmartDeviceDB(getApplicationContext()).getTankHistoryRequest(1);
                    startValue = historyRequestVO.getStartValue();
                    endValue = historyRequestVO.getEndValue();
                    getHistory();
                }else {
                    new SweetAlertDialog(SettingScreen.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops..")
                            .setContentText("You are not connected with tank wifi. Please connect and retry.")
                            .show();
                }

                //getHistoryTest();
            }
        });

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingScreen.this);
                alertDialog.setTitle("Clear History");
                alertDialog.setMessage("Do you really want to clear device history?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                        if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)){
                            clearDeviceHistory();
                        }else {
                            new SweetAlertDialog(SettingScreen.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops..")
                                    .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                    .show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        List<String> dataset = new LinkedList<>(Arrays.asList("Tank 1", "Tank 2", "Tank 3", "Tank 4", "Tank 5","Tank 6"));
        niceSpinner.attachDataSource(dataset);

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tankNoForClear = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
        //Toast.makeText(getApplicationContext(),formatter.format(new Date(Calendar.getInstance().getTimeInMillis())).toString(),Toast.LENGTH_LONG).show();
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    void setRtc(){
        try{
            if(!progress.isShowing()) {
                progress.setMessage("Calling Get RTC...");
                progress.show();
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_RTC, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if(progress != null){
                            progress.cancel();
                        }
                        boolean call = false;
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                        try {
                            //response = response.replace("\n", "");
                            response = response.replace("(", "");
                            response = response.replace(")", "");


                            Date d = formatter.parse(response);
                            long fiveMin = 1000*60*1; // 1 minute

                            if((d.getTime() - Calendar.getInstance().getTimeInMillis()) > fiveMin || ((Calendar.getInstance().getTimeInMillis() - d.getTime()) > fiveMin)){
                                call = true;
                            }else{
                                call = false;
                                Toast.makeText(SettingScreen.this, "Time difference is less than 5 minute", Toast.LENGTH_LONG).show();
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                            //errorMessage.setText(response + "\n" + " One : " + e.toString());
                        }


                        if(call) {
                            progress.setMessage("Calling Set RTC...");
                            progress.show();
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.SET_RTC + formatter.format(new Date(Calendar.getInstance().getTimeInMillis())).toString() + "/", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(progress != null){
                                        progress.cancel();
                                    }
                                    try {
                                        if(response.equals("(0)")) {
                                            Toast.makeText(SettingScreen.this,"RTC time set successfully",Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(SettingScreen.this, "Error occurred", Toast.LENGTH_LONG).show();
                                    }
                                }

                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(SettingScreen.this, "Error occurred", Toast.LENGTH_LONG).show();
                                    if(progress != null){
                                        progress.cancel();
                                    }
                                }
                            });

                            RequestQueue requestQueue = Volley.newRequestQueue(SettingScreen.this);
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
                        }

                    }catch (Exception e){
                        Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                        //errorMessage.setText(response + "\n" + " Two :  " + e.toString());
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //errorMessage.setText("Three :  " + error.toString());
                    Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                    if(progress != null){
                        progress.cancel();
                    }
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
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
        }
    }


    void clearDeviceHistory(){
        if(!progress.isShowing()) {
            progress.setMessage("Clearing history for tank no "+tankNoForClear +"\nPlease Wait...");
            progress.show();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.CLEAR_HISTORY + tankNoForClear + "/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(progress != null){
                        progress.cancel();
                    }
                    if(response.equals("(0)")) {
                        Toast.makeText(SettingScreen.this,"Data NOT cleared",Toast.LENGTH_LONG).show();
                    }else if(response.equals("(1)")){
                        Toast.makeText(SettingScreen.this,"Successfully data cleared",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                if(progress != null){
                    progress.cancel();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
    }

    void setRtcTest(){
        try {
            String res = "(15-6-17 16:26:55)";
            //res = res.replace("\n", "");
            res = res.replace("(", "");
            res = res.replace(")", "");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

            Date d = formatter.parse(res);
            long fiveMin = 1000*60*5;

            if((d.getTime() - Calendar.getInstance().getTimeInMillis()) > fiveMin || ((Calendar.getInstance().getTimeInMillis() - d.getTime()) > fiveMin)){
                Toast.makeText(SettingScreen.this,"call set rtc",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(SettingScreen.this,"do not call set rtc",Toast.LENGTH_LONG).show();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void getHistory(){
        try{
            if(!progress.isShowing()) {
                progress.setMessage("Please Wait...");
                progress.show();
            }

            if (endValue == 0 || endValue == 1 || endValue == 1001 || endValue == 1000) {
                startValue = 1;
                endValue = 100;
            } else {
                int rem = endValue % 100;
                rem = 100 - rem;
                startValue = endValue;
                endValue = startValue + rem;
            }

            errorMessage.setText(errorMessage.getText() + "\n**Calling Tank No : " + tankNo + " **Start Value = " + startValue + " **End Value = " + endValue);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_HISTORY + tankNo + "/" + startValue + "/" + endValue + "/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        errorMessage.setText(errorMessage.getText() + "\n**Response : " + response);
                        if (!response.equals("(0)")) {
                            response = response.replace("(", "");
                            response = response.replace(")", "");
                            String args[] = response.split(":");

                            String args1[] = args[0].split(",");

                            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                            String res = "More";
                            for (int i = 0; i < args1.length; i = i + 2) {
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
                                String date = args1[i].trim();// + "-00";
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
                                    i = i + 2;
                                    endValue = startValue + (i / 2);
                                    break;
                                }else{

                                }
                            }
                            if (res.equals("More")) {
                                startValue = Integer.parseInt(args[1]);
                                endValue = Integer.parseInt(args[1]) + 1;
                                getHistory();
                            } else {
                                endValue = endValue + 1;
                                HistoryRequestVO historyRequestVO1 = new HistoryRequestVO();
                                historyRequestVO1.setTankNo(tankNo);
                                historyRequestVO1.setStartValue(startValue);
                                historyRequestVO1.setEndValue(endValue);
                                int result = smartDeviceDB.updateTankHistoryRequest(historyRequestVO1);
                                if (tankNo < 6) {
                                    tankNo = tankNo + 1;
                                    HistoryRequestVO historyRequestVO = new SmartDeviceDB(getApplicationContext()).getTankHistoryRequest(1);
                                    startValue = historyRequestVO.getStartValue();
                                    endValue = historyRequestVO.getEndValue();
                                    getHistory();
                                }else{
                                    if(progress != null){
                                        progress.cancel();
                                    }
                                    new SmartDeviceSharedPreferences(getApplicationContext()).setLastSync();
                                }
                            }
                        } else {
                            endValue = endValue + 1;
                            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                            HistoryRequestVO historyRequestVO1 = new HistoryRequestVO();
                            historyRequestVO1.setTankNo(tankNo);
                            historyRequestVO1.setStartValue(startValue);
                            historyRequestVO1.setEndValue(endValue);
                            smartDeviceDB.updateTankHistoryRequest(historyRequestVO1);
                            if (tankNo < 6) {
                                tankNo = tankNo + 1;
                                HistoryRequestVO historyRequestVO = new SmartDeviceDB(getApplicationContext()).getTankHistoryRequest(1);
                                startValue = historyRequestVO.getStartValue();
                                endValue = historyRequestVO.getEndValue();
                                getHistory();
                            }else{
                                if(progress != null){
                                    progress.cancel();
                                }
                                new SmartDeviceSharedPreferences(getApplicationContext()).setLastSync();
                            }
                        }

                    } catch (Exception e) {
                        if(progress != null){
                            progress.cancel();
                        }
                        e.printStackTrace();
                        errorMessage.setText(errorMessage.getText() + "\n**Error No 1 : " + e.toString());
                        Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(progress != null){
                        progress.cancel();
                    }
                    errorMessage.setText(errorMessage.getText() + "\n**Error No 2 : " + error.toString());
                    Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(SettingScreen.this);
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
            errorMessage.setText(errorMessage.getText() + "\n**Error No 3 : " + e.toString());
            Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_SHORT).show();
        }
    }

    void getHistoryTest(){
        try {
            //String response = "(30-6-17 20-44,20,30-6-17 20-44,100,30-6-17 20-44,0,30-6-17 20-50,80,30-6-17 20-50,70,30-6-17 20-50,0,30-6-17 20-50,90,30-6-17 20-50,0,30-6-17 20-51,80,30-6-17 20-51,0,30-6-17 20-51,70,30-6-17 20-51,0,30-6-17 21-1,90,30-6-17 21-1,0,30-6-17 21-8,20,30-6-17 21-8,40,30-6-17 21-8,0,30-6-17 22-41,10,1-7-17 11-0,0,1-7-17 11-14,10,1-7-17 11-24,0,5-7-17 10-11,20,5-7-17 10-12,0,5-7-17 11-7,10,5-7-17 11-7,0,5-7-17 11-7,100,5-7-17 11-7,0,5-7-17 11-7,100,5-7-17 11-7,30,5-7-17 11-7,0,5-7-17 11-7,10,5-7-17 11-29,0,5-7-17 11-31,10,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 19-57,100,30-6-17 20-2,0,30-6-17 20-14,100,30-6-17 20-28,0,30-6-17 20-28,100,30-6-17 20-29,0,30-6-17 20-29,10,30-6-17 20-33,0,30-6-17 20-33,90,30-6-17 20-34,10,30-6-17 20-34,0,30-6-17 20-41,30,30-6-17 20-41,60,30-6-17 20-41,20,30-6-17 20-41,0,30-6-17 22-5,80,30-6-17 22-5,50,30-6-17 22-5,0,30-6-17 22-18,40,30-6-17 22-18,0,30-6-17 22-18,40,30-6-17 22-18,0,30-6-17 22-18,40,30-6-17 22-18,0,30-6-17 22-19,10,30-6-17 22-19,0,30-6-17 22-19,20,30-6-17 22-19,0,30-6-17 22-21,10,30-6-17 22-36,0,30-6-17 22-36,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0,30-6-17 8-22,100,30-6-17 8-22,0:99)";
            String response = "(30-6-17 20-44-10,20,30-6-17 20-54-20,100:2)";
            response = response.replace("(", "");
            response = response.replace(")", "");
            String args[] = response.split(":");

            String args1[] = args[0].split(",");

            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
            Toast.makeText(SettingScreen.this,"Length "+ args1.length,Toast.LENGTH_SHORT).show();
            for (int i = 0; i < args1.length; i = i + 2) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
                String date = args1[i].trim(); //+ "-00";
                Date d = formatter.parse(date);
                HistoryVO historyVO = new HistoryVO();
                historyVO.setDateTime(d.getTime());
                historyVO.setTankNo(tankNo);
                historyVO.setPercentage(Integer.parseInt(args1[i+1].trim()));
                String res = smartDeviceDB.insertTankHistory(historyVO);
                if (res.equals("Change")) {
                    // It should be minus to but we are starting from zero that's why minus one
                    // When we find change it will access value which is repeated to keep end value as last new value we need to minus i by 2.
                    // i / 2 is required as we have 2 item for one value item 1 : date and item 2 : percentage
                    break;
                }else{

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}


