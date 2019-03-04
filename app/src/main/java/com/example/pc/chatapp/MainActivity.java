package com.example.pc.chatapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.chatapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    Context context;
    private String id;
    TextView textName, textEmail ,textMobile, textDob, textStatus ;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textMobile = findViewById(R.id.textMobile);
        textDob = findViewById(R.id.textDob);
        textStatus = findViewById(R.id.textStatus);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        readData();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    private void readData(){

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        ref.child(id).addValueEventListener(new ValueEventListener() {

            @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            User user = dataSnapshot.getValue(User.class);

         //   Toast.makeText(MainActivity.this,"your"  ,Toast.LENGTH_SHORT).show();
                textName.setText(user.name);
                textEmail.setText(user.email);
                textMobile.setText(user.mobile);
                textDob.setText(user.dob);
                textStatus.setText(user.status);
               // Glide.with(context).using(new FirebaseImage)
            // Log.d("", "User name: " + user.name + ", email " + user.email);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w("", "Failed to read value.", error.toException());
        }
    });
    }
    //Action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //Action bar menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.setting:

            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return(true);
        case R.id.singout:

            signOut();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    private void signOut() {
       FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
