package com.example.chargezone1.UserActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chargezone1.MainActivity;
import com.example.chargezone1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.concurrent.TimeUnit;

public class LoginViaPhone extends AppCompatActivity {
    private CheckBox mBox;
    Button sendOtpButton;

    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    EditText edtPhone, edtOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_via_phone);

        sendOtpButton = findViewById(R.id.sendBtn);

        mBox = (CheckBox) findViewById(R.id.checkBox1);
        String checkBoxText = "I agree to all the <a href='http://www.redbus.in/mob/mTerms.aspx' > Terms and Conditions</a>";

        mBox.setText(Html.fromHtml(checkBoxText));
        mBox.setMovementMethod(LinkMovementMethod.getInstance());
        mBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBox.isChecked()) {
                    sendOtpButton.setVisibility(View.VISIBLE);
                } else {
                    sendOtpButton.setVisibility(View.GONE);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        edtPhone = findViewById(R.id.editPhoneNumber);


        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginViaPhone.this, OtpVerification.class);
                startActivity(intent);
//                String phoneNumber = edtPhone.getText().toString().trim();
//                if (!phoneNumber.isEmpty()) {
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                            "+91" + phoneNumber, // Phone number to verify
//                            60, // Timeout duration
//                            TimeUnit.SECONDS, // Unit of timeout
//                            LoginViaPhone.this, // Activity (for callback binding)
//                            mCallbacks); // OnVerificationStateChangedCallbacks
//                } else {
//                    Toast.makeText(LoginViaPhone.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
//                }
            }
        });

//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                // This method will be called in two situations:
//                // 1 - Instant verification. In some cases, the phone number can be instantly
//                //     verified without needing to send or enter a verification code.
//                // 2 - Auto-retrieval. On some devices, Google Play services can automatically
//                //     detect the incoming verification SMS and perform verification without user action.
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                // This method is called if an invalid request for verification is made,
//                // for instance if the the phone number format is not valid.
//                Toast.makeText(LoginViaPhone.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//                SharedPreferences preferences = getSharedPreferences("user_auth", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putBoolean("isLoggedIn", true);
//                editor.apply();
//                // The SMS verification code has been sent to the provided phone number,
//                // we now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                // Save the verification ID and resending token so we can use them later
//                // Start the OTP verification activity
//                Intent intent = new Intent(LoginViaPhone.this, OtpVerification.class);
//                intent.putExtra("verificationId", s);
//                startActivity(intent);
//            }
//        };
//    }
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(LoginViaPhone.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
//                            // Here you can navigate to the next activity or perform any other operation
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Toast.makeText(LoginViaPhone.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
   }
}
