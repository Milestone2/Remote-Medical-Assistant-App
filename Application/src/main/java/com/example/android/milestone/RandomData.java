package com.example.android.milestone;

import java.util.Random;

/**
 * Created by Owner on 9/5/2017.
 */

public class RandomData {


    public static int respiration() {
        Random r = new Random();
        return r.nextInt(30 - 10) + 10;
    }


    public static int pulse() {
        Random r = new Random();
        return r.nextInt(120 - 40) + 40;
    }
}
