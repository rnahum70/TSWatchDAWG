package com.example.tswatchdawg;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.tswatchdawg.db.Symptom;
import com.example.tswatchdawg.db.TicDatabase;

import com.hsalf.smilerating.SmileRating;
import com.hsalf.smileyrating.SmileyRating;

import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Tic extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SeekBar seekBar;
    TextView textView;

    private static final String FILE_NAME = "example.txt";
    EditText mEditText;
    Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic);

        mEditText = findViewById(R.id.edit_text);

        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        Toast.makeText(Tic.this, "BAD", Toast.LENGTH_SHORT);
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(Tic.this, "GOOD", Toast.LENGTH_SHORT);
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(Tic.this, "GREAT", Toast.LENGTH_SHORT);
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(Tic.this, "OKAY", Toast.LENGTH_SHORT);
                        break;
                    case SmileRating.TERRIBLE:
                        Toast.makeText(Tic.this, "TERRIBLE", Toast.LENGTH_SHORT);
                        break;
                    case SmileRating.NONE:
                        Toast.makeText(Tic.this, "NONE", Toast.LENGTH_SHORT);
                        break;
                }
            }
        });

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Toast.makeText(Tic.this, "Selected rating" + level, Toast.LENGTH_SHORT);
            }
        });

//        Spinner spinner = findViewById(R.id.spinner1);
//        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.tictypes, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);

        seekBar= (SeekBar)findViewById(R.id.seekBarID);
        textView= (TextView)findViewById(R.id.textViewID);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Tic Intensity " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonSave = findViewById(R.id.buttonSave);


        // Calculate Button
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Time
                SimpleDateFormat time_1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS z");
                String ticTime = time_1.format(new Date());

                //Time Millis
                Date date = null;
                try {
                    date = time_1.parse(ticTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long time_2 = date.getTime();
                String ticTimeMillis = String.valueOf(time_2);


                //Type
                boolean type_vocal = ((CheckBox) findViewById(R.id.checkbox_vocal)).isChecked();
                boolean type_head = ((CheckBox) findViewById(R.id.checkbox_head)).isChecked();
                boolean type_face = ((CheckBox) findViewById(R.id.checkbox_face)).isChecked();
                boolean type_shoulder = ((CheckBox) findViewById(R.id.checkbox_shoulder)).isChecked();
                boolean type_arm = ((CheckBox) findViewById(R.id.checkbox_arm)).isChecked();
                boolean type_leg = ((CheckBox) findViewById(R.id.checkbox_leg)).isChecked();

                List<String> checkedTypes = new ArrayList<>();
                if (type_vocal) checkedTypes.add("Vocal");
                if (type_head) checkedTypes.add("Head");
                if (type_face) checkedTypes.add("Face");
                if (type_shoulder) checkedTypes.add("Shoulder");
                if (type_arm) checkedTypes.add("Arm");
                if (type_leg) checkedTypes.add("Leg");
                String ticType = android.text.TextUtils.join(",", checkedTypes);

                //Intensity
                String ticIntensity = String.valueOf(seekBar.getProgress());

                //Feeling
                String ticFeeling = String.valueOf(smileRating.getSelectedSmile());

                //Notes
                String ticNotes = mEditText.getText().toString();

                saveNewSymptom(ticTime, ticTimeMillis, ticType, ticIntensity, ticFeeling, ticNotes);

            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    public void save(View v) {
//        String text = mEditText.getText().toString();
//        FileOutputStream fos = null;
//        try {
//            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
//            fos.write(text.getBytes());
//            mEditText.getText().clear();
//            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
//                    Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private void saveNewSymptom(String ticTime, String ticTimeMillis, String ticType, String ticIntensity, String ticFeeling, String ticNotes){
        TicDatabase db = TicDatabase.getDbInstance(this.getApplicationContext());

        Symptom symptom = new Symptom();
        symptom.ticTime = ticTime;
        symptom.ticTimeMillis = ticTimeMillis;
        symptom.ticType = ticType;
        symptom.ticIntensity = ticIntensity;
        symptom.ticFeeling = ticFeeling;
        symptom.ticNotes = ticNotes;

        db.userDao().insertSymptom(symptom);
        finish();

    }





}

