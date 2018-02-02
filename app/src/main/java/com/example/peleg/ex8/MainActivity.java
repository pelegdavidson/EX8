package com.example.peleg.ex8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText edt1;
    EditText edt2;
    int index;
    final int REGISTERCODE = 1;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.bt);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt1.addTextChangedListener(new tw());
        edt2.addTextChangedListener(new tw());
        RadioGroup group= findViewById(R.id.rg);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                index = radioGroup.indexOfChild(radioButton);
                if(index>0){
                    edt1.setEnabled(true);
                    edt2.setEnabled(true);
                }
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String s1 = edt1.getText().toString();
                String s2 = edt2.getText().toString();
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                if(index==0){
                    i.putExtra("content1", s1);
                    i.putExtra("content2", s2);
                }else if(edt1.getText().length() > 0)
                    i.putExtra("content1", s1);
                else
                    i.putExtra("content2", s2);

                startActivityForResult(i, REGISTERCODE);
            }
        });
    }
    private class tw implements TextWatcher {

        public tw() {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(index==0) {
                edt2.setEnabled(true);
                edt1.setEnabled(true);
                bt.setEnabled((edt1.getText().length() > 0 &&
                        edt2.getText().length() > 0));
            }else {
                bt.setEnabled((edt1.getText().length() > 0 ||
                        edt2.getText().length() > 0));
                if(edt1.getText().length() > 0)
                    edt2.setEnabled(false);
                else
                    edt1.setEnabled(false);
                    edt2.setEnabled(true);

            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };

}
