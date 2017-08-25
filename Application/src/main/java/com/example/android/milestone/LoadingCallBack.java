package com.example.android.milestone;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Emmanuel Roodly on 25/08/2017.
 */

public class LoadingCallBack implements AsyncCallback {

    private Context context;
    private ProgressDialog progressDialog;

    public LoadingCallBack(Context context){
        this(context, "loading...");
    }


    public LoadingCallBack(Context context, String loadingMessage){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(loadingMessage);
    }

    @Override
    public void handleResponse(Object response) {
        progressDialog.dismiss();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        progressDialog.dismiss();

    }

    public void showLoading(){
        progressDialog.show();

    }

    public  void hideLoading(){
        progressDialog.dismiss();
    }
}
