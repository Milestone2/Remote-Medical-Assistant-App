package com.example.android.milestone;

import java.util.Random;

/**
 * Created by Owner on 9/5/2017.
 */

public class RandomData {
    private static final int minPulse =20;
    private static final int maxPulse = 150;
    private static final int minRespiration = 5;
    private static final int maxRespiration = 40;

    public static int respiration() {
        Random r = new Random();
        return r.nextInt(maxRespiration - minRespiration) + minRespiration;
    }


    public static int pulse() {
        Random r = new Random();
        return r.nextInt(maxPulse - minPulse) + minPulse;
    }
}
