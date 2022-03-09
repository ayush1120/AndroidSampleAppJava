package com.android.savefilewithpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThreadHandleTutorialActivity extends AppCompatActivity {

    Button mThreadStartButton, mThreadStopButton;
    TextView mThreadTextView;
    Handler mUICallbackHandler;
    int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handle_tutorial);

        mThreadStartButton = findViewById(R.id.buttonThreadStart);
        mThreadStopButton = findViewById(R.id.buttonThreadStop);
        mThreadTextView = findViewById(R.id.textThread);
        HandleThreadUtil threadUtil = new HandleThreadUtil();

        mUICallbackHandler = new Handler(Looper.getMainLooper());

        mThreadStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadUtil.startBackgroundThreads();
                Handler handler = threadUtil.getCallbackHandler();
                handler.post(() -> {
                    for(int i=0; i<100000; i++) {
                        mCount = i;
                        Log.d("Ayush: ", "Inside backgroundThread: "+mCount);
                        mUICallbackHandler.post(() -> {
                            Log.d("Ayush: ", "Inside MainThread: "+mCount);
                            mThreadTextView.setText("Loop Iteration : "+(mCount+1));
                        });
                        try {
                           Thread.sleep(100);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                });
            }
        });

        mThreadStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadUtil.stopBackgroundThreads();
            }
        });

    }
}