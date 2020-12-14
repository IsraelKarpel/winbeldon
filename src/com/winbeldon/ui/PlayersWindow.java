package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;
import com.winbeldon.model.Player;

import javax.swing.*;
import java.awt.*;
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

    private static List<Player> players = new ArrayList<>();

    private void fillPlayersList(){
        DefaultListModel dlm = new DefaultListModel();
        for (Player player: players){
            dlm.addElement(player.getFullName());
        }
        playersList.setModel(dlm);
    }


    PlayersWindow(Country country, Date date){
        country = country;
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
        players = db.getPlayersByCountryAndDate(country.getCountryCode(),date);
        fillPlayersList();
        playersList.setVisible(true);
    }
}

