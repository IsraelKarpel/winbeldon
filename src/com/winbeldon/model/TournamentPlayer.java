package com.winbeldon.model;

public class TournamentPlayer {
    private final String firstName;
    private final String lastName;
    private final Long year;
    private final String  tournament;

    public TournamentPlayer(String firstName, String lastName, Long year, String tournament) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.tournament = tournament;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Long getYear() {
        return year;
    }

    public String getTournament() {
        return tournament;
    }
}
