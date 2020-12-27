package com.winbeldon.ui;


import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainWindow extends JFrame {
    private JComboBox countriesComboBox;
    private JButton pressMeButton;
    private JComboBox datesComboBox;
    private static List<Country> countries = new ArrayList<>();
    private static List<Date> rankingDates = new ArrayList<>();
    private JPanel panelMain;
    private JLabel resultLabel;

    private MainWindow() {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        resultLabel.setVisible(false);
        fillCountries();
        fillRankingDates();

        pressMeButton.addActionListener(e -> {
            Country selectedCountry = getSelectedCountry();
            Date selectedRankingDate = getSelectedDate();
            new PlayersWindow(selectedCountry, selectedRankingDate);
        });
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

    public static void main(String[] args) {
        DBHandler db = DBHandler.getInstance();
        boolean isOpen = db.openConnection();
        if (isOpen) {
            countries = db.getCountriesList();
            rankingDates = db.getRankingDatesList();
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


}
