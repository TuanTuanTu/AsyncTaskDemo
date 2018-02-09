package com.training.vungoctuan.asynctaskdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private static int MY_PERMISSIONS_REQUEST_READ_STORAGE = 1;
    private static int MY_PERMISSIONS_REQUEST_WRITE_STOREAGE = 2;
    public Button button_download;
    public ImageView image_downloaded;
    public ProgressBar progressBar;
    private DownloadFilesTask downloadFilesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_download = findViewById(R.id.button_download);
        image_downloaded = findViewById(R.id.image_downloaded);
        progressBar = findViewById(R.id.progressBar);
        downloadFilesTask = new DownloadFilesTask(this);

        requestPermission();

        button_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFilesTask.execute(
                    "http://www.catster.com/wp-content/uploads/2017/08/A-brown-cat-licking-its-lips.jpg");
            }
        });
    }

    void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STOREAGE);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_STORAGE);
            }
        }


    }
}
