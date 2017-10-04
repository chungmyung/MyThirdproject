package com.chungmyung.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public static final String TAG = MyIntentService.class.getSimpleName();
    private int i = 0 ;


    public MyIntentService( ) {  // Thread이름을 정해 주는 곳.. 허나 사용자는 쓰레드 이름을 정하질 안으니 arg. 삭제

        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        for ( i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                Log.d(TAG, "onStartCommand : " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setCallback(MainActivity mainActivity) {

    }


    public class MyBinder extends Binder {
        public MyIntentService getService() {
            return MyIntentService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder() ;
    }


    public int getValue() {
        return i ;
    }

    public interface IServiceCallback {
        void onCallback(int Value);
    }

    IServiceCallback mCallback;

    public void setCallback(IServiceCallback callback) {
        mCallback = callback ;
    }
}
