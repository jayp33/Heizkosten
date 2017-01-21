package com.japhdroid.heizkosten;

/**
 * Created by User on 18.11.2015.
 */
public class CostCalculator {

    private int[] tooFast            = { 0,  10, 15, 20, 25,  30,  40,  50,  60,  70 };
    private int[] fee_city           = { 15, 25, 35, 80, 100, 160, 200, 280, 480, 680 };
    private int[] fee_highway        = { 10, 20, 30, 70, 80,  120, 160, 240, 440, 600 };
    private int[] points_city        = { 0,  0,  0,  1,  1,   2,   2,   2,   2,   2 };
    private int[] points_highway     = { 0,  0,  0,  1,  1,   1,   2,   2,   2,   2 };
    private int[] revocation_city    = { 0,  0,  0,  0,  -1,  1,   1,   2,   3,   3 };
    private int[] revocation_highway = { 0,  0,  0,  0,  -1,  -1,  1,   1,   2,   3 };

    private boolean inCity = true;

    public Speeding speeding(Status status) {
        inCity = status.inCity;
        int tooFastIndex = getTooFastIndex(status.actualSpeed - status.allowedSpeed);
        if (tooFastIndex < 0)
            return new Speeding(0, 0, 0);
        if (inCity)
            return new Speeding(fee_city[tooFastIndex], points_city[tooFastIndex], revocation_city[tooFastIndex]);
        else
            return new Speeding(fee_highway[tooFastIndex], points_highway[tooFastIndex], revocation_highway[tooFastIndex]);
    }

    private int getTooFastIndex(int tooFastSpeed) {
        for (int i = tooFast.length - 1; i >= 0; i--)
            if (tooFastSpeed > tooFast[i])
                return i;
        return -1;
    }
}
