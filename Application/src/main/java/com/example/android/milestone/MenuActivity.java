package com.example.android.milestone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.adapters.ContactAdapter2;
import com.example.android.milestone.bluetoothGattBLE.DeviceScanActivity;
import com.example.android.milestone.fragments.ContactFragment2;
import com.example.android.milestone.fragments.HistoryFragment;
import com.example.android.milestone.fragments.HomeFragment;
import com.example.android.milestone.fragments.LocalisationFragment;
import com.example.android.milestone.fragments.ProfileFragment;
import com.example.android.milestone.fragments.SendFragment;
import com.example.android.milestone.fragments.SettingFragment;
import com.example.android.milestone.models.Contact;

import java.util.ArrayList;
import java.util.List;

import weborb.client.Fault;

public class MenuActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    public FloatingActionButton fab;
    FrameLayout fragmentContainer;
    BackendlessUser user;
    TextView tvL_user;
    TextView tvL_email;

    public  static ArrayList<Contact> c;//Listes des contacts a afficher
    DataQueryBuilder c_query;

    public DataQueryBuilder contactQuery;

    double latitude;
    double longitude;
    public static GPSTracker gps;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        gps = new GPSTracker(this);


        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");
        user = (BackendlessUser) getIntent().getSerializableExtra("userInfo");

        c = new ArrayList<>();
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        //nvDrawer = NavigationView.inflate(getBaseContext(), R.layout.nav_header, savedInstanceState );
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        View hView = nvDrawer.getHeaderView(0);
        tvL_user = (TextView)  hView.findViewById(R.id.tvL_user);
        tvL_email = (TextView) hView.findViewById(R.id.tvL_email);
        tvL_user.setText(user.getProperty("Nom").toString());
        tvL_email.setText(user.getEmail());

        loadContact();

        fragmentContainer = (FrameLayout) findViewById(R.id.flContent);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //sendEmail();
               // sendSMS("37396810");
              /*  for(int i = 0; i < c.size(); i++){
                    sendSMS(String.valueOf(c.get(i).getTel()));
                }*/
            }
        });

        if(savedInstanceState == null){
            HomeFragment homeFragment = HomeFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, homeFragment);
            ft.commit();
        }


    }

    public void loadContact(){
        String query = "id = " + "'" + user.getUserId() + "'";
        c_query = DataQueryBuilder.create();
        c_query.setWhereClause(query);
        Backendless.Persistence.of(Contact.class).find( c_query,new AsyncCallback<List<Contact>>() {
            @Override
            public void handleResponse(List<Contact> response) {
                c.addAll(response);
                Log.d("DEBUG", response.toString());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d("DEBUG", fault.getMessage());
            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_history_fragment:
                fragmentClass = HistoryFragment.class;
                break;
            case R.id.nav_profile_fragment:
                fragmentClass = ProfileFragment.newInstance(user).getClass();
                break;
            case R.id.nav_contact_fragment:
                fragmentClass =ContactFragment2.class;
                break;
            case R.id.nav_localisation:
                fragmentClass = LocalisationFragment.class;
                break;
            case R.id.nav_setting_fragment:
                fragmentClass = SettingFragment.class;
                break;
            case R.id.nav_share_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_send_fragment:
                fragmentClass = SendFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    public void onBluetooth(MenuItem item) {
        Intent i= new Intent(this, DeviceScanActivity.class);
        startActivity(i);
    }

    public void sendEmail() {
        String uriText =
                "mailto:email@provider.com" +
                        "?subject=" + Uri.encode("some subject text here") +
                        "&body=" + Uri.encode("some text here");

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, "Send email"));
        }
    }

    /*public void sendSMS(String phone) {

        String sms = "Please help," +  "location()" + "My Body Diagnostic -\n" + "Heart beat:89BMP\n" + "Oxygen:89% \n" + "Breathing:89 \n" + "Tempeture:89.F \n";

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

    }*/



    public void onLogout(MenuItem item) {
        signOutDialog();
        //signOut();
    }

    @Override
    public void onBackPressed() {
        signOutDialog();
        //System.exit(0);
    }

    public void signOutDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
        alertDialog.setTitle("Deconnection");
        alertDialog.setMessage("Voulez-vous vous deconnectez ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();

            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void signOut(){
        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MenuActivity.this, fault.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
