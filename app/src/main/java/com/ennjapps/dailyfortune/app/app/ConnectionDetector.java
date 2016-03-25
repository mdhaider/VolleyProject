package com.ennjapps.dailyfortune.app.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by haider on 24-03-2016.
 */
public class ConnectionDetector {
    private Context context;

    ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}