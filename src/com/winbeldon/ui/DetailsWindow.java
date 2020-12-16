package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Player;

import javax.swing.*;

public class DetailsWindow extends JFrame {
    private JPanel panelMain;
    private JLabel data;
    private final DBHandler db = DBHandler.getInstance();

    private void showDetails(int id) {
        Player player = db.getPlayerById(id);
        int playerId = player.getPlayerId();

        int total_wins = db.getTotalMatchesWinsByPlayerId(playerId);
        int total_finals_wins = db.getTotalFinalsMatchesWinsByPlayerId(playerId);
        data.setText(player.toString() + ", " + total_wins + ", " + total_finals_wins);
    }


    DetailsWindow(int id) {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        data.setText("LOADING...");
        showDetails(id);
    }
}