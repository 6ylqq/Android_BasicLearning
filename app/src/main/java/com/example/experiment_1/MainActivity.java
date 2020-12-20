package com.example.experiment_1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.experiment_1.broadcast.MyBroadcastReceiver;
import com.example.experiment_1.broadcast.NetChangeReceiver;

/**
 * @author ylqq
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NetChangeReceiver netChangeReceiver;
    private MyBroadcastReceiver myBroadcastReceiver;
    private static final String CHANNEL_ID = "BootBroadcastReceiverId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button toExOne = findViewById(R.id.toEx1);
        Button btn_receiver=findViewById(R.id.btn_receiver);
        Button btn_register=findViewById(R.id.btn_register);
        Button btn_mySelf=findViewById(R.id.btn_mySelf);
        Button btn_pendingIntent=findViewById(R.id.btn_pendingIntent);

        toExOne.setOnClickListener(this);
        btn_mySelf.setOnClickListener(this);
        btn_receiver.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_pendingIntent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toEx1:
                //静态注册，以及设置开机自启并发送通知
                //详见清单配置文件
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                //动态注册监听网络
                netChangeReceiver= new NetChangeReceiver();
                IntentFilter netConnIntentFilter = new IntentFilter();
                netConnIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                this.registerReceiver(netChangeReceiver, netConnIntentFilter);
                break;
            case R.id.btn_mySelf:
                //自定义广播的发送与接收，会显示hello world
                sentBroadcast();
                break;
            case R.id.btn_receiver:
                String textTitle = "BootBroadcastReceiver";
                String textContent = "开机启动的通知";
                Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(textTitle)
                        .setContentText(textContent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build();
                NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManagerCompat notificationManagerText = NotificationManagerCompat.from(this);
                notificationManagerText.createNotificationChannel(notificationChannel);
                notificationManagerText.notify(1,notification2);
            case R.id.btn_pendingIntent:
                Intent intent1=new Intent(this,LoginActivity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                Notification notification=new NotificationCompat.Builder(this,getString(R.string.app_name))
                        .setContentTitle("PendingIntent")
                        .setContentText("hello pendingIntent")
                        .setWhen(System.currentTimeMillis())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .build();
                NotificationChannel mChannel = new NotificationChannel(getString(R.string.app_name), getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
                mChannel.setDescription("notification channel");
                notificationManager.createNotificationChannel(mChannel);
                System.out.println("hi");
                notificationManager.notify(2,notification);
                break;
            default:break;
        }
    }

    /**
     *自定义发送广播
     */
    private void sentBroadcast() {
        Intent intent = new Intent("myself");
        intent.putExtra("data", " myselfBroadcast");
        sendBroadcast(intent);

        //接收广播：
        myBroadcastReceiver = new MyBroadcastReceiver();
        this.registerReceiver(myBroadcastReceiver, new IntentFilter("myself"));
    }


    @Override
    protected void onDestroy() {
        //注销接收器
        this.unregisterReceiver(netChangeReceiver);
        this.unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }


}