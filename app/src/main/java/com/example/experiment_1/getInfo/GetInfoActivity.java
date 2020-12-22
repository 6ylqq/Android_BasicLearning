package com.example.experiment_1.getInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.experiment_1.R;

/**
 * @author ylqq
 */
public class GetInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "my_Contacts";
    private TextView showContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        initView();
    }

    private void initView(){
        Button btnCursor =findViewById(R.id.btn_cursor);
        Button btnGps =findViewById(R.id.btn_gps);
        Button btnCamera =findViewById(R.id.btn_camera);
        Button btnVideo=findViewById(R.id.btn_video);
        showContacts=findViewById(R.id.showContacts);

        btnCursor.setOnClickListener(this);
        btnGps.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cursor:
                showContacts.setText(ContactsUtil.getContactInfo(this).toString());
                Log.i(TAG,ContactsUtil.getContactInfo(this).toString());
            default:break;
        }

    }
}