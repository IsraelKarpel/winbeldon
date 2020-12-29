package com.winbeldon.ui;


import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;
import com.winbeldon.model.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainWindow extends JFrame {
    private JComboBox countriesComboBox;
    private JButton searchButton;
    private JComboBox datesComboBox;
    private static List<Country> countries = new ArrayList<>();
    private static List<Date> rankingDates = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();
    private JPanel panelMain;
    private JComboBox player1comboBox;
    private JComboBox player2comboBox;
    private JButton compareButton;
    private JComboBox player3comboBox;
    private JComboBox player4comboBox;
    private JButton bestEachCountryButton;

    private MainWindow() {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        fillCountries();
        fillRankingDates();
        fillPlayers();

        searchButton.addActionListener(e -> {
            Country selectedCountry = getSelectedCountry();
            Date selectedRankingDate = getSelectedDate();
            new PlayersWindow(selectedCountry, selectedRankingDate);
        });
        compareButton.addActionListener(e -> {
            Player player1 = getSelectedPlayer(1);
            Player player2 = getSelectedPlayer(2);
            Player player3 = getSelectedPlayer(3);
            Player player4 = getSelectedPlayer(4);
            new ComparePlayersWindow(player1, player2, player3, player4);
        });
        bestEachCountryButton.addActionListener(e -> new BestPlayerEachCountry());
    }

    private Country getSelectedCountry() {
        try {
            return countries.get(countriesComboBox.getSelectedIndex());
        } catch (NullPointerException e) {
            System.out.println("ERROR - You have not chosen any country - " + e.getMessage());
            return null;
        }
    }

    private Date getSelectedDate() {
        try {
            return rankingDates.get(datesComboBox.getSelectedIndex());
        } catch (NullPointerException e) {
            System.out.println("ERROR - you have not chosen any date - " + e.getMessage());
            return null;
        }
    }
    private Player getSelectedPlayer(int num){
        try{
            if (num == 1){
                return players.get(player1comboBox.getSelectedIndex());
            } else if (num == 2){
                return players.get(player2comboBox.getSelectedIndex());
            } else if (num == 3){
                return players.get(player3comboBox.getSelectedIndex());
            } else if (num == 4){
                return players.get(player4comboBox.getSelectedIndex());
            }
        }catch (NullPointerException e){
            System.out.println("ERROR - You have not chosen any Player - " + e.getMessage());
            return null;
        }
        return null;
    }

    public static void main(String[] args) {
        DBHandler db = DBHandler.getInstance();
        boolean isOpen = db.openConnection();
        if (isOpen) {
            countries = db.getCountriesList();
            rankingDates = db.getRankingDatesList();
            players = db.getAllPlayersList();
            new MainWindow().setVisible(true);
        }
    }

    private void fillCountries() {
        countriesComboBox.setVisible(true);
        for (Country c : countries) {
            countriesComboBox.addItem(c.getCountryName());
        }
        // set default country to israel
        countriesComboBox.setSelectedItem("Israel");
    }

    private void fillRankingDates() {
        countriesComboBox.setVisible(true);
        for (Date d : rankingDates) {
            datesComboBox.addItem(d.toString());
        }
    }

    private void fillPlayers(){
        player1comboBox.setVisible(true);
        player2comboBox.setVisible(true);
        player3comboBox.setVisible(true);
        player4comboBox.setVisible(true);
        for (Player p: players){
            player1comboBox.addItem(p.getFullName());
            player2comboBox.addItem(p.getFullName());
            player3comboBox.addItem(p.getFullName());
            player4comboBox.addItem(p.getFullName());
        }
    }


}
