package com.winbeldon.db;

import com.winbeldon.model.Country;
import com.winbeldon.model.Details;
import com.winbeldon.model.RankPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.winbeldon.Constants.*;

// TODO 14/12/2020: make it singleton!
public class DBHandler {
    Connection conn; // DB connection
    final String HOST = "localhost";
    final String PORT = "3306";
    final String SCHEMA = "winbeldon";
    final String USER = "root";
    final String PASSWORD = "Emuna123!";

    /**
     * Empty constructor
     */
    public DBHandler() {
        this.conn = null;
    }

    /**
     * @return true if the connection was successfully set
     */
    public boolean openConnection() {

        // loading the driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load the MySQL JDBC driver..");
            return false;
        }
        System.out.println("Driver loaded successfully");

        System.out.print("Trying to connect... ");

        // creating the connection.
        //TODO 13/12/2020: Parameters should be taken from config file.
        String host = HOST;
        String port = PORT;
        String schema = SCHEMA;
        String user = USER;
        String password = PASSWORD;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema + "?serverTimezone=UTC", user, password);
        } catch (SQLException e) {
            System.out.println("Unable to connect - " + e.getMessage());
            conn = null;
            return false;
        }
        System.out.println("Connected!");
        return true;
    }

    /**
     * close the connection
     */
    public void closeConnection() {
        // closing the connection
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Unable to close the connection - " + e.getMessage());
        }
    }

    /**
     * Shows executeQuery
     */
    public List<Country> getCountriesList() {
        System.out.print("Getting countries from DB... ");

        String QUERY = "SELECT *" +
                " FROM winbeldon.countries" +
                " ORDER BY country_name";

        List<Country> countries = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                String name = (rs.getString(COUNTRY_NAME));
                String code = (rs.getString(COUNTRY_CODE));
                Country c = new Country(name, code);
                countries.add(c);
            }
            System.out.println("Done!");
            return countries;
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
            return countries;
        } catch (NullPointerException e) {
            System.out.println(("ERROR NullPointerException - " + e.getMessage()));
            return countries;
        }
    }

    public List<Date> getRankingDatesList() {
        System.out.print("Getting ranking dates from DB... ");
        String QUERY = "SELECT DISTINCT rank_date" +
                " FROM winbeldon.rankings" +
                " ORDER BY rank_date DESC";

        List<Date> rankingDates = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Date date = (rs.getDate(RANK_DATE));
                rankingDates.add(date);
            }
            System.out.println("Done!");
            return rankingDates;
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
            return rankingDates;
        } catch (NullPointerException e) {
            System.out.println(("ERROR NullPointerException - " + e.getMessage()));
            return rankingDates;
        }
    }

    public List<RankPlayer> getPlayersByCountryAndDate(String countryCode, Date rankingDate) {
        System.out.println("Getting players by country from DB... ");
        String QUERY = "SELECT players.player_id, first_name, last_name, rankings.rank, points" +
                " FROM winbeldon.players, winbeldon.rankings" +
                " WHERE players.player_id=rankings.player_id" +
                " AND country_code='" + countryCode + "'" +
                " AND rank_date='" + rankingDate + "'" +
                " ORDER BY rankings.rank";

        List<RankPlayer> rankPlayerList = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                RankPlayer player = new RankPlayer(
                        rs.getInt(PLAYER_ID),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME),
                        rs.getInt(RANK),
                        rs.getInt(POINTS));
                rankPlayerList.add(player);
            }
            System.out.println("Done!");
            return rankPlayerList;
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
            return rankPlayerList;
        } catch (NullPointerException e) {
            System.out.println(("ERROR NullPointerException - " + e.getMessage()));
            return rankPlayerList;
        }
    }

    public Details getDetails(int id) {
        Details details = new Details();
        String first_name, last_name, hand, birth_date, country_name;
        String QUERY = "SELECT DISTINCT first_name ,last_name, hand, birth_date, country_code" +
                " FROM winbeldon.players" +
                " WHERE '" + id + "' = player_id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
                    rs.next();
                    details.SetDetails(
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME), rs.getString(HAND),
                    rs.getString(BIRTH_DATE),
                    rs.getString(COUNTRY_CODE));
            System.out.println("Done!");
            return details;
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
            return details;
        } catch (NullPointerException e) {
            System.out.println(("ERROR NullPointerException - " + e.getMessage()));
            return details;
        }


    }
}
