package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by amit on 12/6/17.
 */

public class UserSelection extends AppCompatActivity {

    @BindView(R.id.continueTextView)
    LatoRegularTextView continueTextView;

    @BindView(R.id.verifyTextview)
    LatoRegularTextView verifyTextview;

    @BindView(R.id.inputPassword)
    EditText inputPassword;

    @BindView(R.id.linearLayoutOne)
    LinearLayout linearLayoutOne;

    @BindView(R.id.relativeLayoutOne)
    RelativeLayout relativeLayoutOne;

    @BindView(R.id.goToSettingTextView)
    LatoRegularTextView goToSettingTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SmartDeviceSharedPreferences(getApplicationContext()).setIsAdmin(0);
                new SmartDeviceSharedPreferences(getApplicationContext()).setSetupPageNumber(2);

                Intent intent = new Intent(UserSelection.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        verifyTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputPassword.getText().toString().isEmpty()){
                    if(inputPassword.getText().toString().equals(Constant.ADMIN_PASSWORD)){
                        new SmartDeviceSharedPreferences(getApplicationContext()).setIsAdmin(1);
                        new SmartDeviceSharedPreferences(getApplicationContext()).setSetupPageNumber(2);

                        Intent intent = new Intent(UserSelection.this, CalibrateSensor.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("activityName", "SplashScreen");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else{
                        new SweetAlertDialog(UserSelection.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please enter correct password")
                                .show();
                    }
                }else{
                    new SweetAlertDialog(UserSelection.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Please enter password")
                            .show();
                }
            }
        });

        goToSettingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if(wifiInfo.getSSID().toString().equals(Constant.WIFI_SSID)){
            relativeLayoutOne.setVisibility(View.GONE);
            linearLayoutOne.setVisibility(View.GONE);
            continueTextView.setEnabled(true);
            verifyTextview.setEnabled(true);
            inputPassword.setEnabled(true);
        }else{
            relativeLayoutOne.setVisibility(View.VISIBLE);
            linearLayoutOne.setVisibility(View.VISIBLE);
            continueTextView.setEnabled(false);
            verifyTextview.setEnabled(false);
            inputPassword.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

        }
    }
}
