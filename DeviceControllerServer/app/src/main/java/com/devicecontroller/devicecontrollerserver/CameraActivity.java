package com.devicecontroller.devicecontrollerserver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.devicecontroller.devicecontrollerserver.server.ServerManagement;

public class CameraActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = findViewById(R.id.imageViewCam);
        try {
            Log.e("CameraActivity", "VAL : " + ServerManagement.CAMERA);
            ServerManagement.CAMERA.equals("null");
            byte[] octetx = Base64.decode(ServerManagement.CAMERA, Base64.NO_WRAP);
            Bitmap bmp = BitmapFactory.decodeByteArray(octetx, 0, octetx.length);
            imageView.setImageBitmap(bmp);
        } catch (NullPointerException e) {
            Toast.makeText(getBaseContext(), "Client disconected", Toast.LENGTH_SHORT).show();
        }
    }
}

