package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        //To hide the toolbar during launching the splash screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(() -> {
            //to direct to the main screen
            Intent intent = new Intent(splash_screen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 1500);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //on backPressed exit the app
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}