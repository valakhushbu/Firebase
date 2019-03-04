package com.example.pc.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.pc.chatapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataActivity extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth auth;

    private EditText editName, editMobile, editEmail,editDOb,editStatus;
    private Button btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        createUser();
        finish();
    }

    private void createUser() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        String userId = mDatabase.push().getKey();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User user = new User("","","","","","");

        mDatabase.child(id).setValue(user);
        startActivity(new Intent(UserDataActivity.this, MainActivity.class));
      //  addUserChangeListener();
    }

   /* private void addUserChangeListener() {
        final String user_ = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // User data change listener
        mFirebaseDatabase.child(user_).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    // Check for null
                    if (user == null) {
                        Log.e("", "User data is null!");
                        return;
                    }
                    Log.e("", "User data is changed!" + user.name + ", " + user.email);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("", "Failed to read user", error.toException());
            }
        });
    }
*/
}
