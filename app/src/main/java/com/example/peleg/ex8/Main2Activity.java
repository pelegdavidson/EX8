package com.example.peleg.ex8;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends Activity implements View.OnClickListener{
    String s1;
    String s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Resources res =getResources();
        Intent i = getIntent();
        s1 = i.getStringExtra("content1");
        s2 = i.getStringExtra("content2");
        int newPre1 = i.getIntExtra("content3",0);
        findViewById(R.id.btback).setOnClickListener(this);
        if(s1.length() > 0 &&
                s2.length() > 0&&
                ((Double.parseDouble(s2)*(9.0/5.0)) + 32.0)==Double.parseDouble(s1)){
           TextView tv =  findViewById(R.id.tv2);
           tv.setText(res.getString(R.string.answer1 ,String.format("%."+newPre1+"f",Double.parseDouble(s2)),String.format("%."+newPre1+"f",Double.parseDouble(s1))));
        }else if(s1.length() > 0 &&
                s2.length() > 0&&
                ((Double.parseDouble(s2)*(9.0/5.0)) + 32.0)!=Double.parseDouble(s1)){
            TextView tv =  findViewById(R.id.tv2);
            tv.setText(res.getString(R.string.answer2));
        }else if(s1.length() > 0){
            TextView tv =  findViewById(R.id.tv2);
            s2 = Double.toString((Double.parseDouble(s1)-32.0)*(5.0/9.0));
            tv.setText(res.getString(R.string.answer3 , s1,String.format("%."+newPre1+"f",(Double.parseDouble(s1)-32.0)*(5.0/9.0))));
        }else if(s2.length() > 0){
            TextView tv =  findViewById(R.id.tv2);
            s1 = Double.toString((Double.parseDouble(s2)*(9.0/5.0)) + 32.0);
            tv.setText(res.getString(R.string.answer4 ,s2,String.format("%."+newPre1+"f",Double.parseDouble(s2)*(9.0/5.0)) + 32.0));
        }

    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent();
        i.putExtra("content1", s1);
        i.putExtra("content2", s2);
        setResult(RESULT_OK, i);
        finish();
    }
}
