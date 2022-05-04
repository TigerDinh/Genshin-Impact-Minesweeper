package com.example.cmpt276asn3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 *  This class is responsible for displaying the main menu and for navigating to the help menu,
 *  option menu, and game menu
 */

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        setupStartGameButton();
        setupOptionMenuButton();
        setupHelpMenuButton();
    }

    private void setupStartGameButton() {
        Button btn = findViewById(R.id.btnStartGame);
        btn.setOnClickListener((v)->{
            Intent startGameIntent = GameActivity.getIntent(this);
            startActivity(startGameIntent);
        });
    }

    private void setupOptionMenuButton() {
        Button btn = findViewById(R.id.btnOptionMenu);
        btn.setOnClickListener((v)->{
            Intent optionIntent = OptionMenuActivity.getIntent(this);
            startActivity(optionIntent);
        });
    }

    private void setupHelpMenuButton() {
        Button btn = findViewById(R.id.btnHelpMenu);
        btn.setOnClickListener((v)->{
            Intent helpIntent = HelpMenuActivity.getIntent(this);
            startActivity(helpIntent);
        });
    }

    public static Intent getIntent(Context context){
        Intent newIntent = new Intent(context, MainMenuActivity.class);
        return newIntent;
    }
}