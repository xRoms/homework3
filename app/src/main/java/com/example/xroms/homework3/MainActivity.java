package com.example.xroms.homework3;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView error;
    ImageView image;
    static String fileName = "forsenwithwoman";
    static String action = "com.example.xroms.homework.ACTION_DOWNLOAD_FINISHED";

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            show();
            startService(new Intent(MainActivity.this, LoadService.class));
        }
    };

    BroadcastReceiver br2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        error = (TextView) findViewById(R.id.error);
        image = (ImageView) findViewById(R.id.image);
        image.setVisibility(View.INVISIBLE);
        error.setVisibility(View.INVISIBLE);
        IntentFilter intFilt = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
        IntentFilter intFilt2 = new IntentFilter(action);
        registerReceiver(br, intFilt);
        registerReceiver(br2, intFilt2);
    }

    protected void show() {
        File f = new File(getFilesDir(), fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        if (f.exists()) {
            image.setImageBitmap(bitmap);
            image.setVisibility(View.VISIBLE);
            error.setVisibility(View.INVISIBLE);
        }
        else {
            error.setText("Не загружено");
            image.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy () {
        unregisterReceiver(br);
        unregisterReceiver(br2);
        super.onDestroy();
    }
}
