package com.example.romany.model;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

public class CommonFunctions {

    public CommonFunctions() {
    }

    public void CheckConnection(Context context, AlertDialog progressBar)
    {
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=manager.getActiveNetworkInfo();
        if (null!=activeNetwork)
        {
            if (activeNetwork.getType()==ConnectivityManager.TYPE_WIFI)
            {
                ShowMessage(context,"WIFI ENABLED");
            }

            if (activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE)
            {
                ShowMessage(context,"DATA ENABLED");
            }
        }
        else
        {
            ShowMessage(context,"No Internet");
            progressBar.dismiss();
        }
    }

    public void ShowMessage(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
