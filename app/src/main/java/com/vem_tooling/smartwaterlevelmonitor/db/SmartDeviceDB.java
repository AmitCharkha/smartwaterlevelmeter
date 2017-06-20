package com.vem_tooling.smartwaterlevelmonitor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.vem_tooling.smartwaterlevelmonitor.vo.AlarmSettingVO;
import com.vem_tooling.smartwaterlevelmonitor.vo.TankVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by amit on 20/6/17.
 */

public class SmartDeviceDB {

    Context context;

    public SmartDeviceDB(Context context){
        this.context = context;
    }


    /**
     * Update Alarm
     */

    public int updateAlarmSetting(AlarmSettingVO alarmSettingVO){
        ContentValues contentValues = new ContentValues();
        SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
        SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
        try{
            contentValues.put("onOff", alarmSettingVO.getOnOff());
            contentValues.put("updatedDate", Calendar.getInstance().getTimeInMillis());
            return db.update("alarm_setting", contentValues, "tankNo = ? AND percentage = ?", new String[]{String.valueOf(alarmSettingVO.getTankNo()),String.valueOf(alarmSettingVO.getPercentage())});
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return 0;
    }

    /**
     * Get All Alarm
     */

    public List<AlarmSettingVO> getAllAlarm(){
        List<AlarmSettingVO> alarmSettingsList = new ArrayList<>();
        try{
            String query = "SELECT * FROM alarm_setting";
            SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
            SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
            Cursor cur = db.rawQuery(query,null);
            cur.moveToFirst();

            if(cur.getCount() > 0){
                do{
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();

                    if(cur.getInt(cur.getColumnIndex("id")) != 0){
                        alarmSettingVO.setId(cur.getInt(cur.getColumnIndex("id")));
                    }else{
                        alarmSettingVO.setId(0);
                    }

                    if(cur.getInt(cur.getColumnIndex("tankNo")) != 0){
                        alarmSettingVO.setTankNo(cur.getInt(cur.getColumnIndex("tankNo")));
                    }else{
                        alarmSettingVO.setId(0);
                    }

                    if(cur.getInt(cur.getColumnIndex("percentage")) != 0){
                        alarmSettingVO.setPercentage(cur.getInt(cur.getColumnIndex("percentage")));
                    }else{
                        alarmSettingVO.setPercentage(0);
                    }

                    if(cur.getInt(cur.getColumnIndex("onOff")) != 0){
                        alarmSettingVO.setOnOff(cur.getInt(cur.getColumnIndex("onOff")));
                    }else{
                        alarmSettingVO.setOnOff(0);
                    }

                    alarmSettingsList.add(alarmSettingVO);
                }while (cur.moveToNext());
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return alarmSettingsList;
    }


    /**
     * Update tank percentage
     */

    public int updateTankPercentage(TankVO tankVO){
        ContentValues contentValues = new ContentValues();
        SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
        SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
        try{
            contentValues.put("percentage", tankVO.getPercentage());
            contentValues.put("updatedDate", Calendar.getInstance().getTimeInMillis());
            return db.update("tank", contentValues, "tankNo = ?", new String[]{String.valueOf(tankVO.getTankNo())});
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return 0;
    }

    /**
     * Get all tank percentaeg
     */
    public List<TankVO> getTankPercentage(){
        List<TankVO> tankVOList = new ArrayList<>();
        try{
            String query = "SELECT * FROM tank";
            SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
            SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
            Cursor cur = db.rawQuery(query,null);
            cur.moveToFirst();

            if(cur.getCount() > 0){
                do{
                    TankVO tankVO = new TankVO();

                    if(cur.getInt(cur.getColumnIndex("id")) != 0){
                        tankVO.setId(cur.getInt(cur.getColumnIndex("id")));
                    }else{
                        tankVO.setId(0);
                    }

                    if(cur.getInt(cur.getColumnIndex("tankNo")) != 0){
                        tankVO.setTankNo(cur.getInt(cur.getColumnIndex("tankNo")));
                    }else{
                        tankVO.setId(0);
                    }

                    if(cur.getInt(cur.getColumnIndex("percentage")) != 0){
                        tankVO.setPercentage(cur.getInt(cur.getColumnIndex("percentage")));
                    }else{
                        tankVO.setPercentage(0);
                    }

                    tankVOList.add(tankVO);
                }while (cur.moveToNext());
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return tankVOList;
    }

}
