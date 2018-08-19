package com.vem_tooling.smartwaterlevelmonitor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by amit on 15/6/17.
 */

public class SmartDeviceSharedPreferences {
    Context context;

    public SmartDeviceSharedPreferences(Context context){
        this.context = context;
    }

    public int getIsAdmin(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        int count = sharedPreferences.getInt(Constant.IS_ADMIN, 0);
        return count;
    }

    public void setIsAdmin(int count){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(Constant.IS_ADMIN, count);
        edit.commit();
    }

    public int getSetupPageNumber(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        int count = sharedPreferences.getInt(Constant.SETUP_PAGE_NUMBER, 1);
        return count;
    }

    public void setSetupPageNumber(int number){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(Constant.SETUP_PAGE_NUMBER, number);
        edit.commit();
    }

    public long getLastSync(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        long syncTime = sharedPreferences.getLong(Constant.HISTORY_SYNC_TIME, 0);
        return syncTime;
    }

    public void setLastSync(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(Constant.HISTORY_SYNC_TIME, Calendar.getInstance().getTimeInMillis());
        edit.commit();
    }

    public long getCurrentValueLastSync(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        long syncTime = sharedPreferences.getLong(Constant.CURRENT_VALUE_SYNC_TIME, 0);
        return syncTime;
    }

    public void setCurrentValueLastSync(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(Constant.CURRENT_VALUE_SYNC_TIME, Calendar.getInstance().getTimeInMillis());
        edit.commit();
    }

    public String getNewSSID(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        String ssid = sharedPreferences.getString(Constant.NEW_SSID,null);
        return ssid;
    }

    public void setNewSSID(String ssid){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.NEW_SSID, ssid);
        edit.commit();
    }

    public String getNewPassword(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        String ssid = sharedPreferences.getString(Constant.NEW_SSID_PASSWORD,null);
        return ssid;
    }

    public void setNewPassword(String ssid){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.NEW_SSID_PASSWORD, ssid);
        edit.commit();
    }

    public String getHomeWifiSsid(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        String ssid = sharedPreferences.getString(Constant.HOME_WIFI_SSID,null);
        return ssid;
    }

    public void setHomeWifiSsid(String ssid){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.HOME_WIFI_SSID, ssid);
        edit.commit();
    }

    public String getHomeWifiPassword(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        String ssid = sharedPreferences.getString(Constant.HOME_WIFI_SSID_PASSWORD,null);
        return ssid;
    }

    public void setHomeWifiPassword(String ssid){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SMARTDEVICE_PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constant.HOME_WIFI_SSID_PASSWORD, ssid);
        edit.commit();
    }


}
