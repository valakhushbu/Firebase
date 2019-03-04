package com.example.pc.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.chatapp.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView textName,textEmail,textMobile,textDob,textStatus,textImageEdit;
    EditText editName,editEmail,editMobile,editDob,editStatus;
    TextView textNameEdit,textEmailEdit, textMobileEdit, textDobEdit, textStatusEdit;
    TextView editNameEdit,editEmailEdit, editMobileEdit, editDobEdit, editStatusEdit;
    ImageView userImage;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public FirebaseAuth auth;

    private String downloadUrl;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editName=findViewById(R.id.editName);
        editEmail=findViewById(R.id.editEmail);
        editMobile=findViewById(R.id.editMobile);
        editDob=findViewById(R.id.editDob);
        editStatus=findViewById(R.id.editStatus);

       /* textName=findViewById(R.id.textName);
        textEmail=findViewById(R.id.textEmail);
        textMobile=findViewById(R.id.textMobile);
        textDob=findViewById(R.id.textDob);
        textStatus=findViewById(R.id.textStatus);
*/
        textNameEdit = findViewById(R.id.textNameEdit);

        editNameEdit = findViewById(R.id.editNameEdit);

        userImage = findViewById(R.id.userImg);


       /* textName.setVisibility(View.VISIBLE);
        textEmail.setVisibility(View.VISIBLE);
        textMobile.setVisibility(View.VISIBLE);
        textDob.setVisibility(View.VISIBLE);
        textStatus.setVisibility(View.VISIBLE);*/
        editNameEdit.setVisibility(View.GONE);
        textNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setEnabled(true);
                editEmail.setEnabled(true);
                editMobile.setEnabled(true);
                editDob.setEnabled(true);
                editStatus.setEnabled(true);
              /*  textName.setVisibility(View.GONE);
                textEmail.setVisibility(View.GONE);
                textMobile.setVisibility(View.GONE);
                textDob.setVisibility(View.GONE);
                textStatus.setVisibility(View.GONE);
                textNameEdit.setVisibility(View.GONE);
*/            textNameEdit.setVisibility(View.GONE);
              editNameEdit.setVisibility(View.VISIBLE);
            }
        });

        editNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* textName.setVisibility(View.VISIBLE);
                textEmail.setVisibility(View.VISIBLE);
                textMobile.setVisibility(View.VISIBLE);
                textDob.setVisibility(View.VISIBLE);
                textStatus.setVisibility(View.VISIBLE);
                textNameEdit.setVisibility(View.VISIBLE);*/
                textNameEdit.setVisibility(View.VISIBLE);
                editNameEdit.setVisibility(View.GONE);

            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        userImage = findViewById(R.id.userImg);
        textImageEdit= findViewById(R.id.textImageEdit);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        textImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(userImage.equals("")){
                  Toast.makeText(EditProfileActivity.this,"please",Toast.LENGTH_SHORT).show();
              }else {
                  Toast.makeText(EditProfileActivity.this,"please not",Toast.LENGTH_SHORT).show();
                  uploadImage();
              }
            }
        });

        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        editNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
}
//uploda in storage firebase
    private void uploadImage() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putFile(filePath);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    downloadUrl = task.getResult().toString();
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    mDatabase.child(id).child("photo").setValue(downloadUrl);

                    Toast.makeText(EditProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                userImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void updateUser() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userId = mDatabase.push().getKey();
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String mobile = editMobile.getText().toString();
        String dob = editDob.getText().toString();
        String status = editStatus.getText().toString();
        if (!TextUtils.isEmpty(name))
            mDatabase.child(id).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mDatabase.child(id).child("email").setValue(email);

        if (!TextUtils.isEmpty(mobile))
            mDatabase.child(id).child("mobile").setValue(mobile);

        if (!TextUtils.isEmpty(dob))
            mDatabase.child(id).child("dob").setValue(dob);

        if (!TextUtils.isEmpty(status))
            mDatabase.child(id).child("status").setValue(status);
        Toast.makeText(this,"Upadte succsessfully .. ",Toast.LENGTH_SHORT).show();
    }

}


