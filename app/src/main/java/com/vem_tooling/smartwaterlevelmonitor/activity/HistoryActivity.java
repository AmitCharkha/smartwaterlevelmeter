package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.vem_tooling.smartwaterlevelmonitor.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amit on 5/6/17.
 */

public class HistoryActivity extends AppCompatActivity {

    private LineChart chart;
    private TextView monthYearTextView,backTextView;
    private CompactCalendarView compactCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        }

        chart = (LineChart) findViewById(R.id.chart);

        monthYearTextView = (TextView) findViewById(R.id.monthYearTextView);
        backTextView = (TextView) findViewById(R.id.backTextView);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
                updateDate(formatter.format(firstDayOfNewMonth));
            }
        });

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LineData data = new LineData(getXAxisValues(), getDataSet());
        chart.setDrawGridBackground(false);
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();

        SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
        updateDate(formatter.format(new Date()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void updateDate(String s){
        monthYearTextView.setText(s);
    }

    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(0.000f, 0);
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3);
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4);
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(95.000f, 5);
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(92.000f, 6);
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(98.000f, 7);
        valueSet1.add(v1e8);

        LineDataSet barDataSet1 = new LineDataSet(valueSet1, "Water Level");
        barDataSet1.setColor(Color.rgb(0, 100, 0));
        barDataSet1.setDrawFilled(true);

        setColor(barDataSet1);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("12 AM");
        xAxis.add("03 AM");
        xAxis.add("06 AM");
        xAxis.add("09 AM");
        xAxis.add("12 PM");
        xAxis.add("03 PM");
        xAxis.add("06 PM");
        xAxis.add("09 PM");
        return xAxis;
    }


    @TargetApi(21)
    public void setColor(LineDataSet barDataSet1){
        barDataSet1.setFillDrawable(getDrawable(R.drawable.gradient_blue));
    }


}
