package com.vem_tooling.smartwaterlevelmonitor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by amit on 9/6/17.
 */

public class SmartDeviceDBHelper extends SQLiteAssetHelper{

    protected static final String DATABASE_NAME = "smartdevice";

    protected static final int DATABASE_VERSION = 1;

    private static SmartDeviceDBHelper helper;

    public SmartDeviceDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SmartDeviceDBHelper getInstance(Context context) {
        if(helper == null)
            helper = new SmartDeviceDBHelper(context);
        return helper;
    }

    public static synchronized SmartDeviceDBHelper getInstance() {
        if (helper == null) {
            throw new IllegalStateException(SmartDeviceDBHelper.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return helper;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public synchronized void close() {
        super.close();
    }

}
