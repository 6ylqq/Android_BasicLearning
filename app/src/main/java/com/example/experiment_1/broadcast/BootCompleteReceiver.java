package com.example.experiment_1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"开机启动MsgService...");
        Toast.makeText(context,"开机启动",Toast.LENGTH_LONG).show();
    }
}
