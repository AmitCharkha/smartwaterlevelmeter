package com.vem_tooling.smartwaterlevelmonitor.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.vem_tooling.smartwaterlevelmonitor.vo.AlarmSettingVO;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryRequestVO;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryVO;
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


    /**
     * Get Tank Alarm ON/OFF Value
     */
    public int isAlarmOn(int tankNo,int percentage){
        int res = 0;
        try{
            String query = "SELECT * FROM alarm_setting WHERE tankNo=" + tankNo + " AND percentage=" + percentage;
            SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
            SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
            Cursor cur = db.rawQuery(query,null);
            cur.moveToFirst();

            if(cur.getCount() > 0){
                res = cur.getInt(cur.getColumnIndex("onOff"));
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return res;
    }

    /**
     * Get Tank History status
     */
    public HistoryRequestVO getTankHistoryRequest(int tankNo){
        try{
            String query = "SELECT * FROM history_request WHERE tankNo="+tankNo;
            SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
            SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
            Cursor cur = db.rawQuery(query,null);
            cur.moveToFirst();

            if(cur.getCount() > 0){
                HistoryRequestVO historyRequestVO = new HistoryRequestVO();

                if(cur.getInt(cur.getColumnIndex("id")) != 0){
                    historyRequestVO.setId(cur.getInt(cur.getColumnIndex("id")));
                }else{
                    historyRequestVO.setId(0);
                }

                if(cur.getInt(cur.getColumnIndex("tankNo")) != 0){
                    historyRequestVO.setTankNo(cur.getInt(cur.getColumnIndex("tankNo")));
                }else{
                    historyRequestVO.setId(0);
                }

                if(cur.getInt(cur.getColumnIndex("startValue")) != 0){
                    historyRequestVO.setStartValue(cur.getInt(cur.getColumnIndex("startValue")));
                }else{
                    historyRequestVO.setStartValue(0);
                }

                if(cur.getInt(cur.getColumnIndex("endValue")) != 0){
                    historyRequestVO.setEndValue(cur.getInt(cur.getColumnIndex("endValue")));
                }else{
                    historyRequestVO.setEndValue(0);
                }

                return historyRequestVO;
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return null;
    }

    /**
     * Update tank history request data
     */

    public int updateTankHistoryRequest(HistoryRequestVO historyRequestVO){
        ContentValues contentValues = new ContentValues();
        SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
        SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
        try{
            contentValues.put("startValue", historyRequestVO.getStartValue());
            contentValues.put("endValue", historyRequestVO.getEndValue());
            contentValues.put("updatedDate", Calendar.getInstance().getTimeInMillis());
            return db.update("history_request", contentValues, "tankNo = ?", new String[]{String.valueOf(historyRequestVO.getTankNo())});
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return 0;
    }


    /**
     * Get Tank History of particular tank for particular date
     */
    public List<HistoryVO> getTankHistoryDateWise(int tankNo, long startTime, long endTime){
        SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
        SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
        List<HistoryVO> historyVOs = new ArrayList<>();
        try{
            String query = "SELECT * FROM history WHERE tankNo="+tankNo + " AND dateTime >=" + startTime + " AND dateTime <=" + endTime +" ORDER BY dateTime";

            Cursor cur = db.rawQuery(query,null);
            cur.moveToFirst();

            if(cur.getCount() > 0){
                do {
                    HistoryVO historyVO = new HistoryVO();

                    if (cur.getInt(cur.getColumnIndex("id")) != 0) {
                        historyVO.setId(cur.getInt(cur.getColumnIndex("id")));
                    } else {
                        historyVO.setId(0);
                    }

                    if (cur.getInt(cur.getColumnIndex("tankNo")) != 0) {
                        historyVO.setTankNo(cur.getInt(cur.getColumnIndex("tankNo")));
                    } else {
                        historyVO.setId(0);
                    }

                    if (cur.getInt(cur.getColumnIndex("dateTime")) != 0) {
                        historyVO.setDateTime(cur.getLong(cur.getColumnIndex("dateTime")));
                    } else {
                        historyVO.setDateTime(0);
                    }

                    if (cur.getInt(cur.getColumnIndex("percentage")) != 0) {
                        historyVO.setPercentage(cur.getInt(cur.getColumnIndex("percentage")));
                    } else {
                        historyVO.setPercentage(0);
                    }

                    historyVOs.add(historyVO);
                }while (cur.moveToNext());
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }finally {
            if (smartDeviceDBHelper != null) {
                smartDeviceDBHelper.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return historyVOs;
    }

    /**
     * Tank
     * @param historyVO
     * @return
     */
    public String insertTankHistory(HistoryVO historyVO){
        String res = "More";
        SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
        SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("tankNo",historyVO.getTankNo());
            contentValues.put("dateTime",historyVO.getDateTime());
            contentValues.put("percentage",historyVO.getPercentage());
            contentValues.put("createdDate", Calendar.getInstance().getTimeInMillis());
            contentValues.put("updatedDate", Calendar.getInstance().getTimeInMillis());
            if(getCountForHistoryRecord(historyVO) == 0){
                db.insert("history", null, contentValues);
            }else{
                res = "Change";
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }finally {
            if (smartDeviceDBHelper != null) {
                smartDeviceDBHelper.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
        return res;
    }

    /**
     * Get count for history
     */

    int getCountForHistoryRecord(HistoryVO historyVO){
        SmartDeviceDBHelper smartDeviceDBHelper = new SmartDeviceDBHelper(context);
        SQLiteDatabase db = smartDeviceDBHelper.getWritableDatabase();
        int count = 0;
        try{
            String query = "SELECT Count(*) FROM history WHERE dateTime = " + historyVO.getDateTime() + " AND tankNo = " + historyVO.getTankNo();
            Cursor cur = db.rawQuery(query, null);
            cur.moveToFirst();

            if (cur.getCount() > 0) {
                count = cur.getInt(cur.getColumnIndex("Count(*)"));
            }
            if (cur != null) {
                if (!cur.isClosed())
                    cur.close();
            }
        }catch (SQLiteException e){
            System.out.println("Error : " + e.toString());
        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }
        return count;
    }
}
