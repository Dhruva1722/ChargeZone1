package com.example.chargezone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chargezone1.Fragment.BookingFragment;
import com.example.chargezone1.Fragment.ChargersFragment;
import com.example.chargezone1.Fragment.Contact_UsFragment;
import com.example.chargezone1.Fragment.HomeFragment;
import com.example.chargezone1.Fragment.MyVehiclesFragment;
import com.example.chargezone1.Fragment.MyWalletFragment;
import com.example.chargezone1.Fragment.ProfileFragment;
import com.example.chargezone1.UserActivity.ChargingStationActivity;
import com.example.chargezone1.UserActivity.LoginActivity;
import com.example.chargezone1.UserActivity.TermsAndCondition;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth mAuth;
    TextView logoutBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        ImageView toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        Drawable customIcon = ContextCompat.getDrawable(this, R.drawable.profile);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.setHomeAsUpIndicator(customIcon);
//        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        //Handle toolbar click to open the navigation drawer
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        loadFragment(new HomeFragment());

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.nav_home) {
                    fragment = new HomeFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
               } else if (item.getItemId() == R.id.nav_acc) {
                    fragment = new ProfileFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_charge_history) {
                    Intent intent = new Intent(MainActivity.this, ChargingStationActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_vehicles) {
                    fragment = new MyVehiclesFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_my_wallet) {
                    fragment = new MyWalletFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_booking) {
                    fragment = new BookingFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId() == R.id.nav_contact_us) {
                    fragment = new Contact_UsFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_termscondition) {
                    Intent intent = new Intent(MainActivity.this, TermsAndCondition.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (fragment != null) {
                    loadFragment(fragment);
                }
                return true;
            }
        });

        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout method
                logout();
            }
        });

    }

    private void logout() {
        // Sign out the user from Firebase Authentication
        mAuth.signOut();

        // Clear the authentication state from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_auth", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Redirect the user to the LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish the MainActivity to prevent going back to it when pressing back button
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.relativelayout, fragment)
                .commit();
    }
}