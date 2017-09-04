package com.veryfit.multi.util;

import android.text.format.Time;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Logcat {
    public static final String TAG = "Util/Logcat";

    public static void writeLogcat(String filename) throws IOException {
        InputStreamReader input = new InputStreamReader(Runtime.getRuntime().exec(new String[]{"logcat", "-v", Time.getCurrentTimezone(), "-d"}).getInputStream());
        OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(filename));
        BufferedReader br = new BufferedReader(input);
        BufferedWriter bw = new BufferedWriter(output);
        while (true) {
            String line = br.readLine();
            if (line == null) {
                bw.close();
                output.close();
                br.close();
                input.close();
                return;
            }
            bw.write(line);
            bw.newLine();
        }
    }

    public static String getLogcat() throws IOException {
        InputStreamReader input = new InputStreamReader(Runtime.getRuntime().exec(new String[]{"logcat", "-v", Time.getCurrentTimezone(), "-d", "-t", "500"}).getInputStream());
        BufferedReader br = new BufferedReader(input);
        StringBuilder log = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                br.close();
                input.close();
                return log.toString();
            }
            log.append(new StringBuilder(String.valueOf(line)).append("\n").toString());
        }
    }
}
