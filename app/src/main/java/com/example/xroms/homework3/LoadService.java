package com.example.xroms.homework3;

/**
 * Created by xRoms on 28.11.2016.
 */


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class LoadService extends Service{

    File image;
    private String LOG_TAG = "Loader";
    private String URL = "https://pp.vk.me/c631523/v631523681/2e05f/MSDrRLBJwGA.jpg";

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        image = new File(getFilesDir(), MainActivity.fileName);
        Task();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    void Task() {
        new Thread(new Runnable() {
            InputStream in = null;
            FileOutputStream out = null;
            public void run() {
                try {
                    try {
                        in = new BufferedInputStream(new URL(URL).openStream());
                    }
                    catch (MalformedURLException e) {
                        Log.e(LOG_TAG, "Bad URL");
                    }
                    out = new FileOutputStream(image);

                    int read;
                    byte[] cache = new byte[256];
                    boolean flag = true;
                    while (flag) {
                        read = in.read(cache);
                        if (read != -1) {
                            out.write(cache, 0, read);
                        }
                        else {
                            flag = false;
                        }
                    }
                    sendBroadcast(new Intent(MainActivity.action));
                }
                catch (IOException e) {
                    Log.e(LOG_TAG, "Download went wrong");
                }
            }
        }).start();
    }
}

