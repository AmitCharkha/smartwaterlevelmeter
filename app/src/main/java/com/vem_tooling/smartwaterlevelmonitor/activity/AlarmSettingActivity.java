package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.db.SmartDeviceDB;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoLightItalicTextView;
import com.vem_tooling.smartwaterlevelmonitor.vo.AlarmSettingVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amit on 20/6/17.
 */

public class AlarmSettingActivity extends AppCompatActivity {

    @BindView(R.id.backTextView)
    LatoLightItalicTextView backTextView;

    @BindView(R.id.switchButton1)
    SwitchCompat switchButton1;

    @BindView(R.id.switchButton2)
    SwitchCompat switchButton2;

    @BindView(R.id.switchButton3)
    SwitchCompat switchButton3;

    @BindView(R.id.switchButton4)
    SwitchCompat switchButton4;

    @BindView(R.id.switchButton5)
    SwitchCompat switchButton5;

    @BindView(R.id.switchButton6)
    SwitchCompat switchButton6;

    @BindView(R.id.switchButton7)
    SwitchCompat switchButton7;

    @BindView(R.id.switchButton8)
    SwitchCompat switchButton8;

    @BindView(R.id.switchButton9)
    SwitchCompat switchButton9;

    @BindView(R.id.switchButton10)
    SwitchCompat switchButton10;

    @BindView(R.id.switchButton11)
    SwitchCompat switchButton11;

    @BindView(R.id.switchButton12)
    SwitchCompat switchButton12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.alarm_screen);
            ButterKnife.bind(this);

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            }

            backTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            setData();

            switchButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(1);
                    alarmSettingVO.setPercentage(70);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 1",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 1",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(1);
                    alarmSettingVO.setPercentage(25);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 1",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 1",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(2);
                    alarmSettingVO.setPercentage(70);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 2",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(2);
                    alarmSettingVO.setPercentage(25);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 2",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(3);
                    alarmSettingVO.setPercentage(70);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 3",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 3",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(3);
                    alarmSettingVO.setPercentage(25);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 3",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 3",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            switchButton7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(4);
                    alarmSettingVO.setPercentage(70);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 4",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 4",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(4);
                    alarmSettingVO.setPercentage(25);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 4",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 4",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(5);
                    alarmSettingVO.setPercentage(70);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 5",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 5",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(5);
                    alarmSettingVO.setPercentage(25);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 5",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 5",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            switchButton11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(6);
                    alarmSettingVO.setPercentage(70);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 6",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 6",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            switchButton12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
                    AlarmSettingVO alarmSettingVO = new AlarmSettingVO();
                    alarmSettingVO.setTankNo(6);
                    alarmSettingVO.setPercentage(25);
                    int res;
                    if(isChecked){
                        alarmSettingVO.setOnOff(1);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }else{
                        alarmSettingVO.setOnOff(0);
                        res = smartDeviceDB.updateAlarmSetting(alarmSettingVO);
                    }

                    if(res > 0){
                        Toast.makeText(getApplicationContext(),"Updated alarm for Tank 6",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Updated failed for Tank 6",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    void setData(){
        ProgressDialog progress = new ProgressDialog(AlarmSettingActivity.this);
        progress.setMessage("Please wait...");
        progress.show();

        try{
            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
            List<AlarmSettingVO> alarmSettingVOs = smartDeviceDB.getAllAlarm();


            for (AlarmSettingVO alarmSettingVO: alarmSettingVOs) {

                switch (alarmSettingVO.getTankNo()){
                    case 1:
                        if(alarmSettingVO.getPercentage() == 70){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton1.setChecked(true);
                            }else{
                                switchButton1.setChecked(false);
                            }
                        }else if(alarmSettingVO.getPercentage() == 25){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton2.setChecked(true);
                            }else{
                                switchButton2.setChecked(false);
                            }
                        }
                        break;
                    case 2:
                        if(alarmSettingVO.getPercentage() == 70){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton3.setChecked(true);
                            }else{
                                switchButton3.setChecked(false);
                            }
                        }else if(alarmSettingVO.getPercentage() == 25){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton4.setChecked(true);
                            }else{
                                switchButton4.setChecked(false);
                            }
                        }
                        break;
                    case 3:
                        if(alarmSettingVO.getPercentage() == 70){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton5.setChecked(true);
                            }else{
                                switchButton5.setChecked(false);
                            }
                        }else if(alarmSettingVO.getPercentage() == 25){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton6.setChecked(true);
                            }else{
                                switchButton6.setChecked(false);
                            }
                        }
                        break;
                    case 4:
                        if(alarmSettingVO.getPercentage() == 70){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton7.setChecked(true);
                            }else{
                                switchButton7.setChecked(false);
                            }
                        }else if(alarmSettingVO.getPercentage() == 25){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton8.setChecked(true);
                            }else{
                                switchButton8.setChecked(false);
                            }
                        }
                        break;
                    case 5:
                        if(alarmSettingVO.getPercentage() == 70){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton9.setChecked(true);
                            }else{
                                switchButton9.setChecked(false);
                            }
                        }else if(alarmSettingVO.getPercentage() == 25){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton10.setChecked(true);
                            }else{
                                switchButton10.setChecked(false);
                            }
                        }
                        break;
                    case 6:
                        if(alarmSettingVO.getPercentage() == 70){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton11.setChecked(true);
                            }else{
                                switchButton11.setChecked(false);
                            }
                        }else if(alarmSettingVO.getPercentage() == 25){
                            if(alarmSettingVO.getOnOff() == 1){
                                switchButton12.setChecked(true);
                            }else{
                                switchButton12.setChecked(false);
                            }
                        }
                        break;
                }
            }

        }catch (Exception e){
            System.out.println("Error : " + e.toString());
        } finally {
            if(progress != null){
                progress.cancel();
            }
        }
    }

}
