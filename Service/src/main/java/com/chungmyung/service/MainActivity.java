package com.chungmyung.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Intent mServiceIntent;
    private MyService mMyService;
    private boolean mBound;  // 초기는 false./

    private ServiceConnection mServiceConnetion;

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

    public void onBindtService(View view) {
        Intent service = new Intent(this, MyService.class);
        mServiceConnetion = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMyService = ((MyService.MyBinder) service).getService();
   //             mMyService.setCallback(MainActivity.this);
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
            }
        };
        bindService(service, mServiceConnetion, BIND_AUTO_CREATE);  // 서비스를 create 동시에 Bind하라..
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBound) {
            unbindService(mServiceConnetion);  // 인자는 connetion객체...
        }
    }

    public void getValue(View view) {
        if (mBound) {
            Toast.makeText(mMyService, mMyService.getValue() + " ", Toast.LENGTH_SHORT).show();
        }
    }
}