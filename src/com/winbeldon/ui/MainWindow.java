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
    private JLabel resultLabel;

    private Calendar cld = Calendar.getInstance();
    private JDateChooser dateChooser = new JDateChooser(cld.getTime());
    private String dateSelected, countrySelected;
    private static List<Country> countries = new ArrayList<>();

    private MainWindow() {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center

        resultLabel.setVisible(false);

        //calender
        dateChooser.setDateFormatString("dd/MM/yyyy");
        JCalender.add(dateChooser);

        fillCountries();
        setButtonListener();
    }

    private void fillCountries() {
        countriesComboBox.setVisible(true);

        for (Country c : countries) {
            countriesComboBox.addItem(c.getCountryName());
        }
    }

    private void setButtonListener() {
        pressMeButton.addActionListener(event -> {

            // fetch date
            SimpleDateFormat sdfmt = new SimpleDateFormat("dd/MM/yyyy");
            dateSelected = sdfmt.format(dateChooser.getDate());

            // fetch country
            countrySelected = (String) countriesComboBox.getSelectedItem();

            // show result
            resultLabel.setText(dateSelected + ", " + countrySelected);
            resultLabel.setVisible(true);
        });
    }

    public static void main(String[] args) {
        DBHandler db = new DBHandler();
        db.openConnection();
        countries = db.getCountriesList();

        new MainWindow().setVisible(true);
    }

}
