package com.chungmyung.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ServiceStart(View view) {
        Intent servie = new Intent(this, MyService.class) ;
        startService(new Intent(this, MyService.class));
    }

    public void onIntentService(View view) {
        Intent servie = new Intent(this, MyIntentService.class) ;
        startService(new Intent(this, MyService.class));
    }
}
