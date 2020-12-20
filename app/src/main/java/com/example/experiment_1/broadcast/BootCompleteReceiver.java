package com.example.experiment_1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author ylqq
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"开机启动MsgService...");
        System.out.println("开机启动MsgService...");
        //TODO 开机显示的Toast不稳定，有时候能显示有时候不能显示
        Toast.makeText(context,"开机启动",Toast.LENGTH_LONG).show();
    }
}
