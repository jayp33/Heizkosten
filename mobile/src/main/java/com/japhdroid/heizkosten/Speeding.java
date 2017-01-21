package com.japhdroid.heizkosten;

/**
 * Created by User on 18.11.2015.
 */
public class Speeding {

    private int fee = 0;
    private int points = 0;
    private int revocation = 0;

    public Speeding(int fee, int points, int revocation) {
        this.fee = fee;
        this.points = points;
        this.revocation = revocation;
    }

    public int getFee() {
        return fee;
    }

    public String getFeeStr() {
        return String.valueOf(fee);
    }

    public int getPoints() {
        return points;
    }

    public String getPointsStr() {
        return String.valueOf(points);
    }

    public int getRevocation() {
        return revocation;
    }

    public String getRevocationStr() {
        if (revocation < 0)
                return "(" + Math.abs(revocation) + ")";
        return String.valueOf(revocation);
    }
}
