package com.vem_tooling.smartwaterlevelmonitor.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONGenerator {

    //giveMeSetSSIDandPassword JSON string
    private static final String userId = "userId";
    private static final String newSSID = "newSSID";
    private static final String newPassword = "newPassword";

    // giveMeSetHomeSSIDandPassword JSON string
    private static final String homeSSID = "ssid";
    private static final String homePassword = "password";


    public static JSONObject giveMeSetSSIDandPassword(long usrId, String ssid, String password) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(userId,usrId);
        jsonObject.put(newSSID,ssid);
        jsonObject.put(newPassword,password);
        return jsonObject;
    }

    public static JSONObject giveMeSetHomeSSIDandPassword(String ssid, String password) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(homeSSID, ssid);
        jsonObject.put(homePassword, password);
        return jsonObject;
    }

}
