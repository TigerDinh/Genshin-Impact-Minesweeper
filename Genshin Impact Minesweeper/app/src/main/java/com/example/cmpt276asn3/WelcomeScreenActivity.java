package com.example.cmpt276asn3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


/**
 *  This class is responsible for displaying an animated welcome screen which leads user to
 *  the main menu
 */
public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        setupButtonToMainMenu();
        startAnimationShrinkButton();
    }

    private void setupButtonToMainMenu() {
        Button btn= findViewById(R.id.btnToMainMenu);
        btn.setOnClickListener((v)->{
            Intent mainMenuIntent = MainMenuActivity.getIntent(this);
            startActivity(mainMenuIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private void startAnimationShrinkButton() {
        Button btn = findViewById(R.id.btnToMainMenu);
        btn.setScaleX(5f);
        btn.setScaleY(5f);
        btn.animate().scaleX(1f).scaleY(1f).setDuration(5000).start();
    }


}