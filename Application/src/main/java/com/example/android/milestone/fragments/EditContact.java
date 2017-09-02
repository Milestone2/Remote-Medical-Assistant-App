package com.example.android.milestone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.models.Contact;
import com.google.android.gms.cast.Cast;

/**
 * Created by Emmanuel Roodly on 30/08/2017.
 */

public class EditContact extends DialogFragment {

    EditText etCM_nom;
    EditText etCM_prenom;
    EditText etCM_tel1;
    EditText etCM_tel2;
    EditText etCM_email;
    TextView tvCM_id;
    Button btnAnnule;
    Button btnEnregistrer;
    Contact mycontact;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_contact, null);



        etCM_nom = (EditText) root.findViewById(R.id.etCM_nom);
        etCM_prenom = (EditText) root.findViewById(R.id.etCM_prenom);
        etCM_email = (EditText) root.findViewById(R.id.etCM_email);
        etCM_tel1 = (EditText) root.findViewById(R.id.etCM_tel1);
        etCM_tel2 = (EditText) root.findViewById(R.id.etCM_tel2);
        tvCM_id = (TextView) root.findViewById(R.id.tvCM_id);
        btnAnnule = (Button) root.findViewById(R.id.btnAnnule);
        btnEnregistrer = (Button) root.findViewById(R.id.btnEnregistrer);


        etCM_nom.setText(mycontact.getNom());
        etCM_prenom.setText(mycontact.getPrenom());
        etCM_email.setText(mycontact.getEmail());
        etCM_tel1.setText(String.valueOf(mycontact.getTel()));
        etCM_tel2.setText(String.valueOf(mycontact.getTel2()));
        tvCM_id.setText(mycontact.getId());


        btnAnnule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public  EditContact(){

    }

    public static EditContact newInstance(Contact contact){
        EditContact editContact = new EditContact();
        editContact.setContact(contact);
        return editContact;
    }


    public void setContact(Contact econtact){
        mycontact = econtact;
    }
}
