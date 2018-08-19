package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
import com.vem_tooling.smartwaterlevelmonitor.utils.StatusCodes;

import org.angmarch.views.NiceSpinner;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class WifiSelectionActivity extends AppCompatActivity {

    WifiManager mainWifi;
    WifiReceiver receiverWifi;

    StringBuilder sb = new StringBuilder();

    @BindView(R.id.niceSpinner)
    NiceSpinner niceSpinner;

    @BindView(R.id.connect)
    LatoRegularTextView connect;

    @BindView(R.id.inputPassword)
    EditText inputPassword;

    ArrayList<String> connections;
    ProgressDialog progress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_selection_activity);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        ButterKnife.bind(this);
        progress = new ProgressDialog(WifiSelectionActivity.this);
        progress.setMessage("Please wait...");
        progress.show();

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (inputPassword.getText().toString().isEmpty()) {
                        new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please enter wifi password")
                                .show();
                        return;
                    }

                    if (connectToWifi(connections.get(niceSpinner.getSelectedIndex()), inputPassword.getText().toString())) {
                        // Store the ssid and password of home wifi
                        SmartDeviceSharedPreferences smartDeviceSharedPreferences = new SmartDeviceSharedPreferences(getApplicationContext());
                        smartDeviceSharedPreferences.setHomeWifiSsid(connections.get(niceSpinner.getSelectedIndex()));
                        smartDeviceSharedPreferences.setHomeWifiPassword(inputPassword.getText().toString());

                        // Reconnect to hardware device wifi
                        if (smartDeviceSharedPreferences.getNewSSID() != null && !smartDeviceSharedPreferences.getNewSSID().isEmpty()) {
                            if (!connectToWifi(smartDeviceSharedPreferences.getNewSSID(), smartDeviceSharedPreferences.getNewPassword())) {
                                new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Failed to connect to network")
                                        .show();
                                return;
                            }
                        } else {
                            if (!connectToWifi(Constant.WIFI_SSID, Constant.WIFI_PASSWORD)) {
                                new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Failed to connect to network")
                                        .show();
                                return;
                            }
                        }

                        // Call Hardware API to give home wifi ssid and password
                        updateData(JSONGenerator.giveMeSetHomeSSIDandPassword(connections.get(niceSpinner.getSelectedIndex()), inputPassword.getText().toString()));

                        //Testing purpose added
                        /*if (connectToWifi(smartDeviceSharedPreferences.getHomeWifiSsid(), smartDeviceSharedPreferences.getHomeWifiPassword())) {
                            new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("SWLM")
                                    .setContentText("CONNECTED TO HOME NTEWORK")
                                    .show();
                        }else {
                            new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Failed to connect to network")
                                    .show();
                        }*/
                    } else {
                        new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Failed to connect to network")
                                .show();
                        return;
                    }
                }catch (Exception e){
                    new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong")
                            .show();
                }
            }
        });

        doInback();
    }

    public void doInback() {
        try {
            mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            receiverWifi = new WifiReceiver();
            registerReceiver(receiverWifi, new IntentFilter(
                    WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

            if (mainWifi.isWifiEnabled() == false) {
                mainWifi.setWifiEnabled(true);
            }

            mainWifi.startScan();
        }catch (Exception e){
            e.printStackTrace();
            if(progress != null){
                progress.cancel();
            }
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {

            try {
                connections=new ArrayList<>();
                sb = new StringBuilder();
                List<ScanResult> wifiList;
                wifiList = mainWifi.getScanResults();
                for (int i = 0; i < wifiList.size(); i++) {
                    connections.add(wifiList.get(i).SSID);
                }

                niceSpinner.attachDataSource(connections);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(progress != null){
                    progress.cancel();
                }
            }

        }
    }

    /**
     * Connect to the specified wifi network.
     *
     * @param networkSSID     - The wifi network SSID
     * @param networkPassword - the wifi password
     */
    private boolean connectToWifi(final String networkSSID, final String networkPassword) {
        try {
            mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (!mainWifi.isWifiEnabled()) {
                mainWifi.setWifiEnabled(true);
            }else{
                WifiInfo wifiInfo = mainWifi.getConnectionInfo();
                if(wifiInfo != null && wifiInfo.getSSID() != null && !wifiInfo.getSSID().isEmpty()){
                    String ssid = wifiInfo.getSSID().replaceAll("\"","");
                    if(ssid.equals(networkSSID)){
                        return true;
                    }
                }
            }

            WifiConfiguration conf = new WifiConfiguration();
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            String ntSSID = networkSSID.replaceAll("\"","");
            String ntPswd = networkPassword.replaceAll("\"","");
            conf.SSID = String.format("\"%s\"", ntSSID);
            conf.preSharedKey = String.format("\"%s\"", ntPswd);

            int netId = getExistingNetworkId(ntSSID);
            if(netId == -1){
                //mainWifi.removeNetwork(netId);
                netId = mainWifi.addNetwork(conf);
            }
            mainWifi.disconnect();
            boolean res = mainWifi.enableNetwork(netId, true);
            mainWifi.reconnect();

            return res;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private int getExistingNetworkId(String SSID) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration existingConfig : configuredNetworks) {
                String ntSSID = existingConfig.SSID.replaceAll("\"","");
                if (ntSSID.equals(SSID)) {
                    return existingConfig.networkId;
                }
            }
        }
        return -1;
    }

    void updateData(JSONObject requestObject){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constant.SET_HOME_SSID_PASSWORD, requestObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        try {
                            if (response != null && response.has(Constant.STATUS_CODE) && response.get(Constant.STATUS_CODE).equals(StatusCodes.SUCCESS)) {
                                SmartDeviceSharedPreferences smartDeviceSharedPreferences = new SmartDeviceSharedPreferences(getApplicationContext());
                                if (connectToWifi(smartDeviceSharedPreferences.getHomeWifiSsid(), smartDeviceSharedPreferences.getHomeWifiPassword())) {
                                    new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("SWLM")
                                            .setContentText("CONNECTED TO HOME NTEWORK")
                                            .show();
                                } else if(response != null && response.has(Constant.STATUS_CODE) && response.get(Constant.STATUS_CODE).equals(StatusCodes.FAILURE_PASSWORD_FOR_WIFI)){
                                    new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Incorrect wifi password. Please retry again")
                                            .show();
                                } else{
                                    new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Failed to connect to network")
                                            .show();
                                }
                            }else{
                                new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Error...")
                                        .setContentText("Error occurred while connecting to home wifi")
                                        .show();
                            }
                        }catch (Exception e){
                            new SweetAlertDialog(WifiSelectionActivity.this, SweetAlertDialog.ERROR_TYPE)
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
