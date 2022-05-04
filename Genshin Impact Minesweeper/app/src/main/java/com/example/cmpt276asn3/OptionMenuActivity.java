package com.example.cmpt276asn3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 *  This class is responsible for displaying the options for the board size and number of
 *  hiders. It also saves data of the board size and number of hiders to shared preferences
 */

public class OptionMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);

        createNumberOfCharacterRadioButtons();
        createBoardSizeRadioButtons();
    }

    private void createNumberOfCharacterRadioButtons() {
        RadioGroup hiderGroup = findViewById(R.id.radioGroupNumCharacter);
        int[] numHiders = getResources().getIntArray(R.array.num_hiders);

        for (int i = 0; i< numHiders.length; i++) {
            int numHider = numHiders[i];

            RadioButton button = new RadioButton(this);
            button.setText("" + numHider + " " + getString(R.string.character_option));

            button.setOnClickListener((view -> {
                savedNumberOfHiders(numHider);
            }));

            hiderGroup.addView(button);
            if (numHider == getNumberOfHiders(this)){
                button.setChecked(true);
            }
        }
    }

    private void createBoardSizeRadioButtons() {
        RadioGroup group = findViewById(R.id.radioGroupBoardSize);
        int[] numRows = getResources().getIntArray(R.array.num_rows);
        int[] numCols = getResources().getIntArray(R.array.num_cols);

        for (int i = 0; i < numRows.length; i++) {
            int numRow = numRows[i];
            int numCol = numCols[i];

            RadioButton button = new RadioButton(this);
            button.setText("" + numRow + " " + getString(R.string.row_by_column_1) + " "
                    + numCol + " " + getString(R.string.row_by_column_2));

            button.setOnClickListener((view -> {
                savedBoardSize(numRow, numCol);
            }));

            group.addView(button);
            if (numRow == getRowSize(this) &&
                    numCol == getColumnSize(this)){
                button.setChecked(true);
            }
        }
    }

    public static Intent getIntent(Context context){
        Intent newIntent = new Intent(context, OptionMenuActivity.class);
        return newIntent;
    }

    private void savedNumberOfHiders(int numHider) {
        SharedPreferences prefs = this.getSharedPreferences("NumOfHiders", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number Characters Hiding", numHider);
        editor.apply();
    }

    static public int getNumberOfHiders(Context context){
        SharedPreferences prefs = context.getSharedPreferences("NumOfHiders", MODE_PRIVATE);
        int defaultValue = context.getResources().getInteger(R.integer.default_num_hiders);
        return prefs.getInt("Number Characters Hiding", defaultValue);
    }

    private void savedBoardSize(int numRow, int numCol) {
        savedRowSize(numRow);
        savedColumnSize(numCol);
    }

    private void savedRowSize(int numRow) {
        SharedPreferences prefs = this.getSharedPreferences("RowSize", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number of Rows", numRow);
        editor.apply();
    }

    static public int getRowSize(Context context){
        SharedPreferences prefs = context.getSharedPreferences("RowSize", MODE_PRIVATE);
        int defaultValue = context.getResources().getInteger(R.integer.default_row);
        return prefs.getInt("Number of Rows", defaultValue);
    }

    private void savedColumnSize(int numCol) {
        SharedPreferences prefs = this.getSharedPreferences("ColumnSize", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number of Columns", numCol);
        editor.apply();
    }

    static public int getColumnSize(Context context){
        SharedPreferences prefs = context.getSharedPreferences("ColumnSize", MODE_PRIVATE);
        int defaultValue = context.getResources().getInteger(R.integer.default_column);
        return prefs.getInt("Number of Columns", defaultValue);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}