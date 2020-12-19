package com.example.experiment_1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.widget.Toast;

public class NetChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network currentNetwork=connectivityManager.getActiveNetwork();
        if (currentNetwork!=null){
            Toast.makeText(context,"现在网络通畅",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"现在网络断了",Toast.LENGTH_LONG).show();
        }
    }
}
