package leeco.fibonacci;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class FibonacciService extends Service {
    public FibonacciService() {
    }

    private static final String TAG = "FibonacciService";

    private IFibonacciServiceImpl service;

    @Override
    public void onCreate() {
        super.onCreate();
        this.service = new IFibonacciServiceImpl();
        Log.d(TAG, "Fibonacci service onCreate, thread is: " + Thread.currentThread().getId());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return this.service;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "unBind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "serviced started");
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        Log.d(TAG, "Destroyed");
        this.service = null;
        super.onDestroy();
    }
}
