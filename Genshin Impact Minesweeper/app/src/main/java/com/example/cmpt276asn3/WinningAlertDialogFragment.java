package com.example.cmpt276asn3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

/**
 *  This class is responsible for displaying a dialog when the player has found all the characters
 *  and will lead the user to the main menu
 */

public class WinningAlertDialogFragment extends AppCompatDialogFragment {

    private int numOfCharacters;
    private int numOfScans;

    public WinningAlertDialogFragment(int numOfHiders, int userScans) {
        this.numOfCharacters = numOfHiders;
        this.numOfScans = userScans;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.winning_alert_dialog, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(dialogView);

        displayWinningText(dialogView);
        setupReturnMainMenuButton(dialogView);

        return alertDialog.create();
    }

    private void displayWinningText(View dialogView) {
        TextView editText = dialogView.findViewById(R.id.txtWinningMsg);
        editText.setText(getString(R.string.winning_dialog_1) + " " + numOfCharacters + " "
                + getString(R.string.winning_dialog_2) + " " + numOfScans);
    }

    private void setupReturnMainMenuButton(View dialogView) {
        Button returnBtn = dialogView.findViewById(R.id.btnReturnMainMenu);
        returnBtn.setOnClickListener((v)->{
            getActivity().finish();
        });
    }
}
