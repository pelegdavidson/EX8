package com.example.peleg.ex8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        String s1 = i.getStringExtra("content1");
        String s2 = i.getStringExtra("content2");
        if(s1.length() > 0 &&
                s2.length() > 0){

        }

    }
}
