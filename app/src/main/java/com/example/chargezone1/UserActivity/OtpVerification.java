package com.example.chargezone1.UserActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.chargezone1.MainActivity;
import com.example.chargezone1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {

    private static final String TAG = "OTPVerificationActivity";
     PinView otpEditText;
    private Button verifyButton;
    private String verificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        mAuth = FirebaseAuth.getInstance();

        otpEditText = findViewById(R.id.pinview);
        verifyButton = findViewById(R.id.verifyBtn);


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpVerification.this, MainActivity.class);
                startActivity(intent);
                // Retrieve the OTP entered by the user
//                String otp = ((PinView) findViewById(R.id.pinview)).getText().toString();
//                if (!otp.isEmpty()) {
//                    // Create a PhoneAuthCredential object with the code entered by the user and the verification ID
//                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
//                    // Sign in with the credential
//                    signInWithPhoneAuthCredential(credential);
//                } else {
//                    Toast.makeText(OtpVerification.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        // Retrieve the verification ID from the intent
        verificationId = getIntent().getStringExtra("verificationId");
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(OtpVerification.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OtpVerification.this, MainActivity.class);
                            startActivity(intent);
                            // Here you can navigate to the next activity or perform any other operation
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(OtpVerification.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}