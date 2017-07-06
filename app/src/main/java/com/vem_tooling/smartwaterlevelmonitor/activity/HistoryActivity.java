package com.vem_tooling.smartwaterlevelmonitor.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.vem_tooling.smartwaterlevelmonitor.R;
import com.vem_tooling.smartwaterlevelmonitor.db.SmartDeviceDB;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoLightItalicTextView;
import com.vem_tooling.smartwaterlevelmonitor.font_text_view.LatoRegularTextView;
import com.vem_tooling.smartwaterlevelmonitor.utils.SmartDeviceSharedPreferences;
import com.vem_tooling.smartwaterlevelmonitor.vo.HistoryVO;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amit on 5/6/17.
 */

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart chart;

    @BindView(R.id.monthYearTextView)
    LatoRegularTextView monthYearTextView;

    @BindView(R.id.backTextView)
    LatoLightItalicTextView backTextView;

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;

    @BindView(R.id.noDataAvailable)
    LatoRegularTextView noDataAvailable;

    @BindView(R.id.niceSpinner)
    NiceSpinner niceSpinner;

    private int tankNo = 1;
    private Date selDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        ButterKnife.bind(this);
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
            }

            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    try {
                        selDate = dateClicked;
                        setData(dateClicked);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    try{
                        SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
                        updateDate(formatter.format(firstDayOfNewMonth));
                        selDate = firstDayOfNewMonth;
                        setData(firstDayOfNewMonth);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
                    }
                }
            });

            List<String> dataset = new LinkedList<>(Arrays.asList("Tank 1", "Tank 2", "Tank 3", "Tank 4", "Tank 5","Tank 6"));
            niceSpinner.attachDataSource(dataset);

            niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tankNo = position + 1;
                    setData(selDate);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            backTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            SimpleDateFormat formatter = new SimpleDateFormat("MMM yyyy");
            updateDate(formatter.format(new Date()));
            selDate = new Date();
            setData(new Date());
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void updateDate(String s){
        monthYearTextView.setText(s);
    }

    private ArrayList<ILineDataSet> getDataSet(List<HistoryVO> historyVOs) {
        ArrayList<ILineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();
        int i = 0;
        for (HistoryVO historyVO:historyVOs) {
            BarEntry v1e2 = new BarEntry(historyVO.getPercentage(), i);
            valueSet1.add(v1e2);
            i++;
        }
        /*BarEntry v1e1 = new BarEntry(0.000f, 0);
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
        valueSet1.add(v1e8);*/

        LineDataSet barDataSet1 = new LineDataSet(valueSet1, "Water Level");
        barDataSet1.setColor(Color.rgb(0, 100, 0));
        barDataSet1.setDrawFilled(true);

        setColor(barDataSet1);

        dataSets = new ArrayList<>();

        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues(List<HistoryVO> historyVOs) {
        ArrayList<String> xAxis = new ArrayList<>();
        for (HistoryVO historyVO:historyVOs) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(historyVO.getDateTime());
            xAxis.add(String.format("%02d",calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d",calendar.get(Calendar.MINUTE)));
        }

        /*xAxis.add("12 AM");
        xAxis.add("03 AM");
        xAxis.add("06 AM");
        xAxis.add("09 AM");
        xAxis.add("12 PM");
        xAxis.add("03 PM");
        xAxis.add("06 PM");
        xAxis.add("09 PM");*/
        return xAxis;
    }


    @TargetApi(21)
    public void setColor(LineDataSet barDataSet1){
        barDataSet1.setFillDrawable(getDrawable(R.drawable.gradient_blue));
    }


    public void setData(Date dateClicked){
        final ProgressDialog progress = new ProgressDialog(HistoryActivity.this);
        try{
            progress.setMessage("Fetching Data.. Please Wait...");
            progress.show();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateClicked.getTime());
            String date = calendar.get(Calendar.DATE) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " 00:00:00";
            String date1 = calendar.get(Calendar.DATE) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " 23:59:59";

            SmartDeviceDB smartDeviceDB = new SmartDeviceDB(getApplicationContext());
            List<HistoryVO> historyVOs = smartDeviceDB.getTankHistoryDateWise(tankNo, formatter.parse(date).getTime(), formatter.parse(date1).getTime());

            if (historyVOs != null && historyVOs.size() > 0) {
                noDataAvailable.setVisibility(View.GONE);
                chart.setVisibility(View.VISIBLE);
                LineData data = new LineData(getXAxisValues(historyVOs), getDataSet(historyVOs));
                chart.setDrawGridBackground(false);
                chart.setData(data);
                chart.setDescription("");
                chart.animateXY(2000, 2000);
                chart.invalidate();
            }else{
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date2 = new Date(new SmartDeviceSharedPreferences(getApplicationContext()).getLastSync());
                if(new SmartDeviceSharedPreferences(getApplicationContext()).getLastSync() > 0) {
                    noDataAvailable.setText("No record found for selected date \n \n Last history updated on \n" + formatter1.format(date2));
                }else{
                    noDataAvailable.setText("Please update history for first time \nfrom\nSetting ---> Update Tank History");
                }
                noDataAvailable.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
            }

            if(progress != null){
                progress.cancel();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error Occurred", Toast.LENGTH_LONG).show();
            if(progress != null){
                progress.cancel();
            }
        }
    }


}
