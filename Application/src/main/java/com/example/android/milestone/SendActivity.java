package com.example.android.milestone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendActivity extends AppCompatActivity {

    double latitude;
    double longitude;
    GPSTracker gps;// GPSTracker class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendEmail();
    }

    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pbobc10@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Medi-Care");
        intent.putExtra(Intent.EXTRA_TEXT, "Test from Medi-care");
        if (null != intent.resolveActivity(getPackageManager())) {
            startActivity(Intent.createChooser(intent, ""));
        }
    }

    public void sendSMS(String phone, int pulse, int resp) {

        String sms = "Please help\n" + "My Body Diagnostic:\n" + "Heart beat: " + pulse + " BMP\n" +
                "Breathing: " + resp + " BPM\n" + "Temperature: 89.F\n" + "My Location: " +
                "https://maps.google.com/maps?q=" + MenuActivity.gps.currentLocation().getLatitude() + "," + MenuActivity.gps.currentLocation().getLongitude() + "&z=18\n";


        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, sms, null, null);
            Log.d("TEST_SMS", "PASS : " + phone);
        } catch (Exception e) {
            Log.d("TEST_SMS", e.toString());
            Log.d("TEST_SMS", "FAILD");
        }

    }



   /* public void  test(){
        Toast.makeText(getApplicationContext(),"yes yes",Toast.LENGTH_LONG).show();
    }*/
}
