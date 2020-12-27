package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Player;

import javax.swing.*;
import java.util.Date;

public class DetailsWindow extends JFrame {
    private JPanel panelMain;
    private JList<String> resultList;
    private JLabel resultLabel;
    private final DBHandler db = DBHandler.getInstance();

    private void showDetails(int id) {
        Player player = db.getPlayerById(id);
        int playerId = player.getPlayerId();
        resultLabel.setText(player.getFullName());

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Full name: " + player.getFullName());
        model.addElement("Birth Date: " + player.getBirthDate());
        model.addElement("Hand: " + player.getHand());
        model.addElement("Country: " + player.getCountryCode());
        model.addElement("Total wins in singles matches: Loading...");
        model.addElement("Total times Champion in singles matches: Loading...");
        model.addElement("Total wins on doubles matches: Loading...");
        model.addElement("Total times Champion in doubles matches: Loading...");
        model.addElement("Best Rank: Loading...");
        model.addElement("The Date where he got the best rank: Loading...");

        resultList.setModel(model);

        new Thread(() -> {
            int singles_total_wins = db.getSinglesTotalMatchesWinsByPlayerId(playerId);
            model.setElementAt("Total wins in singles matches: " + singles_total_wins, 4);
        }).start();

        new Thread(() -> {
            int singles_total_finals_wins = db.getSinglesTotalFinalsMatchesWinsByPlayerId(playerId);
            model.setElementAt("Total times Champion in singles matches: " + singles_total_finals_wins, 5);
        }).start();

        new Thread(() -> {
            int doubles_total_wins = db.getDoublesTotalMatchesWinsByPlayerId(playerId);
            model.setElementAt("Total wins on doubles matches: " + doubles_total_wins, 6);
        }).start();

        new Thread(() -> {
            int doubles_total_finals_wins = db.getDoublesTotalFinalsMatchesWinsByPlayerId(playerId);
            model.setElementAt("Total times Champion in doubles matches: " + doubles_total_finals_wins, 7);
        }).start();

        new Thread(() -> {
            int best_rank = db.getPlayerBestRankByPlayerId(id);
            model.setElementAt("Best Rank: " + best_rank, 8);
        }).start();

        new Thread(() -> {
            Date date_of_best_rank = db.getDateOfBestRankByPlayerId(id);
            model.setElementAt("The Date where he got the best rank: " + date_of_best_rank, 9);
        }).start();
    }


    DetailsWindow(int id) {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        showDetails(id);
    }
}