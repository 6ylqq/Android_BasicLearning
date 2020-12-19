package com.example.experiment_1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.experiment_1.broadcast.MyBroadcastReceiver;
import com.example.experiment_1.broadcast.NetChangeReceiver;

/**
 * @author ylqq
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NetChangeReceiver netChangeReceiver;
    private MyBroadcastReceiver myBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button toExOne = findViewById(R.id.toEx1);
        Button toExTwo = findViewById(R.id.toEx2);
        Button btn_receiver=findViewById(R.id.btn_receiver);
        Button btn_register=findViewById(R.id.btn_register);
        Button btn_mySelf=findViewById(R.id.btn_mySelf);

        toExOne.setOnClickListener(this);
        toExTwo.setOnClickListener(this);
        btn_mySelf.setOnClickListener(this);
        btn_receiver.setOnClickListener(this);
        btn_register.setOnClickListener(this);
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
            default:break;
        }
    }

    /**
     *自定义发送广播
     */
    private void sentBroadcast() {
        Intent intent = new Intent("myself");
        intent.putExtra("data", "myselfBroadcast");
        sendBroadcast(intent);

        //接收广播：
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, new IntentFilter("myself"));
    }


    @Override
    protected void onDestroy() {
        //注销接收器
        this.unregisterReceiver(netChangeReceiver);
        this.unregisterReceiver(myBroadcastReceiver);
        super.onDestroy();
    }


}