package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.MenuActivity;
import com.example.android.milestone.adapters.ContactAdapter2;
import com.example.android.milestone.adapters.HistoryAdapter;
import com.example.android.milestone.models.Contact;
import com.example.android.milestone.models.History;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.bluetoothlegatt.R.id.lvContact;

/**
 * Created by Owner on 8/19/2017.
 */

public class HistoryFragment extends Fragment implements AddHistory.HistoryListener{

    ArrayList<History> histories;
    HistoryAdapter historyAdapter;
    ListView lvHistory;
    FloatingActionButton flAddHistory;
    BackendlessUser user;
    MenuActivity menuActivity;
    FragmentManager fm;
    History history;
    AddHistory addHistory;
    public SwipeRefreshLayout swipeContainer2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine_contact = inflater.inflate(R.layout.history_ui, container, false);
        histories = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getContext(), histories);
        user = Backendless.UserService.CurrentUser();
        flAddHistory = (FloatingActionButton) racine_contact.findViewById(R.id.floatingAddHistory);
        lvHistory = (ListView) racine_contact.findViewById(R.id.lvHistory);
        swipeContainer2 = (SwipeRefreshLayout) racine_contact.findViewById(R.id.swipContainer2);
        menuActivity = (MenuActivity) getActivity();
        menuActivity.fab.setVisibility(View.INVISIBLE);//Remplacer le FAB d'urgence par le FAB d'ajout de contact
        fm = getFragmentManager();
        lvHistory.setAdapter(historyAdapter);
        addHistory = new AddHistory();

        flAddHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHistory.setTargetFragment(HistoryFragment.this, 300);
                addHistory.show(fm, "Adding an History");
            }
        });


        swipeContainer2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                historyAdapter.clear();
                populateHistory();
            }
        });

        swipeContainer2.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                populateHistory();
            }
        }, 1000);
        return racine_contact;
    }

    private void populateHistory() {

        //histories.add(history);//Fake Data
        if(isOnline()) {

            Backendless.Persistence.of("History").find(new AsyncCallback<List<Map>>() {
                @Override
                public void handleResponse(List<Map> response) {
                    Log.d("DEBUG", response.toString());
                    histories.addAll(History.fromMap(response));
                    historyAdapter.notifyDataSetChanged();
                    swipeContainer2.setRefreshing(false);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d("DEBUG", fault.getMessage());
                    swipeContainer2.setRefreshing(false);
                }
            });
        }else{
            Toast.makeText(getContext(), "Pas d'acces internet", Toast.LENGTH_SHORT).show();
            swipeContainer2.setRefreshing(false);
        }

        historyAdapter.notifyDataSetChanged();

    }

    //Check internet
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void onFinishEditHistory(String summary, String detail, String histDate) {
        HashMap history = new HashMap();
        history.put("summary", summary);
        history.put("detail", detail);
        history.put("hId", user.getUserId());
        history.put("dateH", histDate);
        history.put("doctor", user.getProperty("Doctor"));
        if (isOnline()) {
            swipeContainer2.setRefreshing(true);
            Backendless.Data.of("History").save(history, new AsyncCallback<Map>() {
                @Override
                public void handleResponse(Map response) {
                    Log.d("DEBUG", response.toString());
                    Toast.makeText(menuActivity, "Saved", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d("DEBUG", fault.getMessage());
                    Toast.makeText(menuActivity, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Snackbar.make(getView(), "No internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        historyAdapter.notifyDataSetChanged();
        swipeContainer2.setRefreshing(false);
    }
}
