package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoLightItalicTextView;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by amit on 2/6/17.
 */

public class CalibrateSensor extends AppCompatActivity {

    @BindView(R.id.calibrateTank1_Top)
    LatoRegularTextView calibrateTank1_Top;
    @BindView(R.id.calibrateTank2_Top)
    LatoRegularTextView calibrateTank2_Top;
    @BindView(R.id.calibrateTank3_Top)
    LatoRegularTextView calibrateTank3_Top;
    @BindView(R.id.calibrateTank4_Top)
    LatoRegularTextView calibrateTank4_Top;
    @BindView(R.id.calibrateTank5_Top)
    LatoRegularTextView calibrateTank5_Top;
    @BindView(R.id.calibrateTank6_Top)
    LatoRegularTextView calibrateTank6_Top;

    @BindView(R.id.calibrateTank1_Bottom)
    LatoRegularTextView calibrateTank1_Bottom;
    @BindView(R.id.calibrateTank2_Bottom)
    LatoRegularTextView calibrateTank2_Bottom;
    @BindView(R.id.calibrateTank3_Bottom)
    LatoRegularTextView calibrateTank3_Bottom;
    @BindView(R.id.calibrateTank4_Bottom)
    LatoRegularTextView calibrateTank4_Bottom;
    @BindView(R.id.calibrateTank5_Bottom)
    LatoRegularTextView calibrateTank5_Bottom;
    @BindView(R.id.calibrateTank6_Bottom)
    LatoRegularTextView calibrateTank6_Bottom;

    @BindView(R.id.nextTextView)
    LatoLightItalicTextView nextTextView;

    @BindView(R.id.backTextView)
    LatoLightItalicTextView backTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibrate_sensor);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        try {
            if (getIntent().getExtras().getString("activityName").equalsIgnoreCase("SplashScreen")) {
                backTextView.setVisibility(View.GONE);
                nextTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SmartDeviceSharedPreferences(getApplicationContext()).setSetupPageNumber(3);
                        Intent intent = new Intent(CalibrateSensor.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                nextTextView.setVisibility(View.GONE);
                backTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }

            calibrateTank1_Top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 1", Toast.LENGTH_LONG).show();
                        calibrateTankTopLevel(1);
                    }else{
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank2_Top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 2", Toast.LENGTH_LONG).show();
                        calibrateTankTopLevel(2);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank3_Top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 3", Toast.LENGTH_LONG).show();
                        calibrateTankTopLevel(3);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank4_Top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 4", Toast.LENGTH_LONG).show();
                        calibrateTankTopLevel(4);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank5_Top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 5", Toast.LENGTH_LONG).show();
                        calibrateTankTopLevel(5);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank6_Top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 6", Toast.LENGTH_LONG).show();
                        calibrateTankTopLevel(6);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank1_Bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 1", Toast.LENGTH_LONG).show();
                        calibrateTankBottomLevel(1);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank2_Bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 2", Toast.LENGTH_LONG).show();
                        calibrateTankBottomLevel(2);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank3_Bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 3", Toast.LENGTH_LONG).show();
                        calibrateTankBottomLevel(3);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank4_Bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 4", Toast.LENGTH_LONG).show();
                        calibrateTankBottomLevel(4);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank5_Bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 5", Toast.LENGTH_LONG).show();
                        calibrateTankBottomLevel(5);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });

            calibrateTank6_Bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)) {
                        Toast.makeText(CalibrateSensor.this, "Calibrating Sensor of Tank 6", Toast.LENGTH_LONG).show();
                        calibrateTankBottomLevel(6);
                    }else {
                        new SweetAlertDialog(CalibrateSensor.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops..")
                                .setContentText("You are not connected with tank wifi. Please connect and retry.")
                                .show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(CalibrateSensor.this,"Error occurred",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    void calibrateTankTopLevel(int no){
        try{
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.CALIBAR_SENESOR_TOP_LEVEL + no + "/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(CalibrateSensor.this,"Top level set successfully",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(CalibrateSensor.this,"Error occurred",Toast.LENGTH_LONG).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CalibrateSensor.this,"Error occurred",Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch (Exception e){
        }
    }

    void calibrateTankBottomLevel(int no){
        try{
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Constant.CALIBAR_SENESOR_BOTTOM_LEVEL + no + "/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(CalibrateSensor.this,"Bottom level set successfully",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(CalibrateSensor.this,"Error occurred",Toast.LENGTH_LONG).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CalibrateSensor.this,"Error occurred",Toast.LENGTH_LONG).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest1);
        }catch (Exception e){
        }
    }
}
