package com.example.experiment_1.getInfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.experiment_1.R;

import java.util.List;

/**
 * @author ylqq
 */
public class GetInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "my_Contacts";
    private TextView showContacts;
    private TextView showLocation;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        initView();
    }

    private void initView() {
        Button btnCursor = findViewById(R.id.btn_cursor);
        Button btnGps = findViewById(R.id.btn_gps);
        Button btnCamera = findViewById(R.id.btn_camera);
        Button btnVideo = findViewById(R.id.btn_video);
        showContacts = findViewById(R.id.showContacts);
        showLocation = findViewById(R.id.showLocation);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnCursor.setOnClickListener(this);
        btnGps.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cursor:
                showContacts.setText(ContactsUtil.getContactInfo(this).toString());
                Log.i(TAG, ContactsUtil.getContactInfo(this).toString());

            case R.id.btn_gps:
                int checkPermission = ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                } else {
                    showMyLocation();
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        showLocation.setText("经度：" + location.getLongitude() + "  纬度:" + location.getLatitude());
                    }
                }
                break;
            case R.id.btn_video:
                Intent intent=new Intent(this,MyVideoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_camera:
                Intent intent1=new Intent(this,MyCameraActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void showMyLocation() {
        //权限确认
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "无法提供位置信息", Toast.LENGTH_LONG).show();
        }
    }

}