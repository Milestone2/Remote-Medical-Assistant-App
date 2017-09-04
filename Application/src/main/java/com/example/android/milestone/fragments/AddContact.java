package com.example.android.milestone.fragments;




import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bluetoothlegatt.R;


/**
 * Created by Emmanuel Roodly on 19/08/2017.
 */

public class AddContact extends DialogFragment {

    FragmentManager fm;
    EditText etName;
    EditText etPrenom;
    EditText etEmail;
    EditText etNumber1;
    EditText etNumber2;
    Button btSave;
    Button btCancel;
    //ContactFragment2 contactFragment2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View racine_addC = inflater.inflate(R.layout.dialog_add_contact, null);

        fm = getFragmentManager();
        etName = (EditText) racine_addC.findViewById(R.id.etName);
        etPrenom = (EditText) racine_addC.findViewById(R.id.etPrenom);
        etEmail = (EditText) racine_addC.findViewById(R.id.etEmail);
        etNumber1 = (EditText) racine_addC.findViewById(R.id.etNumber1);
        etNumber2 = (EditText) racine_addC.findViewById(R.id.etNumber2);
        btSave = (Button) racine_addC.findViewById(R.id.btSaveContact);
        btCancel = (Button) racine_addC.findViewById(R.id.btCancel);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(etName.getText().toString()) &&
                        !TextUtils.isEmpty(etName.getText().toString()) &&
                        !TextUtils.isEmpty(etPrenom.getText().toString()) &&
                        !TextUtils.isEmpty(etEmail.getText().toString()) &&
                        !TextUtils.isEmpty(etNumber1.getText().toString()) &&
                        !TextUtils.isEmpty(etNumber2.getText().toString())
                        ){
                    SendContactInfo();//Donnees envoyees a la liste des contacts
                    etName.getText().clear();
                    etPrenom.getText().clear();
                    etEmail.getText().clear();
                    etNumber1.getText().clear();
                    etNumber2.getText().clear();
                    dismiss();
                }else{
                    Snackbar.make(view, "Tous les champs sont obligatoires", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }


            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return  racine_addC;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }



    //Definition de l'interface permettant d'envoyer les informations du contact pour les enregistrer
    public interface ContactListener{
        void onFinishEditContact(String nom, String prenom, String email, int number1, int number2);
    }
    //methode utilisant l'interface pour faire passer les donnees
    public void SendContactInfo(){
        ContactListener listener = (ContactListener) getTargetFragment();
        listener.onFinishEditContact(etName.getText().toString(),
                etPrenom.getText().toString(),
                etEmail.getText().toString(),
                Integer.parseInt(etNumber1.getText().toString()),
                Integer.parseInt(etNumber2.getText().toString())
        );

    }
}
