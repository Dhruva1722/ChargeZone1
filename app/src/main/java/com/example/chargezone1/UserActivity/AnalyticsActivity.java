package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chargezone1.Adapter.StationDataAdapter;
import com.example.chargezone1.MainActivity;
import com.example.chargezone1.Model.StationData;
import com.example.chargezone1.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsActivity extends AppCompatActivity {

//    implements OnChartValueSelectedListener
    ImageView backBtn;
    private static final int REQUEST_CODE_ADD_DATA = 1;

    PieChart pieChart;
    List<StationData> stationDataList;
    RecyclerView recyclerView;
    StationDataAdapter adapter;
     List<PieEntry> pieEntries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analytics);

        TextView  titleTextView= findViewById(R.id.text);
        titleTextView.setText("Analytics");

        recyclerView = findViewById(R.id.analysis_list);
//        List<StationData> stationDataList = readDataFromFile();

        stationDataList = readDataFromFile();
        adapter = new StationDataAdapter(stationDataList, this,this); // Pass the activity reference
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn = findViewById(R.id.backArrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalyticsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        pieChart = findViewById(R.id.pieChart);

        if (stationDataList == null || stationDataList.isEmpty()) {
            // Handle the case where no data is available
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configure PieChart
        configurePieChart(stationDataList);
        setupPieChartListener();

//        pieChart.setOnChartValueSelectedListener(this);
    }

    private void configurePieChart(List<StationData> stationDataList) {
        pieChart = findViewById(R.id.pieChart);
        HashMap<String, Float> stationDataMap = new HashMap<>();
        for (StationData data : stationDataList) {
            String stationAddress = data.getStationAddress();
            float kwhValue = Float.parseFloat(data.getStationUnits());
            if (stationDataMap.containsKey(stationAddress)) {
                kwhValue += stationDataMap.get(stationAddress);
            }
            stationDataMap.put(stationAddress, kwhValue);
        }

        pieEntries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : stationDataMap.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(40f, 0f, 40f, 10f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(50f);

        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextSize(15f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setCenterTextColor(Color.parseColor("#222222"));
        pieChart.setCenterText("511.53\n Total Used");

        PieDataSet dataSet = new PieDataSet(pieEntries, "Stations");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setSliceSpace(2f);
        // Value lines
        dataSet.setValueLinePart1Length(0.6f);
        dataSet.setValueLinePart2Length(0.3f);
        dataSet.setValueLineWidth(2f);
        dataSet.setValueLinePart1OffsetPercentage(115f); // Line starts outside of chart
        dataSet.setUsingSliceColorAsValueLineColor(true);

        // Value text appearance
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueTextSize(16f);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        dataSet.setValueTextColor(Color.BLACK);

        int colorBlack = Color.parseColor("#000000");
        pieChart.setEntryLabelColor(colorBlack);

        // Value formatting
        dataSet.setValueFormatter(new ValueFormatter() {
            private NumberFormat formatter = NumberFormat.getPercentInstance();

            @Override
            public String getFormattedValue(float value) {
                return formatter.format(value / 100f);
            }
        });
        pieChart.setUsePercentValues(true);

        dataSet.setSelectionShift(3f);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setYOffset(5f);
        legend.setYEntrySpace(0f);

        pieChart.getDescription().setEnabled(false);

        pieChart.setData(new PieData(dataSet));
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

    }

    private List<StationData> readDataFromFile() {
        List<StationData> stationDataList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput("station_data.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            // Convert StringBuilder to String
            String jsonData = sb.toString();

            // Deserialize JSON string into a list of StationData objects
            Gson gson = new Gson();
            Type listType = new TypeToken<List<StationData>>() {}.getType();
            stationDataList = gson.fromJson(jsonData, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stationDataList;
    }

    private void setupPieChartListener()  {
        Log.d("AnalyticsActivity", "Setting up PieChart listener");
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null) return;

                PieEntry entry = (PieEntry) e;
                String selectedStationAddress = entry.getLabel();
                Log.d("AnalyticsActivity", "Selected Station Address: " + selectedStationAddress);

                // Find the position of the selected station in the data list
                int position = findStationPosition(selectedStationAddress);
                Log.d("AnalyticsActivity", "Position in RecyclerView: " + position);

                // Highlight the item in the RecyclerView
                if (position != -1) {
                    adapter.highlightItem(position);
                }
            }

            @Override
            public void onNothingSelected() {
                // Handle case when nothing is selected (optional)
            }
        });
    }

    private int findStationPosition(String stationAddress) {
        if (stationDataList != null) {
            for (int i = 0; i < stationDataList.size(); i++) {
                if (stationDataList.get(i).getStationAddress().equals(stationAddress)) {
                    return i;
                }
            }
        }
        return -1; // Station not found or list is null
    }


}






