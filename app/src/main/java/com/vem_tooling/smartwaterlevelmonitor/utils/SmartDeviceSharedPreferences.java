package com.vem_tooling.smartwaterlevelmonitor.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
}
