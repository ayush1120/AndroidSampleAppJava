package com.android.savefilewithpicker;

import static android.os.Environment.getExternalStorageDirectory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;

import java.io.File;

public class StorageTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_test);

//        @Nullablepublic static String getInternalStorageDirectoryPath(Context context) {
            String storageDirectoryPath;

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                StorageManager storageManager = (StorageManager) this.getSystemService(Context.STORAGE_SERVICE);
                if(storageManager == null) {
                    storageDirectoryPath = null; //you can replace it with the Environment.getExternalStorageDirectory().getAbsolutePath()
                } else {
                    storageDirectoryPath = storageManager.getPrimaryStorageVolume().getDirectory().getAbsolutePath();
                }
//            } else {
//                storageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            }

//            return storageDirectoryPath;
//        }

    // While porting to Android 11 ot later, need to uncomment it
        if(Build.VERSION.SDK_INT>=30)
          startActivity(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION));

        Log.e("StorageTest", "onCreate: "+storageDirectoryPath);
        Log.e("StorageTest", "onCreate: "+Environment.isExternalStorageManager());
        Log.e("StorageTest", "onCreate: "+ getExternalStorageDirectory());

        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        scanAllFiles(root, 1);
    }


    private void scanAllFiles(File parentDir, int val) {
        Log.e("StorageTest", "scanAllFiles: val = "+val);

        if (parentDir == null)
            return;
//        if (Thread.interrupted())
//            return;
        File[] files = parentDir.listFiles(); // list all files in this
        if (files == null)
            return;

        Log.e("StorageTest", "scanAllFiles: "+files.length);
        for (int i = 0; i < files.length; i++) {
            Log.i("StorageTest", "scanAllFiles: path          : "+files[i].getPath());
            Log.i("StorageTest", "scanAllFiles: Absolute path : "+files[i].getAbsolutePath());
            Log.i("StorageTest", "scanAllFiles: name          : "+files[i].getName());
            if (val > 5) {
                break;
            }
//            if (Thread.interrupted())
//                return;
            Log.i("StorageTest", "scanAllFiles: Files Directory = : "+files[i].isDirectory());
            if (files[i].isDirectory()) {
                scanAllFiles(files[i], val+1);
            } else {
                Log.w("StorageTest", "scanAllFiles: Files Directory = : "+files[i].isDirectory());
            }
        }
    }
}