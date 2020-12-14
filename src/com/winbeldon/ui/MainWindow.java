package com.winbeldon.ui;


import com.toedter.calendar.JDateChooser;
import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainWindow extends JFrame {
    private JPanel panelMain;
    private JComboBox countriesComboBox;
    private JButton pressMeButton;
    private JLabel resultLabel;
    private JComboBox datesComboBox;

    private static List<Country> countries = new ArrayList<>();
    private static List<Date> rankingDates = new ArrayList<>();

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

    }

    private void fillCountries() {
        countriesComboBox.setVisible(true);

        for (Country c : countries) {
            countriesComboBox.addItem(c.getCountryName());
        }
    }

    private void fillRankingDates(){
        countriesComboBox.setVisible(true);
        for (Date d : rankingDates){
            datesComboBox.addItem(d.toString());
        }
    }


    public static void main(String[] args) {
        DBHandler db = new DBHandler();
        db.openConnection();
        countries = db.getCountriesList();
        rankingDates = db.getRankingDatesList();

        new MainWindow().setVisible(true);
    }

}
