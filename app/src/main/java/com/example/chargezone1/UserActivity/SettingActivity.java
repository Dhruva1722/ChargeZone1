package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chargezone1.MainActivity;
import com.example.chargezone1.R;

public class SettingActivity extends AppCompatActivity {

    ImageView backBtn ;

    ListView settingsListView;
    String[] settingsOptions = {"Add Data", "Terms & Conditions", "Logout", "About Us", "Contact Us"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        settingsListView = findViewById(R.id.settingsListView);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_settings, settingsOptions);
        settingsListView.setAdapter(adapter);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Perform action based on item clicked
                switch (position) {
                    case 0:
                        Intent intent = new Intent(SettingActivity.this, ChargingStationActivity.class);
                        startActivity(intent);
                        Toast.makeText(SettingActivity.this, "Add Data clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent1 = new Intent(SettingActivity.this, TermsAndCondition.class);
                        startActivity(intent1);
                        Toast.makeText(SettingActivity.this, "Terms & Conditions clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent2 = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(SettingActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        // About Us
                        // Replace with your logic
                        Toast.makeText(SettingActivity.this, "About Us clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        // Contact Us
                        // Replace with your logic
                        Toast.makeText(SettingActivity.this, "Contact Us clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });



        backBtn = findViewById(R.id.backArrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}