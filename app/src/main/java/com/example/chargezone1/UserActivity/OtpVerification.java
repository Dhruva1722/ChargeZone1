package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chargezone1.MainActivity;
import com.example.chargezone1.R;

public class OtpVerification extends AppCompatActivity {

    Button otyVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        otyVerify = findViewById(R.id.verifyBtn);

        otyVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtpVerification.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}