package com.winbeldon.ui;


import com.toedter.calendar.JDateChooser;
import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainWindow extends JFrame {
    private JPanel panelMain;
    private JPanel JCalender;
    private JComboBox countriesComboBox;
    private JButton pressMeButton;

    private Calendar cld = Calendar.getInstance();
    private JDateChooser dateChooser = new JDateChooser(cld.getTime());
    private String date;
    private static List<Country> countries = new ArrayList<>();

    private MainWindow() {
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center

        //calender
        dateChooser.setDateFormatString("dd/MM/yyyy");
        JCalender.add(dateChooser);

        getDate();
        fillCountries();
    }

    private void fillCountries() {
        countriesComboBox.setVisible(true);

        for (Country c : countries) {
            countriesComboBox.addItem(c.getCountryName());
        }
    }

    public void getDate() {
        pressMeButton.addActionListener(event -> {
            SimpleDateFormat sdfmt = new SimpleDateFormat("dd/MM/yyyy");
            date = sdfmt.format(dateChooser.getDate());
        });
    }

    public static void main(String[] args) {
        DBHandler db = new DBHandler();
        db.openConnection();
        countries = db.getCountriesList();

        new MainWindow().setVisible(true);
    }

}
