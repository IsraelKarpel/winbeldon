package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;
import com.winbeldon.model.RankPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayersWindow extends JFrame {
    private JPanel panelMain;
    private JTable resultTable;
    private JLabel resultLabel;
    DBHandler db = DBHandler.getInstance();

    private static List<RankPlayer> rankPlayers = new ArrayList<>();

    private void createTable() {
        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{"Name", "Rank", "Points"}
        );

        resultTable.setModel(model);

        for (RankPlayer rankPlayer : rankPlayers) {
            Object[] row = {rankPlayer.getFullName(), rankPlayer.getRank(), rankPlayer.getPoints()};
            model.addRow(row);
        }

        TableColumnModel columns = resultTable.getColumnModel();
        columns.getColumn(0).setMinWidth(200);
        columns.getColumn(1).setMinWidth(50);
        columns.getColumn(2).setMinWidth(50);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);

        resultTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int id = rankPlayers.get(row).getPlayerId();
                    new DetailsWindow(id);
                }
            }
        });
    }

    PlayersWindow(Country country, Date date) {
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        rankPlayers = db.getPlayersByCountryAndDate(country.getCountryCode(), date);
        setTableTitle(country.getCountryName());
        createTable();
    }

    private void setTableTitle(String countryName) {
        resultLabel.setText(countryName + " Result");
    }
}

