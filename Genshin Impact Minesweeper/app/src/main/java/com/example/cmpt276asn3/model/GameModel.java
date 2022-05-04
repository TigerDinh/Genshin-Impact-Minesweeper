package com.example.cmpt276asn3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *  This class is responsible for randomly placing the location of the characters and for keeping
 *  track of hidden and found characters.
 */
public class GameModel {

    public static final int MIN = 1;
    private int upperBound;
    private int maxRows;
    private int maxCols;
    private int maxHiders;
    private int totalScanCounter = 0;
    private int numOfCharactersFound = 0;
    private int hiderLocation[];
    private List<Integer> numOfScansOnThisBtn = new ArrayList<>();

    public GameModel(int numRows, int numCols, int numHiders) {
        this.maxRows = numRows;
        this.maxCols = numCols;
        this.maxHiders = numHiders;
        this.totalScanCounter = 0;
        this.numOfCharactersFound = 0;
        hiderLocation = new int[numHiders];
        upperBound = this.maxCols * this.maxRows + 1;

        setupRandomHidingLocation();
    }

    private void setupRandomHidingLocation() {
        for (int i = 0; i < hiderLocation.length; i++){
            Random newRandom = new Random();
            IntStream randomNumberAsIntStream = newRandom.ints(MIN, upperBound);
            int randomNumber = randomNumberAsIntStream.findFirst().getAsInt();

            while (i > 0 && !numIsUnique(randomNumber, i)){
                randomNumberAsIntStream = newRandom.ints(MIN, upperBound);
                randomNumber = randomNumberAsIntStream.findFirst().getAsInt();
            }

            hiderLocation[i] = randomNumber;
        }
    }

    private boolean numIsUnique(int randomNumber, int index) {
        for (int j = index; j >= 0; j--){
            if (hiderLocation[j] == randomNumber){
                return false;
            }
        }
        return true;
    }

    public boolean isHiderHere(int positionRow, int positionColumn){
        int position = positionColumn + 1 + (positionRow * maxCols);

        for (int i = 0; i < hiderLocation.length; i++){
            if (hiderLocation[i] == position){
                return true;
            }
        }
        return false;
    }

    public void addScanToThisBtn(){
        numOfScansOnThisBtn.add(0);
    }

    public int getNumOfScansOnThisBtn(int position){
        return numOfScansOnThisBtn.get(position);
    }

    public void updateNumOfScanOnThisBtn(int position) {
        int scanNumber = numOfScansOnThisBtn.get(position);
        numOfScansOnThisBtn.set(position, (scanNumber + 1));
    }

    public int getTotalScanCounter() {
        return totalScanCounter;
    }

    public int getNumOfCharactersFound() {
        return numOfCharactersFound;
    }

    public void updateTotalScanCounter(){
        totalScanCounter++;
    }

    public void updateNumOfCharactersFound(){
        numOfCharactersFound++;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public int getMaxCols() {
        return maxCols;
    }

    public int getMaxHiders() {
        return maxHiders;
    }
}
