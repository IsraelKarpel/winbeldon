package com.desktopApp;

import com.toedter.calendar.JDateChooser;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class App extends JFrame {
    private JPanel panelMain;
    private JPanel JCalender;
    private JComboBox countriesComboBox;
    private JButton pressMeButton;

    private Calendar cld = Calendar.getInstance();
    private JDateChooser dateChooser = new JDateChooser(cld.getTime());
    private String date;

    private App(){
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(400,250);

        //calender
         dateChooser.setDateFormatString("dd/MM/yyyy");
         JCalender.add(dateChooser);

         GetDate();
    }

    public void GetDate(){
        pressMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdfmt = new SimpleDateFormat("dd/MM/yyyy");
                date = sdfmt.format(dateChooser.getDate());
            }
        });
    }

    public static void main(String[] args) {
        new App().setVisible(true);
    }

}
