package com.paijwar.firebaseTestApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhoneNumber;
    EditText editAddress;

    ProfileModel profileModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.fullName);
        editPhoneNumber = findViewById(R.id.phoneNumber);
        editAddress = findViewById(R.id.address);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid!=null) {
            DatabaseReference dbRef = database.getReference().child("profile").child(uid);
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull  DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        profileModel = snapshot.getValue(ProfileModel.class);
                        if (profileModel!=null) {
                            editName.setText(profileModel.fullName);
                            editPhoneNumber.setText(profileModel.phoneNumber);
                            editAddress.setText(profileModel.address);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {

                }
            });
        }
    }

    public void onClickSave(View view) {
        String fullName = editName.getText().toString();
        String phoneNumber = editPhoneNumber.getText().toString();
        String address = editAddress.getText().toString();

        if (fullName.isEmpty()){
            Toast.makeText(this,"Name cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if (phoneNumber.isEmpty()){
            Toast.makeText(this,"Phone number cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if (address.isEmpty()){
            Toast.makeText(this,"address cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if (profileModel == null){
            profileModel = new ProfileModel();
        }
        profileModel.fullName = fullName;
        profileModel.phoneNumber = phoneNumber;
        profileModel.address = address;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid!=null){
            DatabaseReference dbRef = database.getReference().child("profile").child(uid);
            dbRef.setValue(profileModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ProfileActivity.this, "Profile saved", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }
}