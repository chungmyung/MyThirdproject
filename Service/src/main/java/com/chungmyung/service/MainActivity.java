package com.chungmyung.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements MyService.IServiceCallback, MyIntentService.IServiceCallback {

    private Intent mServiceIntent;
    private MyService mMyService;
    private MyIntentService mMyIntentService;

    private boolean mMyServiceBound;  // 초기는 false./
    private boolean mMyIntentServiceBound;  // 초기는 false./

    private ServiceConnection mMyServiceConnetion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyService = ((MyService.MyBinder) service).getService();
            mMyService.setCallback(MainActivity.this);
            mMyServiceBound = true;

            Toast.makeText(MainActivity.this, "MyService 바인드 됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyServiceBound = false;
        }
    };

    private ServiceConnection mMyIntentServiceConnetion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyIntentService = ((MyIntentService.MyIntentServiceBinder) service).getService();
            mMyIntentService.setCallback(MainActivity.this);
            mMyIntentServiceBound = true;

            Toast.makeText(MainActivity.this, "MyIntentService 바인드됨.  ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyServiceBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ServiceStart(View view) {
        mServiceIntent = new Intent(this, MyService.class);
        startService(mServiceIntent);
        // 종료..
        stopService(mServiceIntent);
    }

    public void onIntentService(View view) {
        Intent service = new Intent(this, MyIntentService.class);
        startService(service);
    }

    public void onBindtMyService(View view) {
        Intent service = new Intent(this, MyIntentService.class);
        bindService(service, mMyServiceConnetion, BIND_AUTO_CREATE);  // 서비스를 create 동시에 Bind하라..
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMyServiceBound) {
            unbindService(mMyServiceConnetion);  // 인자는 connetion객체...
        }
        if (mMyIntentServiceBound) {
            unbindService(mMyIntentServiceConnetion);  // 인자는 connetion객체...
        }
    }

    public void getValue(View view) {
        if (mMyServiceBound) {
            Toast.makeText(mMyService, "MyServie ; " + mMyService.getValue(), Toast.LENGTH_SHORT).show();
        }
        if (mMyIntentServiceBound) {
            Toast.makeText(mMyIntentService, "MyIntentServie ; " + mMyIntentService.getValue(), Toast.LENGTH_SHORT).show();
        }
    }

    @WorkerThread
    @Override
    public void onCallback(final int Value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, " " + value, Toast.LENGTH_SHORT).show();
            }
        });
    }


}