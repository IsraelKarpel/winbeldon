package com.winbeldon.model;

import java.util.Date;

public class Player {
    private final int playerId;
    private final String firstName;
    private final String lastName;
    private final String hand;
    private final Date birthDate;
    private final String countryCode;

    public Player(int id, String firstName, String lastName, String hand, Date birthDate, String countryCode) {
        this.playerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hand = hand;
        this.birthDate = birthDate;
        this.countryCode = countryCode;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }


}
