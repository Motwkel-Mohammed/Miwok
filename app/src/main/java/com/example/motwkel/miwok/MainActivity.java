package com.example.motwkel.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the intent to open  the activities
        TextView n = (TextView) findViewById(R.id.activity_numbers);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent number = new Intent(MainActivity.this, Numbers.class);
                startActivity(number);
            }
        });

            TextView c = (TextView) findViewById(R.id.activity_colors);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent color = new Intent(MainActivity.this, Colors.class);
                    startActivity(color);
            }
        });

        TextView f= (TextView) findViewById(R.id.activity_familly);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent family = new Intent(MainActivity.this, Familly.class);
                startActivity(family);
        }
         });

        TextView p = (TextView) findViewById(R.id.activity_phrases);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phrases = new Intent(MainActivity.this, Phrases.class);
                startActivity(phrases); }
        });

    }}