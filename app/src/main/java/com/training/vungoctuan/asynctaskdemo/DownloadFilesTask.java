package com.training.vungoctuan.asynctaskdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vungoctuan on 2/9/18.
 */
public class DownloadFilesTask extends AsyncTask<String,String, String> {

    private MainActivity mainActivity;
    public DownloadFilesTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream(Environment
                .getExternalStorageDirectory().getPath() + "/image_downloaded.jpg");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress(""+(int)((total*100)/lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        mainActivity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        mainActivity.progressBar.setVisibility(View.GONE);
        mainActivity.button_download.setVisibility(View.GONE);
        String imagePath = Environment.getExternalStorageDirectory().toString() +
            "/image_downloaded.jpg";
        mainActivity.image_downloaded.setImageDrawable(Drawable.createFromPath(imagePath));
    }
}
