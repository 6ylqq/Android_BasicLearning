package com.example.experiment_1.getInfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.experiment_1.R;

/**
 * @author ylqq
 */
public class MyCameraActivity extends AppCompatActivity {


    private static final int TAKE_PICTURE = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);

        Button btnTackPic = findViewById(R.id.btn_tackPic);
        imageView = findViewById(R.id.imageView);

        btnTackPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPic();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //这个是取默认的缓存图片的
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            //取完整图片
            //TODO 设置为完整的图片大小，目前会在63行报错
        }
    }

    private void getPic() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*File savePic=new File(getExternalCacheDir(),"MyPicture.jpg");
        Uri picUri=FileProvider.getUriForFile(this,"com.example.android.fileProvider",savePic);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,picUri);*/
        startActivityForResult(intent, TAKE_PICTURE);
    }


}