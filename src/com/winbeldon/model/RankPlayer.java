package com.winbeldon.model;

public class RankPlayer {
    private final int playerId;
    private final String firstName;
    private final String lastName;
    private final int rank;
    private final int points;

    public RankPlayer(int id, String firstName, String lastName, int rank, int points) {
        playerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
        this.points = points;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getRank() {
        return rank;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return getFullName() + ", " + getRank() + ", " + getPoints();
    }
}