//        recyclerView = findViewById(R.id.analysis_list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        List<ChargingStation> chargingStationList = new ArrayList<>();
//
//        chargingStationList.add(new ChargingStation("Ahmedabad", "137.41 kWh"));
//        chargingStationList.add(new ChargingStation("Surat", "100.01 kWh"));
//        chargingStationList.add(new ChargingStation("Rajkot", "180.21 kWh"));
//        chargingStationList.add(new ChargingStation("Varanasi", "47.12 kWh"));
//        chargingStationList.add(new ChargingStation("Delhi", "37.66 kWh"));
//        chargingStationList.add(new ChargingStation("Vadodara", "187.55 kWh"));
////        chargingStationList.add(new ChargingStation("Jaipur", "201.55 kWh"));
////        chargingStationList.add(new ChargingStation("Udaipur", "101.05 kWh"));
//
//         adapter = new ChargingStationAdapter(chargingStationList,null);
//         recyclerView.setAdapter(adapter);
//
//        adapter.notifyDataSetChanged();
//
//        backBtn = findViewById(R.id.backArrow);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AnalyticsActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        pieChart = findViewById(R.id.pieChart);
//        setupPieChart();
//        loadPieChartData();
//
//        pieChart.setOnChartValueSelectedListener(this);




//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        PieEntry selectedEntry = (PieEntry) e;
//        pieChart.highlightValues(null);
//        // Check if the adapter and RecyclerView are properly initialized
//        if (adapter != null && recyclerView != null) {
//            // Find the corresponding item in your RecyclerView dataset
//            String selectedLabel = selectedEntry.getLabel();
//            for (int i = 0; i < adapter.getItemCount(); i++) {
//                 ChargingStation chargingStation = adapter.getItem(i);
//                View itemView = recyclerView.getLayoutManager().findViewByPosition(i);
//                if (itemView != null) {
//                    if (chargingStation.getName().equals(selectedLabel)) {
//                        // Highlight the item here
//                        itemView.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
//                    } else {
//                        // Remove highlight for other items
//                        itemView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
//                    }
//                }
//            }
//        } else {
//            Log.e("-------", "Adapter or RecyclerView is not initialized properly");
//        }
//    }

//    @Override
//    public void onNothingSelected() {
//        pieChart.highlightValues(null);
//    }
//    private void setupPieChart() {
//        pieChart.setUsePercentValues(false);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(40f, 0f, 40f, 10f);
//
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleRadius(50f);
//
//        pieChart.setDrawCenterText(true);
//        pieChart.setCenterTextSize(15f);
//        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
//        pieChart.setCenterTextColor(Color.parseColor("#222222"));
//        pieChart.setCenterText("511.53\n Total Used");
//    }
//    private void loadPieChartData() {
//        List<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(50f,"Surat" ));
//        entries.add(new PieEntry(100f , " Ahmedabad"));
//        entries.add(new PieEntry(157f,"Varanasi"));
//        entries.add(new PieEntry(134f , "Rajkot"));
//        entries.add(new PieEntry(70f , "Delhi"));
//        entries.add(new PieEntry(30f,"Vadodara"));
////        entries.add(new PieEntry(50f,"Jaipur"));
////        entries.add(new PieEntry(80f,"Udaipur"));
//
//        PieDataSet dataSet = new PieDataSet(entries, "");
//
//        // Chart colors
//        List<Integer> colors = new ArrayList<>();
//        colors.add(Color.parseColor("#4777c0"));
//        colors.add(Color.parseColor("#a374c6"));
//        colors.add(Color.parseColor("#4fb3e8"));
//        colors.add(Color.parseColor("#99cf43"));
//        colors.add(Color.parseColor("#fdc135"));
//        colors.add(Color.parseColor("#fd9a47"));
////        colors.add(Color.parseColor("#eb6e7a"));
////        colors.add(Color.parseColor("#6785c2"));
//        dataSet.setColors(colors);
//
//        dataSet.setSliceSpace(2f);
//        // Value lines
//        dataSet.setValueLinePart1Length(0.6f);
//        dataSet.setValueLinePart2Length(0.3f);
//        dataSet.setValueLineWidth(2f);
//        dataSet.setValueLinePart1OffsetPercentage(115f); // Line starts outside of chart
//        dataSet.setUsingSliceColorAsValueLineColor(true);
//
//        // Value text appearance
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setValueTextSize(16f);
//        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
//        dataSet.setValueTextColor(Color.BLACK);
//
//        int colorBlack = Color.parseColor("#000000");
//        pieChart.setEntryLabelColor(colorBlack);
//
//        // Value formatting
//        dataSet.setValueFormatter(new ValueFormatter() {
//            private NumberFormat formatter = NumberFormat.getPercentInstance();
//
//            @Override
//            public String getFormattedValue(float value) {
//                return formatter.format(value / 100f);
//            }
//        });
//        pieChart.setUsePercentValues(true);
//
//        dataSet.setSelectionShift(3f);
//
//        Legend legend = pieChart.getLegend();
//        legend.setEnabled(true);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
////        legend.setYOffset(5f);
////        legend.setYEntrySpace(0f);
//
//        pieChart.getDescription().setEnabled(false);
//
//        pieChart.setData(new PieData(dataSet));
//        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
//
//        PieData data = new PieData(dataSet);
//        pieChart.setData(data);
//
//    }