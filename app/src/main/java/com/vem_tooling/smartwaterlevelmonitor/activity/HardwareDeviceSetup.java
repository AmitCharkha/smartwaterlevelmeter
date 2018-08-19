package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.JSONGenerator;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by amit on 12/6/17.
 */

public class HardwareDeviceSetup extends AppCompatActivity {

    @BindView(R.id.updateTextView)
    LatoRegularTextView updateTextView;

    @BindView(R.id.skip)
    LatoRegularTextView skip;

    @BindView(R.id.inputSSID)
    EditText inputSSID;

    @BindView(R.id.inputSSIDPassword)
    EditText inputSSIDPassword;

    @BindView(R.id.linearLayoutOne)
    LinearLayout linearLayoutOne;

    @BindView(R.id.relativeLayoutOne)
    RelativeLayout relativeLayoutOne;

    @BindView(R.id.goToSettingTextView)
    LatoRegularTextView goToSettingTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Take him to next screen for selecting wifi network and password
                Intent intent = new Intent(HardwareDeviceSetup.this, WifiSelectionActivity.class);
                startActivity(intent);
            }
        });

        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call Hardware API to udpate the new SSID and Password
                try {
                    if (inputSSID.getText().toString().isEmpty()) {
                        new SweetAlertDialog(HardwareDeviceSetup.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please enter new SSID")
                                .show();
                        return;
                    }

                    if (inputSSIDPassword.getText().toString().isEmpty()) {
                        new SweetAlertDialog(HardwareDeviceSetup.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please enter new password")
                                .show();
                        return;
                    }

                    SmartDeviceSharedPreferences smartDeviceSharedPreferences = new SmartDeviceSharedPreferences(getApplicationContext());
                    smartDeviceSharedPreferences.setNewSSID(inputSSID.getText().toString());
                    smartDeviceSharedPreferences.setNewPassword(inputSSIDPassword.getText().toString());

                    // Call API to update the data
                    updateData(JSONGenerator.giveMeSetSSIDandPassword(12, inputSSID.getText().toString(), inputSSIDPassword.getText().toString()));

                    //Testing purpose added
                    /*Intent intent = new Intent(HardwareDeviceSetup.this, WifiSelectionActivity.class);
                    startActivity(intent);*/

                }catch (Exception e){
                    new SweetAlertDialog(HardwareDeviceSetup.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong")
                            .show();
                }

            }
        });

        goToSettingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)){
            relativeLayoutOne.setVisibility(View.GONE);
            linearLayoutOne.setVisibility(View.GONE);
            updateTextView.setEnabled(true);
            skip.setEnabled(true);
            inputSSID.setEnabled(true);
            inputSSIDPassword.setEnabled(true);
        }else{
            relativeLayoutOne.setVisibility(View.VISIBLE);
            linearLayoutOne.setVisibility(View.VISIBLE);
            skip.setEnabled(false);
            updateTextView.setEnabled(false);
            inputSSID.setEnabled(false);
            inputSSIDPassword.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

        }
    }

    void updateData(JSONObject requestObject){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constant.UPDATE_SSID_PASSWORD, requestObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        try {
                            if (response != null && response.has(Constant.STATUS_CODE) && response.get(Constant.STATUS_CODE).equals("200")) {
                                Intent intent = new Intent(HardwareDeviceSetup.this, WifiSelectionActivity.class);
                                startActivity(intent);
                            }else{
                                new SweetAlertDialog(HardwareDeviceSetup.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error...")
                                        .setContentText("Error occurred while updating password")
                                        .show();
                            }
                        }catch (Exception e){
                            new SweetAlertDialog(HardwareDeviceSetup.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Something went wrong")
                                    .show();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }
}
