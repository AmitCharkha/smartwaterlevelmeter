package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginTextView)
    LatoRegularTextView loginTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HardwareDeviceSetup.class);
                startActivity(intent);
            }
        });
    }
}
