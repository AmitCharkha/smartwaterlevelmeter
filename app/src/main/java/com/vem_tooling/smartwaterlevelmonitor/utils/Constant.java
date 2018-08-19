package com.vem_tooling.smartwaterlevelmonitor.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by amit on 2/6/17.
 */

public class Constant {

    /**
     * Server URL
     */
    private static final String WEB_URL = "http://192.168.4.1/";

    /**
     * API
     */
    // Version 1.0
    public static final String GET_WATER_LEVEL = WEB_URL + "getwaterLevel/";
    public static final String GET_HISTORY  = WEB_URL + "gethistory/";
    public static final String CALIBAR_SENESOR_TOP_LEVEL = WEB_URL + "calibrSenstoplev/";
    public static final String CALIBAR_SENESOR_BOTTOM_LEVEL = WEB_URL + "calibrSensbotlev/";
    public static final String GET_RTC = WEB_URL + "getRTC/";
    public static final String SET_RTC = WEB_URL + "setRTC/";
    public static final String CLEAR_HISTORY = WEB_URL + "clear_history/";

    // Version 2.0
    public static final String UPDATE_SSID_PASSWORD = WEB_URL + "setNewSSIDandPassword";
    public static final String SET_HOME_SSID_PASSWORD = WEB_URL + "setWifiDeviceSSIDandPassword";

    /**
     * Other required field
     */
    public static final String WIFI_PASSWORD = "Admin@123";
    public static final String WIFI_SSID = "\"Smart Water Level Monitor\"";
    //public static String WIFI_SSID = "\"goodday\"";

    /**
     * Shared Preferences variable
     */
    public static final String SMARTDEVICE_PREF = "SMARTDEVICE_PREF";
    public static final String IS_ADMIN = "IS_ADMIN";
    public static final String SETUP_PAGE_NUMBER = "SETUP_PAGE_NUMBER";
    public static final String HISTORY_SYNC_TIME = "HISTORY_SYNC_TIME";
    public static final String CURRENT_VALUE_SYNC_TIME = "CURRENT_VALUE_SYNC_TIME";

    /**
     * Shared Preferences variable Version 2.0
     */
    public static final String NEW_SSID = "NEW_SSID";
    public static final String NEW_SSID_PASSWORD = "NEW_SSID_PASSWORD";
    public static final String HOME_WIFI_SSID = "HOME_WIFI_SSID";
    public static final String HOME_WIFI_SSID_PASSWORD = "HOME_WIFI_SSID_PASSWORD";


    public static final int TIME_OUT = 500000; // In milliseconds.

    /*
    Version 2.0 New requried codes
     */
    public static final String STATUS_CODE = "status_code";

    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);

        // ARE WE CONNECTED TO THE NET
        if(conMgr == null){
            return false;
        }
        else{
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                return true;

            } else {
                return false;
            }
        }
    }
}
