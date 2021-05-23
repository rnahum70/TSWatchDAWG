package com.example.tswatchdawg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.*; // for request params


public class Data extends AppCompatActivity {

//    private static final String TICWATCH_LOG_PATH = "/storage/emulated/0/ticwatch/log/";
    private static final String TICWATCH_LOG_PATH = "/sdcard/ticwatch/log/";
    private static final String SERVER_PASSWORD = "synthetic"; // syntheticduplicitysneerautomaticvexingly

    private static final String TAG = "chinchilla"; // arbitrary tag for debug statements

    DatePicker picker;
    Button btnGet;
    TextView tvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        tvw=(TextView)findViewById(R.id.textView1);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnGet=(Button)findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvw.setText("Data: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
            }
        });

        readTicwatchData();
    }

    private void readTicwatchData() {
        File dir = new File(TICWATCH_LOG_PATH);
        if (dir.exists()) {
            Log.d(TAG, "readTicwatchData:" + TICWATCH_LOG_PATH + " exists!");
            Log.d(TAG, "readTicwatchData: canRead="+ dir.canRead());
            Log.d(TAG, "readTicwatchData: canWrite="+ dir.canWrite());
            Log.d(TAG, "readTicwatchData: canExecute="+ dir.canExecute());
            Log.d(TAG, "readTicwatchData: isDirectory="+ dir.isDirectory());
//            Log.d(TAG, "readTicwatchData: getTotalSpace="+ dir.getTotalSpace());
            File[] files = dir.listFiles();
            if (files == null) {
                Log.d(TAG, "readTicwatchData: dir.listFiles() returned null");
            } else {
                for (File file : files) {
                    Log.d(TAG, "readTicwatchData: file:" + file.getName());
//                if (!file.isDirectory()) {
//                    Log.d(TAG, "readTicwatchData: um");
//                }
                }
            }
            String fname = "ticwatch_log_210512.txt";
            File fpath = new File(TICWATCH_LOG_PATH, fname);
            Log.d(TAG, "readTicwatchData: "+fname+" exists=" + fpath.exists());
            Log.d(TAG, "readTicwatchData: "+fname+" canRead=" + fpath.canRead());
            Log.d(TAG, "readTicwatchData: "+fname+" canWrite=" + fpath.canWrite());
            Log.d(TAG, "readTicwatchData: "+fname+" canExecute=" + fpath.canExecute());

            uploadFile(fpath);
        }
    }

    private void uploadFile(File f) {
        final File file = f;

        RequestParams params = new RequestParams();
        params.put("password", SERVER_PASSWORD);
        try {
            params.put("file", file);
        } catch(FileNotFoundException e) {
            alert("Something went wrong...", "could not find filepath "+file.getName());
        }

        FileSyncClient.post("upload.php", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.trim().equals("success") || responseString.trim().equals("file already exists")) {
                    // upload successful! delete version on device
//                    if (file.exists()) {
//                        if (file.delete()) {
//                            Log.d("chinchilla", "file deleted:" + file.getPath());
//                        } else {
//                            Log.d("chinchilla", "file NOT deleted:" + file.getPath());
//                        }
//                    }
                }
                Log.d("chinchilla", "POST succeeded ––– status code = " + statusCode + ", response: " + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("chinchilla", "uploadFile failure! status code = "+statusCode+", response: "+responseString);
            }

            @Override
            public void onFinish() {
//                numAsyncUploads -= 1;
//                if (numAsyncUploads <= 0) {
//                    syncButton.setEnabled(true);
//                    spinnyWheel.dismiss();
//                    checkSyncComplete(true);
//                } else {
//                    syncButton.setEnabled(false);
//                }
//                Log.d("chinchilla", "onFinish, async tasks = "+numAsyncUploads);
//                checkSyncComplete(false);
            }


        });
    }

    private void alert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

}