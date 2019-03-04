package com.example.pc.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {

    TextView textSettings, textEditProfile, textChangePassword, textForgetPassword, textDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        textEditProfile = findViewById(R.id.textEditProfile);
        textEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,EditProfileActivity.class));
            }
        });

        textChangePassword = findViewById(R.id.textChangePassword);
        textChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,ChangePassword.class));
            }
        });


        textDeleteAccount = findViewById(R.id.textDeleteAccount);
        textDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,DeleteAccountActivity.class));
            }
        });
    }
}
