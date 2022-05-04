package com.example.cmpt276asn3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cmpt276asn3.model.GameModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *  This class is responsible for creating the UI for the game
 */

public class GameActivity extends AppCompatActivity {

    private static final int MIN_IMAGE_NUMBER = 1;
    private static final int MAX_IMAGE_NUMBER = 21;
    public static final int FIRST_TIME_SCAN_ON_THIS_BTN = 0;
    public static final int SECOND_TIME_SCAN_ON_THIS_BTN = 1;
    public static final int IMAGE_ALREADY_REVEALED = 1;
    private List<Integer> listOfUsedImages = new ArrayList<>();
    private GameModel newGame;
    private Button buttonsOfHiders[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupGameModel();
        createBoardOfButtons();
        updateTextUI();
    }

    private void setupGameModel() {
        int numRows = OptionMenuActivity.getRowSize(this);
        int numCols = OptionMenuActivity.getColumnSize(this);
        int numOfHiders = OptionMenuActivity.getNumberOfHiders(this);
        newGame = new GameModel(numRows, numCols, numOfHiders);
    }

    private void createBoardOfButtons() {
        int numRows = newGame.getMaxRows();
        int numCols = newGame.getMaxCols();
        buttonsOfHiders = new Button[numRows][numCols];
        TableLayout table = findViewById(R.id.tableOfButtons);

        for (int row = 0; row < numRows; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for (int col = 0; col < numCols; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                int position = FINAL_COL + (FINAL_ROW*numCols);
                Button btn = new Button(this);

                btn.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                btn.setPadding(0, 0, 0, 0);

                btn.setOnClickListener( (v)->{
                    lockButtonSize(numRows, numCols, buttonsOfHiders);

                    if (newGame.isHiderHere(FINAL_ROW, FINAL_COL )){
                        if (newGame.getNumOfScansOnThisBtn(position) ==
                                FIRST_TIME_SCAN_ON_THIS_BTN){
                            changeButtonIntoImage(btn);
                            newGame.updateNumOfScanOnThisBtn(position);
                            newGame.updateNumOfCharactersFound();
                            decreaseNearbyHiderCounters(FINAL_ROW, FINAL_COL);
                            updateTextUI();
                        }

                        else if (newGame.getNumOfScansOnThisBtn(position) ==
                                SECOND_TIME_SCAN_ON_THIS_BTN){
                            newGame.updateNumOfScanOnThisBtn(position);
                            newGame.updateTotalScanCounter();
                            scanForNearbyHiders(FINAL_ROW, FINAL_COL);
                            updateTextUI();
                        }
                    }

                    else if (newGame.getNumOfScansOnThisBtn(position) ==
                            FIRST_TIME_SCAN_ON_THIS_BTN){
                        newGame.updateNumOfScanOnThisBtn(position);
                        newGame.updateTotalScanCounter();
                        scanForNearbyHiders(FINAL_ROW, FINAL_COL);
                        updateTextUI();
                    }
                });

                tableRow.addView(btn);
                newGame.addScanToThisBtn();
                buttonsOfHiders[row][col] = btn;
            }
        }
    }

    private void updateTextUI() {
        TextView txtCharacterFound = findViewById(R.id.txtCharactersFound);
        TextView txtScanUsed = findViewById(R.id.txtScanUsed);

        txtCharacterFound.setText(getString(R.string.character_found_1) + " "
                + newGame.getNumOfCharactersFound() + " " + getString(R.string.character_found_2) + " "
                + newGame.getMaxHiders() + " " + getString(R.string.character_found_3));
        txtScanUsed.setText(getString(R.string.scan_used) + " " + newGame.getTotalScanCounter());

        if (newGame.getNumOfCharactersFound() == OptionMenuActivity.getNumberOfHiders(this)){
            showWinningAlertDialog();
        }
    }

