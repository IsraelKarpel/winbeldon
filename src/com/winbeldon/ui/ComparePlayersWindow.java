package com.winbeldon.ui;

import com.mysql.cj.conf.ConnectionUrlParser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import com.winbeldon.db.DBHandler;
import com.winbeldon.model.Country;
import com.winbeldon.model.Player;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ComparePlayersWindow extends JFrame {
    DBHandler db = DBHandler.getInstance();
    private JPanel panelMain;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<ConnectionUrlParser.Pair<Date, Double>> player1List;
    private List<ConnectionUrlParser.Pair<Date, Double>> player2List;
    private List<ConnectionUrlParser.Pair<Date, Double>> player3List;
    private List<ConnectionUrlParser.Pair<Date, Double>> player4List;

    ComparePlayersWindow(Player playerOne, Player playerTwo, Player playerThree, Player playerFour) {
        player1 = playerOne;
        player2 = playerTwo;
        player3 = playerThree;
        player4 = playerFour;
        setContentPane(panelMain);
        setTitle("Winbeldon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setLocation(getWidth() / 2, getHeight() / 2); // position window on center
        setVisible(true);

        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        this.add(chartPanel);
        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private TimeSeries buildTimeSeries(Player player, List<ConnectionUrlParser.Pair<Date, Double>> list){
        TimeSeries series = new TimeSeries(player.getFullName());
        for (ConnectionUrlParser.Pair p : list) {
            series.add(new Day((Date)p.left), (Double) p.right);
        }
        return series;
    }

    private XYDataset createDataset() {
        player1List = db.getPlayerDatePointsList(player1.getPlayerId());
        player2List = db.getPlayerDatePointsList(player2.getPlayerId());
        player3List = db.getPlayerDatePointsList(player3.getPlayerId());
        player4List = db.getPlayerDatePointsList(player4.getPlayerId());

        TimeSeries series1 = buildTimeSeries(player1, player1List);
        TimeSeries series2 = buildTimeSeries(player2, player2List);
        TimeSeries series3 = buildTimeSeries(player3, player3List);
        TimeSeries series4 = buildTimeSeries(player4, player4List);



        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Compare Players",
                "Year",
                "Points",
                dataset,
                true,
                true,
                false
        );


        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesPaint(3, Color.ORANGE);
        renderer.setSeriesStroke(3, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Compare Players",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }
}
