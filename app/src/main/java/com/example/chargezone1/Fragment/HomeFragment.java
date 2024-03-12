package com.example.chargezone1.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chargezone1.R;
import com.example.chargezone1.UserActivity.AnalyticsActivity;
import com.example.chargezone1.UserActivity.ChargingStationActivity;
import com.example.chargezone1.UserActivity.SettingActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;

    RelativeLayout chargingActivity , analyticsActivity, consumerActivity, settingActivity;

    ArrayList barEntriesArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);


        chargingActivity = view.findViewById(R.id.chargingStationLy);
        chargingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), ChargingStationActivity.class);
                startActivity(intent);
            }
        });

        analyticsActivity = view.findViewById(R.id.analysisLY);
        analyticsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AnalyticsActivity.class);
                startActivity(intent);
            }
        });

        settingActivity = view.findViewById(R.id.settingLY);
        settingActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        barChart = view.findViewById(R.id.idBarChart);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 100));
        entries.add(new BarEntry(1f, 50));
        entries.add(new BarEntry(2f, 150));
        entries.add(new BarEntry(3f, 80));
        entries.add(new BarEntry(4f, 180));

        // Labels for the X-axis (states)
        final String[] labels = new String[]{"Surat", "Rajkot", "Delhi", "Ahmedabad", "Vadodara"};

        BarDataSet dataSet = new BarDataSet(entries, "Charging Stations");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        barChart.getAxisLeft().setEnabled(true);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);

        return view;
    }


}