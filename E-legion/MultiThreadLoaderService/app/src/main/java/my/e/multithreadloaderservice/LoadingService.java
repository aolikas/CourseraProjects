package my.e.multithreadloaderservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadingService extends Service {

    private static final String TAG = "LoadingService";
    public static final String PROGRESS_ACTION = "my.e.multithreadloaderservice.PROGRESS_ACTION";

    private IBinder mBinder = new LoadingBinder();
    private ExecutorService mExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: started!");
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: started!");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void loading() {
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sendBroadcast();
            }
        });
    }

    private void sendBroadcast() {
        Intent intent = new Intent(PROGRESS_ACTION);
        LocalBroadcastManager.getInstance(LoadingService.this).sendBroadcast(intent);
    }

    public class LoadingBinder extends Binder {
        LoadingService getService() {
            return LoadingService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: started");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: started");
    }


}
