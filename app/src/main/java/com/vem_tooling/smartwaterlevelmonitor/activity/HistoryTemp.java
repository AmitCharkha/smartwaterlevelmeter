package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amit on 15/6/17.
 */

public class HistoryTemp extends AppCompatActivity {

    @BindView(R.id.backTextView)
    TextView backTextView;

    @BindView(R.id.responseTextView)
    TextView responseTextView;

    @BindView(R.id.callAPI)
    Button callAPI;

    @BindView(R.id.startValue)
    EditText startValue;

    @BindView(R.id.endValue)
    EditText endValue;

    @BindView(R.id.tankNumber)
    EditText tankNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_history);

        ButterKnife.bind(this);

        //This is test again
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        }

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        callAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                int startVal = Integer.parseInt(startValue.getText().toString());
                int endVal = Integer.parseInt(endValue.getText().toString());
                int tankNum = Integer.parseInt(tankNumber.getText().toString());

                    Toast.makeText(HistoryTemp.this,"Calling Get HIstory API",Toast.LENGTH_LONG).show();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.GET_HISTORY + tankNum + "/" + startVal + "/" + endVal + "/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                responseTextView.setText(response);
                            }catch (Exception e){
                                Toast.makeText(HistoryTemp.this,"Error occurred",Toast.LENGTH_LONG).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(HistoryTemp.this,"Error occurred",Toast.LENGTH_LONG).show();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(HistoryTemp.this);
                    requestQueue.add(stringRequest);
                }catch (Exception e){
                    Toast.makeText(HistoryTemp.this,"Error occurred",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
