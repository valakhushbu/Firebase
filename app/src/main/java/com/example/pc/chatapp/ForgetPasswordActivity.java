package com.example.pc.chatapp;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText editEmailId;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editEmailId = findViewById(R.id.editEmailId);

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Method for forget password
                forgetPassword();
            }
        });
    }

    private void forgetPassword() {

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                            Log.d("", "Email sent.");
                        }
                        else{
                            Toast.makeText(ForgetPasswordActivity.this, "Something Wrong Please try Again!", Toast.LENGTH_SHORT).show();
                            Log.d("", "Email sent fail");
                        }
                    }
                });
    }
}
