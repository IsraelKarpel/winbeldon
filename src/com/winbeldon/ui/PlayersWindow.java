package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;
import com.winbeldon.model.RankPlayer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayersWindow extends JFrame {
    private JList playersList;
    private JPanel panelMain;
    private JTextField countryTextField;
    private Country country;
    private Date rankingDate;
    DBHandler db;

    private static List<RankPlayer> rankPlayers = new ArrayList<>();

    private void fillPlayersList() {
        DefaultListModel dlm = new DefaultListModel();
        for (RankPlayer rankPlayer : rankPlayers) {
            dlm.addElement(rankPlayer.toString());
        }
        playersList.setModel(dlm);
    }


    PlayersWindow(Country country, Date date) {
        rankingDate = date;
        countryTextField.setText(country.getCountryName());

        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        db = new DBHandler();
        db.openConnection();
        rankPlayers = db.getPlayersByCountryAndDate(country.getCountryCode(), date);
        fillPlayersList();
        playersList.setVisible(true);
    }
}

