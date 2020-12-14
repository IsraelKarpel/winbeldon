package com.winbeldon.db;

import com.winbeldon.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandler {
    Connection conn; // DB connection
    final String HOST = "localhost";
    final String PORT = "3306";
    final String SCHEMA = "winbeldon";
    final String USER = "root";
    final String PASSWORD = "12qw34er";

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

        String QUERY = "SELECT * FROM winbeldon.countries ORDER BY country_name";
        List<Country> countries = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                String name = (rs.getString("country_name"));
                String code = (rs.getString("country_code"));
                Country c = new Country(name, code);
                countries.add(c);
            }
            System.out.println("Done!");
            return countries;
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
            return countries;
        } catch (NullPointerException e){
            System.out.println(("ERROR NullPointerException - " + e.getMessage()));
            return countries;
        }
    }

    public List<Date> getRankingDatesList(){
        System.out.print("Getting ranking dates from DB... ");
        String QUERY = "SELECT distinct rank_date FROM winbeldon.rankings";
        List<Date> rankingDates = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                Date date =(rs.getDate("rank_date"));
                rankingDates.add(date);
            }
            System.out.println("Done!");
            return rankingDates;
        }  catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
            return rankingDates;
        } catch (NullPointerException e){
            System.out.println(("ERROR NullPointerException - " + e.getMessage()));
            return rankingDates;
        }
    }

}
