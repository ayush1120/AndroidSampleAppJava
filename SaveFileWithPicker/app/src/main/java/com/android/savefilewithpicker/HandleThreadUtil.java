package com.android.savefilewithpicker;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

public class HandleThreadUtil {

    private HandlerThread mBackgroundHandlerThread;
    private Handler mCallbackHandler;
    private Looper mLooper;

    public HandleThreadUtil () {
        mLooper = null;
    }

    public HandleThreadUtil (Looper looper) {
        mLooper = looper;
    }

    public Handler getCallbackHandler() {
        return mCallbackHandler;
    }

    public void startBackgroundThreads() {
        mBackgroundHandlerThread = new HandlerThread("CallbacksThread");
        mBackgroundHandlerThread.start();
        mCallbackHandler = new Handler(mBackgroundHandlerThread.getLooper());
    }

    private void use1() {
//       Executor executor = runnable -> mCallbackHandler.post(runnable);
//
//       executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                /// some Code Here
//            }
//       });
    }

    private void use2() {
        mCallbackHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Ayush","Thread.currentThread().getId() : "+Thread.currentThread().getId());
            }
        });
    }


    public void stopBackgroundThreads() {
        Log.i("Ayush", "stopBackgroundThreads: Stopping Background threads");
        // mBackgroundHandlerThread.quit();
        mBackgroundHandlerThread.quit();
        Log.i("Ayush", "stopBackgroundThreads: Inside Try");
        // mBackgroundHandlerThread.join();
        Log.i("Ayush", "stopBackgroundThreads: After Join");
        mBackgroundHandlerThread = null;
        mCallbackHandler = null;
        Log.i("Ayush", "stopBackgroundThreads: Khatam");
    }

}
