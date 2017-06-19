package com.vem_tooling.smartwaterlevelmonitor.utils;

/**
 * Created by amit on 2/6/17.
 */

public class Constant {

    /**
     * Server URL
     */
    public static String WEB_URL = "http://192.168.4.1/";

    /**
     * API
     */
    public static String GET_WATER_LEVEL = WEB_URL + "getwaterLevel/";
    public static String GET_HISTORY  = WEB_URL + "gethistory/";
    public static String CALIBAR_SENESOR_TOP_LEVEL = WEB_URL + "calibrSenstoplev/";
    public static String CALIBAR_SENESOR_BOTTOM_LEVEL = WEB_URL + "calibrSensbotlev/";
    public static String GET_RTC = WEB_URL + "getRTC/";
    public static String SET_RTC = WEB_URL + "setRTC/";

    /**
     * Other required field
     */
    public static String ADMIN_PASSWORD = "Albero@123";
    public static String WIFI_SSID = "\"iSynergy-AP\"";

    /**
     * Shared Preferences variable
     */
    public static String SMARTDEVICE_PREF = "SMARTDEVICE_PREF";
    public static String IS_ADMIN = "IS_ADMIN";
    public static String SETUP_PAGE_NUMBER = "SETUP_PAGE_NUMBER";
}
