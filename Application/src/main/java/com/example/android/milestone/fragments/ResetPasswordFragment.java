package com.example.android.milestone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.android.bluetoothlegatt.R;
import com.example.android.milestone.Defaults;

/**
 * Created by Emmanuel Roodly on 29/08/2017.
 */

public class ResetPasswordFragment extends DialogFragment {

    EditText etReset;
    Button btnReinit;
    ImageButton btnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.reset_fragment, null);
        etReset = (EditText) root.findViewById(R.id.etReset);
        btnReinit = (Button) root.findViewById(R.id.btnReinit);
        btnCancel = (ImageButton) root.findViewById(R.id.ibCancel);
        Backendless.setUrl( Defaults.SERVER_URL );
        Backendless.initApp( getContext(), Defaults.APPLICATION_ID, Defaults.API_KEY );

        btnReinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backendless.UserService.restorePassword(etReset.getText().toString(), new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        if(!TextUtils.isEmpty(etReset.getText().toString())) {
                            Toast.makeText(getContext(), "Succes", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getContext(), fault.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return  root;
    }
}
