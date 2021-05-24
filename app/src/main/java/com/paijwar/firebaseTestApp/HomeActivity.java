package com.paijwar.firebaseTestApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClickLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void onClickProfile(View view) {
        //launch profile activity
        startActivity(new Intent(this, ProfileActivity.class));
    }
}