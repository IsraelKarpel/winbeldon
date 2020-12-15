package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Details;

import javax.swing.*;

public class DetailsWindow extends JFrame {
    private JPanel panelMain;
    private JLabel deta;
    private DBHandler db;
    private int id;

    private void showDetails(int id){
        Details details = db.getDetails(id);
        deta.setText(details.GetAll());
    }


    DetailsWindow(int id) {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);
        db = new DBHandler();
        db.openConnection();
        //Details det = db.getDetails(id);
        showDetails(id);
    }
}