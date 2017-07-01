package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by amit on 2/6/17.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (new SmartDeviceSharedPreferences(getApplicationContext()).getSetupPageNumber() == 2) {
                if(new SmartDeviceSharedPreferences(getApplicationContext()).getIsAdmin() == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("activityName", "SplashScreen");
                    View easySplashScreenView = new EasySplashScreen(SplashScreen.this)
                            .withFullScreen()
                            .withTargetActivity(CalibrateSensor.class)
                            .withBundleExtras(bundle)
                            .withSplashTimeOut(2000)
                            .withBackgroundResource(android.R.color.holo_red_light)
                            .withHeaderText("")
                            .withFooterText("Copyright 2017")
                            .withBeforeLogoText("VEM Tooling Pvt Ltd")
                            .withLogo(R.drawable.vem_logo)
                            .withAfterLogoText("Smart Water Level Monitor")
                            .create();
                    setContentView(easySplashScreenView);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("activityName", "SplashScreen");
                    View easySplashScreenView = new EasySplashScreen(SplashScreen.this)
                            .withFullScreen()
                            .withTargetActivity(MainActivity.class)
                            .withBundleExtras(bundle)
                            .withSplashTimeOut(2000)
                            .withBackgroundResource(android.R.color.holo_red_light)
                            .withHeaderText("")
                            .withFooterText("Copyright 2017")
                            .withBeforeLogoText("VEM Tooling Pvt Ltd")
                            .withLogo(R.drawable.vem_logo)
                            .withAfterLogoText("Smart Water Level Monitor")
                            .create();
                    setContentView(easySplashScreenView);
                }
            } else if (new SmartDeviceSharedPreferences(getApplicationContext()).getSetupPageNumber() == 3) {
                View easySplashScreenView = new EasySplashScreen(SplashScreen.this)
                        .withFullScreen()
                        .withTargetActivity(MainActivity.class)
                        .withSplashTimeOut(2000)
                        .withBackgroundResource(android.R.color.holo_red_light)
                        .withHeaderText("")
                        .withFooterText("Copyright 2017")
                        .withBeforeLogoText("VEM Tooling Pvt Ltd")
                        .withLogo(R.drawable.vem_logo)
                        .withAfterLogoText("Smart Water Level Monitor")
                        .create();

                setContentView(easySplashScreenView);
            } else {
                View easySplashScreenView = new EasySplashScreen(SplashScreen.this)
                        .withFullScreen()
                        .withTargetActivity(UserSelection.class)
                        .withSplashTimeOut(2000)
                        .withBackgroundResource(android.R.color.holo_red_light)
                        .withHeaderText("")
                        .withFooterText("Copyright 2017")
                        .withBeforeLogoText("VEM Tooling Pvt Ltd")
                        .withLogo(R.drawable.vem_logo)
                        .withAfterLogoText("Smart Water Level Monitor")
                        .create();

                setContentView(easySplashScreenView);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
        }
    }
}
