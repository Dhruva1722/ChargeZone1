package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chargezone1.Adapter.TabLayoutAdapter;
import com.example.chargezone1.Fragment.AboutStationFragment;
import com.example.chargezone1.Fragment.AmenitiesFragment;
import com.example.chargezone1.Fragment.ChargersFragment;
import com.example.chargezone1.Fragment.PhotoFragment;
import com.example.chargezone1.MainActivity;
import com.example.chargezone1.R;
import com.google.android.material.tabs.TabLayout;

public class FirstStationActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_station);

        TextView titleTextView= findViewById(R.id.text);
        titleTextView.setText("Station");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        TabLayoutAdapter pagerAdapter = new TabLayoutAdapter(getSupportFragmentManager());

        // Add fragments to the PagerAdapter
        pagerAdapter.addFragment(new AboutStationFragment(), "About");
        pagerAdapter.addFragment(new ChargersFragment(), "Chargers");
        pagerAdapter.addFragment(new AmenitiesFragment(), "Amenities");
        pagerAdapter.addFragment(new PhotoFragment(), "Photos");

        // Set the adapter for the ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        backBtn = findViewById(R.id.backArrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstStationActivity.this, ChargingStationActivity.class);
                startActivity(intent);
            }
        });
    }
}