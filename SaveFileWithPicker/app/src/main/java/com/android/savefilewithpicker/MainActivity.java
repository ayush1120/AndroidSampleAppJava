package com.android.savefilewithpicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    EditText mFileNameEditText;
    Button mPickerButton, mSaveFileButton, mDeleteFileButton;
    TextView mUriPath;
    Uri mSaveUri;
    Boolean mFileSaved = false;

    private final int LAUNCH_PICKER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFileNameEditText = findViewById(R.id.filename_edit);
        mPickerButton = findViewById(R.id.picker_button);
        mSaveFileButton = findViewById(R.id.file_save_button);
        mDeleteFileButton = findViewById(R.id.delete_button);
        mUriPath = findViewById(R.id.uri_path);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Log.d("DisplayMetrics: ", ": widthPixels: "+metrics.widthPixels);
        Log.d("DisplayMetrics: ", ": heightPixels: "+metrics.heightPixels);
        Log.d("DisplayMetrics: ", ": density: "+metrics.density);
        Log.d("DisplayMetrics: ", ": densityDpi: "+metrics.densityDpi);
        Log.d("DisplayMetrics: ", ": scaledDensity: "+metrics.scaledDensity);
        Log.d("DisplayMetrics: ", ": xdpi: "+metrics.xdpi);
        Log.d("DisplayMetrics: ", ": ydpi: "+metrics.ydpi);

        /*
        DisplayMetrics:: : widthPixels: 923
        DisplayMetrics:: : heightPixels: 580
        DisplayMetrics:: : density: 1.0
        DisplayMetrics:: : densityDpi: 160
        DisplayMetrics:: : scaledDensity: 1.0
        DisplayMetrics:: : xdpi: 321.262
        DisplayMetrics:: : ydpi: 319.79
        */

        mPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setComponent(new ComponentName("com.android.documentsui", "com.android.documentsui.picker.PickActivity"));
                startActivityForResult(intent, LAUNCH_PICKER);
            }
        });

        mSaveFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Ayush: ", "Save Button Clicked");
                if (null != mSaveUri && mFileNameEditText.getText().length() > 0) {
                    String fileName = mFileNameEditText.getText().toString() + ".txt";
                    saveFile(fileName, mSaveUri);
                }
            }
        });

        mDeleteFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFileSaved) {
                    Log.d("Ayush: ", "Delete Button Clicked");
                }
            }
        });

    }

    void saveFile(String fileName, Uri dest) {
        File file = new File(fileName);
        URI juri = null; // java.net.URI
        try {
            juri = new URI(dest.toString());
        } catch (Exception e) {
            Toast.makeText(this, "Save Failed | Uri To URI", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }

        try
        {
            Log.d("Ayush: ", "saveFile: juri obtained ");
            File root = new File(juri);
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists())
            {
                root.mkdirs();
            }
            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append("Hello World"+"\n\n");
            writer.flush();
            writer.close();
            mFileSaved = true;
            Log.d("Ayush: ", "saveFile: FileSaved");
            Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                mSaveUri = data.getData();
                Log.d("Ayush: ", "onActivityResult: recieved Result, uri path: "+mSaveUri);
                mUriPath.setText("uri.getPath() : "+mSaveUri.getPath());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Picker Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}