package com.example.android.milestone.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.android.milestone.MainActivity;
import com.example.android.milestone.MenuActivity;
import com.example.android.milestone.adapters.ContactAdapter2;
import com.example.android.milestone.models.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class ContactFragment2 extends Fragment implements AddContact.ContactListener {

    ArrayList<Contact> contacts;//Listes des contacts a afficher
    ContactAdapter2 c_Adapter;//Adapteur gerant la liste des conacts
    ListView lvContact;//Objet view qui va afficher les contacts
    Contact contact;
    FloatingActionButton flAddContact;
    AddContact quickAdd;//Objet de type Addcontact, pour enregistrer un nouveau contact (DialogFragment)
    FragmentManager fm;
    MenuActivity menuActivity;//Instance de l'activite principale
    BackendlessUser user;
    EditContact editContact;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine_contact = inflater.inflate(R.layout.contact_ui, container, false);
        contacts = new ArrayList<>();
        c_Adapter = new ContactAdapter2(getContext(), contacts);
        user = Backendless.UserService.CurrentUser();
        flAddContact = (FloatingActionButton) racine_contact.findViewById(R.id.floatingAddContact);
        lvContact = (ListView) racine_contact.findViewById(R.id.lvContact);
        menuActivity = (MenuActivity) getActivity();
        menuActivity.fab.setVisibility(View.INVISIBLE);//Remplacer le FAB d'urgence par le FAB d'ajout de contact
        fm = getFragmentManager();
        lvContact.setAdapter(c_Adapter);
        quickAdd = new AddContact();



        //Ouverture du DialogFragment AddContact pour enregistre un nouveau contact
        flAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickAdd.setTargetFragment(ContactFragment2.this, 300);
                quickAdd.show(fm, "Adding contact");
            }
        });

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editContact =  EditContact.newInstance(contacts.get(position));
                editContact.setTargetFragment(ContactFragment2.this, 400);
                editContact.show(fm, "Edit contact");
            }
        });

        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Backendless.Persistence.of(Contact.class).remove(contacts.get(position), new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {
                        Toast.makeText(getContext(), "Contact efface", Toast.LENGTH_SHORT).show();
                        contacts.remove(position);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }
        });


        contact = new Contact();

        populateContact();

        return racine_contact;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        menuActivity.fab.setVisibility(View.VISIBLE);
    }

    public void populateContact() {

        contacts.add(contact);//Fake Data
        if(isOnline()) {
            Backendless.Persistence.of(Contact.class).find(new AsyncCallback<List<Contact>>() {
                @Override
                public void handleResponse(List<Contact> response) {
                    contacts.addAll(response);
                    Log.d("DEBUG", response.toString());
                    c_Adapter.notifyDataSetChanged();

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.d("DEBUG", fault.getMessage());
                }
            });
        }else{
            Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
        }

        c_Adapter.notifyDataSetChanged();
    }


    //Methode qui recoit l'enregistrement d'un nouveau contact et l'ajoute a la liste
    @Override
    public void onFinishEditContact(String nom, String prenom, String email, int number1, int number2) {
        Contact newContact = new Contact(nom, prenom, email, number1, number2, user.getUserId().toString());
        contacts.add(newContact);

        Backendless.Data.of(Contact.class).save(newContact, new AsyncCallback<Contact>() {
            @Override
            public void handleResponse(Contact response) {
                Toast.makeText(getContext(), "Contact saved", Toast.LENGTH_SHORT).show();
                c_Adapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getContext(), fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //check connection
    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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

}
