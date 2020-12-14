package com.winbeldon.model;

import java.util.Date;

public class Player {
    private final int playerID;
    private final String firstName;
    private final String lastName;
    private final String hand;
    private final Date birthDate;
    private final String countryCode;

    public Player(int ID, String first_Name, String last_Name, String hand_, Date birth_Date, String country_Code) {
        playerID = ID;
        firstName = first_Name;
        lastName = last_Name;
        hand = hand_;
        birthDate = birth_Date;
        countryCode = country_Code;
    }

    public String getFullName(){
        return firstName + lastName;
    }
}
