package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.CorisandeBoldTextView;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoLightItalicTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.backTextView)
    LatoLightItalicTextView backTextView;

    @BindView(R.id.errorMessage)
    TextView errorMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        }

        if(new SmartDeviceSharedPreferences(getApplicationContext()).getIsAdmin() == 1){
            setRtc.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
        }else{
            setRtc.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
        }

        setRtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRtcTest();
            }
        });

        alarmSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        final ProgressDialog progress = new ProgressDialog(SettingScreen.this);
        try{
            progress.setMessage("Calling Get RTC...");
            progress.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_RTC, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if(progress != null){
                            progress.cancel();
                        }
                        boolean call = false;
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
                        try {
                            //response = response.replace("\n", "");
                            response = response.replace("(", "");
                            response = response.replace(")", "");


                            Date d = formatter.parse(response);
                            long fiveMin = 1000*60*5;

                            if((d.getTime() - Calendar.getInstance().getTimeInMillis()) > fiveMin || ((Calendar.getInstance().getTimeInMillis() - d.getTime()) > fiveMin)){
                                call = true;
                            }else{
                                call = false;
                                Toast.makeText(SettingScreen.this, "Time difference is less than 5 minute", Toast.LENGTH_LONG).show();
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                            errorMessage.setText(response + "\n" + " One : " + e.toString());
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
                        }

                    }catch (Exception e){
                        Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                        errorMessage.setText(response + "\n" + " Two :  " + e.toString());
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errorMessage.setText("Three :  " + error.toString());
                    Toast.makeText(SettingScreen.this,"Error occurred",Toast.LENGTH_LONG).show();
                    if(progress != null){
                        progress.cancel();
                    }
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }catch (Exception e){
            if(progress != null){
                progress.cancel();
            }
        }
    }


    void setRtcTest(){
        try {
            String res = "(15-6-17 16:26:55)";
            //res = res.replace("\n", "");
            res = res.replace("(", "");
            res = res.replace(")", "");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss");

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
}
