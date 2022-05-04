package com.example.cmpt276asn3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 *  This class is responsible for displaying information about the game, the author and the
 *  resources
 */

public class HelpMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);

        setupAboutMeTxt();
        setupHowToPlayTxt();
        setupResourceTxt();
    }

    private void setupAboutMeTxt() {
        TextView editTxt = findViewById(R.id.txtAboutMeTitle);
        editTxt.setText(getString(R.string.about_me_1));

        editTxt = findViewById(R.id.txtAboutAuthor);
        editTxt.setText(getString(R.string.about_me_2));
    }

    private void setupHowToPlayTxt() {
        TextView editTxt = findViewById(R.id.txtHowToPlay);
        editTxt.setText(getString(R.string.how_to_play));
        editTxt.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setupResourceTxt() {
        TextView editTxt = findViewById(R.id.txtResourceInfo);
        editTxt.setText(getString(R.string.resource_info));
    }

    public static Intent getIntent(Context context){
        Intent newIntent = new Intent(context, HelpMenuActivity.class);
        return newIntent;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}