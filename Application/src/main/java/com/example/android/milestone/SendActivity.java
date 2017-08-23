package com.example.android.milestone;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
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

    public void sendSMS(String phone) {

        String sms = "Please help," +  location() + "My Body Diagnostic -\n" + "Heart beat:89BMP\n" + "Oxygen:89% \n" + "Breathing:89 \n" + "Tempeture:89.F \n";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, sms, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private String location() {
        // TODO Auto-generated method stub
        // create class object
        gps = new GPSTracker(getApplicationContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();

        }
        return "my Location is Lat:" + latitude + "\nLong:" + longitude;
    }

    public void  test(){
        Toast.makeText(getApplicationContext(),"yes yes",Toast.LENGTH_LONG).show();
    }
}
