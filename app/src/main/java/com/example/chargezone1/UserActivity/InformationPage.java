package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chargezone1.Adapter.PageAdapter;
import com.example.chargezone1.R;
import com.google.android.material.tabs.TabLayout;

public class InformationPage extends AppCompatActivity {


    Button skipbtn , nextBtn , gotItBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);


        ViewPager viewPager = findViewById(R.id.prodyct_images_viewpager);
        TabLayout tabLayout = findViewById(R.id.viewPager_Indicator);

        nextBtn = findViewById(R.id.nextBtn);
        gotItBtn = findViewById(R.id.gotitBtn);
        skipbtn = findViewById(R.id.skipBtn);

        PageAdapter adapter = new PageAdapter(this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (position == adapter.getCount() - 1) {
                    // Last page reached, hide next button, show "Got It" button
                    nextBtn.setVisibility(View.GONE);
                    skipbtn.setVisibility(View.GONE);
                    gotItBtn.setVisibility(View.VISIBLE);
                } else {
                    // Not the last page, show next button, hide "Got It" button
                    nextBtn.setVisibility(View.VISIBLE);
                    skipbtn.setVisibility(View.VISIBLE);
                    gotItBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = viewPager.getCurrentItem() + 1;
                if (nextPage < adapter.getCount()) {
                    viewPager.setCurrentItem(nextPage);
                }
            }
        });

        gotItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Got It" button click action
                // For example, you can start the main activity here
                startActivity(new Intent(InformationPage.this, LoginActivity.class));
                finish();
            }
        });


        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationPage.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}