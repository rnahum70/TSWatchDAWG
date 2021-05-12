package com.example.tswatchdawg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView tic, data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // defining each of the button cards on the home page
        tic= (CardView) findViewById(R.id.tic);
        data= (CardView) findViewById(R.id.data);
        // Add a click listener to each of the buttons
        tic.setOnClickListener(this);
        data.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
// everytime we test which card is clicked
        switch (v.getId()) {
            case R.id.tic: i = new Intent(this,Tic.class);startActivity(i); break;
            case R.id.data: i = new Intent(this,Data.class);startActivity(i); break;
            default:break;


        }
    }
}