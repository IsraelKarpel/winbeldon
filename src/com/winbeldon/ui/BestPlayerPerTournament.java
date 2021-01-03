package com.winbeldon.ui;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.RankPlayer;
import com.winbeldon.model.TournamentPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.List;

public class BestPlayerPerTournament extends JFrame {
    private JPanel panelMain;
    private JTable playersTable;
    private JLabel header;
    private final DBHandler db = DBHandler.getInstance();


    BestPlayerPerTournament(){
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        header.setText("Loading...");

        setAllPlayers();
    }

    private void setAllPlayers() {
        new Thread(() -> {
            List<TournamentPlayer> allPlayers = db.getBestPlayersByTournament();

            DefaultTableModel model = new DefaultTableModel(
                    null,
                    new String[]{"Tournament", "year", "Full Name"}
            );

            playersTable.setModel(model);

            for (TournamentPlayer tournamentPlayer : allPlayers) {
                Object[] row = {tournamentPlayer.getTournament(), tournamentPlayer.getYear(),
                        tournamentPlayer.getFullName()};
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

            header.setText("The Winner of Each Grand Slam Each Year");
        }).start();
    }
}
