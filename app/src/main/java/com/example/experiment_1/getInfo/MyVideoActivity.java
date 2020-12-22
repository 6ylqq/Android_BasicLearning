package com.example.experiment_1.getInfo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.experiment_1.R;

import java.io.File;

/**
 * @author ylqq
 */
public class MyVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        initView();
        initVideo();
    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        Button btnPlay = findViewById(R.id.btn_play);
        Button btnPause = findViewById(R.id.btn_pause);
        Button btnResume = findViewById(R.id.btn_resume);

        btnPause.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnResume.setOnClickListener(this);
    }

    private void initVideo() {
        int checkPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            File file = new File(
                    Environment.getStorageDirectory(),
                    "./Download/VID_20201213172955.mp4");
            System.out.println(Environment.getStorageDirectory());
            if (file != null) {
                videoView.setVideoPath(file.getPath());
            } else {
                Toast.makeText(this, "找不到视频文件", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.btn_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.btn_resume:
                if (videoView.isPlaying()) {
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }
}