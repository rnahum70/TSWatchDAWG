package com.example.tswatchdawg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tswatchdawg.db.Symptom;
import com.example.tswatchdawg.db.TicDatabase;

import java.util.List;
import java.util.function.Function;


public class Data extends AppCompatActivity {

    DatePicker picker;
    Button btnGet, btnClear;
    TextView tvw;

    Boolean confirmResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        tvw = findViewById(R.id.textView1);
        picker = findViewById(R.id.datePicker1);
        btnGet = findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvw.setText("Data: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
                getSymptoms("this argument doesn't do anything");
            }
        });

        btnClear = findViewById(R.id.button2);
        btnClear.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               clearSymptoms();
           }
        });
    }


    private void getSymptoms(String ticTime) {
        TicDatabase db = TicDatabase.getDbInstance(this.getApplicationContext());
        List<Symptom> allSymptoms = db.userDao().getAllUsers();

        String stuff = "";
        for (Symptom symptom: allSymptoms) {
            stuff += symptom.ticType;
            stuff += "\n";
        } // TODO how should we present this data?

        popup("Symptom Data", stuff);
    }

    private void clearSymptoms() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clearing Data");
        builder.setMessage("Are you sure you want to clear all symptom data? This action cannot be reversed.");
        builder.setCancelable(true);
        builder.setPositiveButton("Proceed", (dialog, which) -> {
            TicDatabase db = TicDatabase.getDbInstance(this.getApplicationContext());
            List<Symptom> allSymptoms = db.userDao().getAllUsers();
            for (Symptom symptom : allSymptoms) {
                db.userDao().deleteSymptom(symptom);
                Log.d("okapi", "deleting symptom: "+symptom);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        });

        builder.show();
//        finish();
    }

    private void popup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

//        // Set up the input
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                subject_id = input.getText().toString();
//                login(subject_id);
            }
        });

        builder.show();
    }

//    private void textEntry(String title, String message) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setCancelable(false);
//
//        // Set up the input
//        final EditText input = new EditText(this);
//        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//        builder.setView(input);
//
//        // Set up the buttons
//        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                result = input.getText().toString();
//            }
//        });
//
//        builder.show();
//    }


}