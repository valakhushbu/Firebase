package com.example.pc.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountActivity extends AppCompatActivity {

    EditText editPassword;
    Button btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        editPassword = findViewById(R.id.editPassword);

        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //method for delete account
                deleteAccount();
            }
        });

    }

    private void deleteAccount() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String password = editPassword.getText().toString();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);


            if (editPassword.equals("")) {
                Toast.makeText(DeleteAccountActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
            }
                else{
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DeleteAccountActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DeleteAccountActivity.this, SignUpActivity.class));
                                finish();
                            } else {
                                Toast.makeText(DeleteAccountActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }}
        }

