package com.winbeldon;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;
import com.winbeldon.model.RankPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.List;

public class BestPlayerEachCountry extends JFrame {
    private JPanel panelMain;
    private JTable playersTable;
    private final DBHandler db = DBHandler.getInstance();

    public BestPlayerEachCountry(){
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        setAllPlayers();
    }

    private void setAllPlayers(){
        List<RankPlayer> allPlayers = db.getAllBestPlayers();
        //List<Country> countries = db.getCountriesList();

        DefaultTableModel model = new DefaultTableModel(
                null,
                new String[]{"Name", "Country", "Points"}
        );

        playersTable.setModel(model);

        for (RankPlayer rankPlayer : allPlayers) {
            Object[] row = {rankPlayer.getFullName(), db.getCountryNameByPlayerID(rankPlayer.getPlayerId()),
                    rankPlayer.getPoints()};
            model.addRow(row);
        }

        TableColumnModel columns = playersTable.getColumnModel();
        columns.getColumn(0).setMinWidth(200);
        columns.getColumn(1).setMinWidth(50);
        columns.getColumn(2).setMinWidth(50);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);

    }

}
