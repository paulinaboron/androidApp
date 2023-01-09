package com.example.paulinaapp01.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Networking {
    public Networking() {
    }

    public boolean connect(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d("xxx", "isInternet: " + netInfoWifi);

        if (netInfoWifi != null && netInfoWifi.isConnectedOrConnecting())
            return true;
        else
            return false;
    }


}
