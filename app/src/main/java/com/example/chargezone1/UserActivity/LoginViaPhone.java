package com.example.chargezone1.UserActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.chargezone1.R;

public class LoginViaPhone extends AppCompatActivity {
    private CheckBox mBox;
    Button continueBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_via_phone);

        continueBtn = findViewById(R.id.continueBtn);

        mBox = (CheckBox) findViewById(R.id.checkBox1);
        String checkBoxText = "I agree to all the <a href='http://www.redbus.in/mob/mTerms.aspx' > Terms and Conditions</a>";

        mBox.setText(Html.fromHtml(checkBoxText));
        mBox.setMovementMethod(LinkMovementMethod.getInstance());
        mBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBox.isChecked()) {
                    continueBtn.setVisibility(View.VISIBLE);
                } else {
                    continueBtn.setVisibility(View.GONE);
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginViaPhone.this, OtpVerification.class);
                startActivity(intent);
            }
        });

    }

}