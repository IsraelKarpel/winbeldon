package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Player;

import javax.swing.*;
import java.util.Date;

public class DetailsWindow extends JFrame {
    private JPanel panelMain;
    private JLabel data;
    private final DBHandler db = DBHandler.getInstance();

    private void showDetails(int id) {
        Player player = db.getPlayerById(id);
        int playerId = player.getPlayerId();

        int singles_total_wins = db.getSinglesTotalMatchesWinsByPlayerId(playerId);
        int singles_total_finals_wins = db.getSinglesTotalFinalsMatchesWinsByPlayerId(playerId);
        int doubles_total_wins = db.getDoublesTotalMatchesWinsByPlayerId(playerId);
        int doubles_total_finals_wins = db.getDoublesTotalFinalsMatchesWinsByPlayerId(playerId);
        int best_rank = db.getPlayerBestRankByPlayerId(id);
        Date date_of_best_rank = db.getDateOfBestRankByPlayerId(id);

        data.setText("<html><u>Full name:</u> " + player.getFullName() + "<br><u>Birth Date:</u> " + player.getBirthDate()
        + "<br><u>Hand:</u> " + player.getHand() + "<br><u>Country:</u> " + player.getCountryCode() + "<br><u>Total wins in singles matches:</u> "
        + singles_total_wins + "<br><u>Total times Champion in singles matches:</u> " + singles_total_finals_wins + "<br><u>Total wins on doubles matches:</u>" +
         doubles_total_wins + "<br><u>Total times Champion in doubles matches:</u>" + doubles_total_finals_wins + "<br><u>Best Rank:</u> " +
        best_rank + "<br><u>The Date where he got the best rank:</u> " + date_of_best_rank + "</html>");
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