    private void decreaseNearbyHiderCounters(int final_row, int final_col) {
        for (int currColumn = 0; currColumn < newGame.getMaxCols(); currColumn++) {
            String btnTxt = buttonsOfHiders[final_row][currColumn].getText().toString();
            if (btnTxt != "" && btnTxt != "0" && currColumn != final_col){
                int btnHiderCounter = Integer.parseInt(btnTxt) - 1;
                buttonsOfHiders[final_row][currColumn].setText("" + btnHiderCounter);
            }
        }

        for (int currRow = 0; currRow < newGame.getMaxRows(); currRow++){
            String btnTxt = buttonsOfHiders[currRow][final_col].getText().toString();
            if (btnTxt != "" && btnTxt != "0" && currRow != final_row){
                int btnHiderCounter = Integer.parseInt(btnTxt) - 1;
                buttonsOfHiders[currRow][final_col].setText("" + btnHiderCounter);
            }
        }
    }

    private void scanForNearbyHiders(int final_row, int final_col) {
        int nearbyCharacters = 0;

        // Checking vertically for characters
        for (int currColumn = 0; currColumn < newGame.getMaxCols(); currColumn++){
            int position = currColumn + (final_row*newGame.getMaxCols());

            if (newGame.isHiderHere(final_row, currColumn) &&
                    currColumn != final_col &&
                    newGame.getNumOfScansOnThisBtn(position) < IMAGE_ALREADY_REVEALED
            ){
                nearbyCharacters++;
            }
        }

        // Checking horizontally for characters
        for (int currRow = 0; currRow < newGame.getMaxRows(); currRow++){
            int position = final_col + (currRow*newGame.getMaxCols());

            if (newGame.isHiderHere(currRow, final_col) &&
                    currRow != final_row &&
                    newGame.getNumOfScansOnThisBtn(position) < IMAGE_ALREADY_REVEALED
            ){
                nearbyCharacters++;
            }
        }

        updateBtnUI(nearbyCharacters, final_col, final_row);
    }

    private void updateBtnUI(int nearbyCharacters, int final_col, int final_row) {
        Button currBtn = buttonsOfHiders[final_row][final_col];
        currBtn.setText("" + nearbyCharacters);
    }

    private void showWinningAlertDialog() {
        FragmentManager winningAlertDialog = getSupportFragmentManager();
        WinningAlertDialogFragment winningDialog =
                new WinningAlertDialogFragment(newGame.getMaxHiders(), newGame.getTotalScanCounter());
        winningDialog.show(winningAlertDialog, "WinningDialog");
    }

    private void changeButtonIntoImage(Button btn) {
        String partOfImageID = "genshin_character_image_";
        int imageNumber = getRandomImageNumber();
        String imageFileName = partOfImageID + imageNumber;
        int imageID = getResources().getIdentifier(imageFileName,
                "drawable",
                GameActivity.this.getPackageName()
        );

        int newWidth = btn.getWidth();
        int newHeight = btn.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), imageID);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,newWidth, newHeight, true);
        Resources resource = getResources();
        btn.setBackground(new BitmapDrawable(resource, scaledBitmap));
    }

    private int getRandomImageNumber() {
        Random newRandom = new Random();
        IntStream randomNumberAsIntStream = newRandom.ints(MIN_IMAGE_NUMBER, MAX_IMAGE_NUMBER);
        int randomNumber = randomNumberAsIntStream.findFirst().getAsInt();

        if (listOfUsedImages.size() == 0){
            listOfUsedImages.add(randomNumber);
            return randomNumber;
        }

        while (!isImageNumberUnique(randomNumber)){
            randomNumberAsIntStream = newRandom.ints(MIN_IMAGE_NUMBER, MAX_IMAGE_NUMBER);
            randomNumber = randomNumberAsIntStream.findFirst().getAsInt();
        }

        listOfUsedImages.add(randomNumber);
        return randomNumber;
    }

    private boolean isImageNumberUnique(int randomNumber) {
        for (int i = 0; i < listOfUsedImages.size(); i++){
            if (listOfUsedImages.get(i) == randomNumber){
                return false;
            }
        }
        return true;
    }

    private void lockButtonSize(int numRows, int numCols, Button[][] buttons) {
        for (int row = 0; row < numRows; row++){
            for (int col = 0; col < numCols; col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);
                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    public static Intent getIntent(Context context){
        Intent newIntent = new Intent(context, GameActivity.class);
        return newIntent;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}