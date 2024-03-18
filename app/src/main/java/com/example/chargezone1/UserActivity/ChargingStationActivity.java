package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chargezone1.Adapter.ChargingStationAdapter;
import com.example.chargezone1.Adapter.StationDataAdapter;
import com.example.chargezone1.MainActivity;
import com.example.chargezone1.Model.ChargingStation;
import com.example.chargezone1.Model.MainViewModel;
import com.example.chargezone1.Model.StationData;
import com.example.chargezone1.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChargingStationActivity extends AppCompatActivity {

    ImageView backBtn, addStationBtn ,deleteBtn , editBtn;
    EditText editTextStationName, editTextStationUnits, editTextStationAddress, editTextStationEmail, editTextStationPhoneNo;
    TextView tvEmpty;
    StationDataAdapter adapter;

    MainViewModel mainViewModel;
    List<StationData> stationDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_station);

        tvEmpty=findViewById(R.id.tv_empty);

        RecyclerView recyclerView = findViewById(R.id.stateDataList);
        List<StationData> stationDataList = readDataFromFile();
        adapter = new StationDataAdapter(stationDataList,this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView  titleTextView= findViewById(R.id.text);
        titleTextView.setText("Charging Station");

        backBtn = findViewById(R.id.backArrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChargingStationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addStationBtn = findViewById(R.id.addStationBtn);
        addStationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddStationPopup();
            }
        });

       deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<StationData> selectedItems = adapter.getSelectedItems();
                if (selectedItems.size() > 0) {
                    deleteSelectedItems(selectedItems);
                } else {
                    Toast.makeText(ChargingStationActivity.this, "No items selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteSelectedItems(ArrayList<StationData> selectedItems) {
        // Remove selected items from the list
        List<StationData> stationDataList = adapter.getStationDataList();
        stationDataList.removeAll(selectedItems);

        // Serialize the updated list
        Gson gson = new Gson();
        String serializedData = gson.toJson(stationDataList);

        // Write the serialized data to the file
        try {
            FileOutputStream fos = openFileOutput("station_data.json", Context.MODE_PRIVATE);
            fos.write(serializedData.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the selected items
        adapter.clearSelectedItems();

        // Notify adapter
        adapter.notifyDataSetChanged();

        // Optionally, you can notify the user about successful deletion
        Toast.makeText(ChargingStationActivity.this, "Selected items deleted", Toast.LENGTH_SHORT).show();
    }

    // Method to read data from file and return as list of StationData objects
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

    // Method to show dialog for adding a new station
    private void showAddStationPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_station, null);
        dialogBuilder.setView(dialogView);

        editTextStationName = dialogView.findViewById(R.id.editTextStationName);
        editTextStationUnits = dialogView.findViewById(R.id.editTextStationUnits);
        editTextStationAddress = dialogView.findViewById(R.id.editTextStationAddress);
        editTextStationPhoneNo = dialogView.findViewById(R.id.editTextStationPhone);
        editTextStationEmail = dialogView.findViewById(R.id.editTextStationEmail);

        Button buttonAdd = dialogView.findViewById(R.id.buttonAdd);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String stationName = editTextStationName.getText().toString();
                String stationUnits = editTextStationUnits.getText().toString();
                String stationAddress = editTextStationAddress.getText().toString();
                String stationPhoneNo = editTextStationPhoneNo.getText().toString();
                String stationEmail = editTextStationEmail.getText().toString();

                // Create a data model object
                StationData stationData = new StationData(stationName, stationUnits, stationAddress, stationPhoneNo, stationEmail);

                // Read existing data from file
                List<StationData> existingDataList = readDataFromFile();

                // Add new data to the list
                existingDataList.add(stationData);

                // Serialize the list
                Gson gson = new Gson();
                String serializedData = gson.toJson(existingDataList);

                // Write the serialized data to the file
                try {
                    FileOutputStream fos = openFileOutput("station_data.json", Context.MODE_PRIVATE);
                    fos.write(serializedData.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });
    }

}
























//        List<ChargingStation> chargingStationList = new ArrayList<>();
//
//        chargingStationList.add(new ChargingStation("Ahmedabad", "137.41 kWh"));
//        chargingStationList.add(new ChargingStation("Surat", "100.01 kWh"));
//        chargingStationList.add(new ChargingStation("Rajkot", "180.21 kWh"));
//        chargingStationList.add(new ChargingStation("Varanasi", "47.12 kWh"));
//        chargingStationList.add(new ChargingStation("Delhi", "37.66 kWh"));
//        chargingStationList.add(new ChargingStation("Vadodara", "187.55 kWh"));
//        chargingStationList.add(new ChargingStation("Jaipur", "201.55 kWh"));
//        chargingStationList.add(new ChargingStation("Udaipur", "101.05 kWh"));

//        ChargingStationAdapter adapter = new ChargingStationAdapter(chargingStationList , null);
//        recyclerView.setAdapter(adapter);

//        List<StationData> stationDataList = new ArrayList<>();