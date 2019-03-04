package com.example.pc.chatapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText editOldPassword,editNewPassword,editNewPasswordMatch;
    Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editOldPassword = findViewById(R.id.editOldPassword);
        editNewPassword = findViewById(R.id.editNewPassword);
        editNewPasswordMatch = findViewById(R.id.editNewPasswordMatch);

        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //method for change password
                changePassword();
            }
        });
    }

    private void changePassword() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String newPassword = editNewPassword.getText().toString().trim();
        String newPAsswordMatch = editNewPasswordMatch.getText().toString();

        if (user != null && !newPassword.equals("")) {
            if (editNewPassword.getText().toString().trim().length() < 6) {
                editNewPassword.setError("Password too short, enter minimum 6 characters");
            }
            else {
                if(newPassword.equals("") && newPAsswordMatch.equals("")){
                    Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
                }
                else if(newPassword.equals(newPAsswordMatch)){
                user.updatePassword(editNewPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePassword.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChangePassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            }
        }
        else if (editNewPassword.getText().toString().trim().equals("")) {
            editNewPassword.setError("Enter password");

        }
    }
}
