package my.e.multithreadingboundservice;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;

import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyService extends Service {

    private static final String TAG = "MyService";

    public static final String PROGRESS_BAR_STATUS = "PROGRESS_BAR_STATUS";
    public static final String PROGRESS_ACTION = "my.e.multithreadingboundservice.ACTION";
    public static final String TEST = "TEST";

    private final IBinder mBinder = new MyLocalBinder();
    public Intent mIntent = new Intent(PROGRESS_ACTION);

    private int mProgress = 0;

    private long mInterval = 200;

    private ExecutorService mExecutorService;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: started");
        mExecutorService = Executors.newFixedThreadPool(1);
        sendProgress();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: started");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: started");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: started");
        super.onRebind(intent);
    }


    private void sendProgress() {
        Log.d(TAG, "sendProgress: stared");
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                while (mProgress < 100) {
                    mProgress += 5;
                    try {
                        Thread.sleep(mInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendThreadStatus(mProgress);
                }
            }
        });
    }


    public void setDown() {
        Log.d(TAG, "change: before" + mProgress);
        if (mProgress == 100) sendProgress();
        mProgress -= 50;
        Log.d(TAG, "change: after" + mProgress);
        if (mProgress < 0)
            mProgress = 0;

    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: started");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mExecutorService.shutdown();
        Log.d(TAG, "onDestroy: started");

    }

    private void sendThreadStatus(int progress) {
        Log.d(TAG, "sendThreadStatus: started");
        mIntent.putExtra(PROGRESS_BAR_STATUS, progress);
        sendBroadcast(mIntent);
    }


    public class MyLocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}