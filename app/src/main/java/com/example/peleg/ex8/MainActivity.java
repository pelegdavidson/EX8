package com.example.peleg.ex8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends Activity implements MyDialog.ResultListener{
    EditText edt1;
    EditText edt2;
    int index;
    final int REGISTERCODE = 1;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = findViewById(R.id.toolbar);
        tb.setLogo(R.mipmap.celsius_round);
        setActionBar(tb);
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
                if(index>0) {
                    edt1.setEnabled(true);
                    edt2.setEnabled(true);
                    if(edt1.getText().length() > 0 &&
                            edt2.getText().length() > 0){
                        bt.setEnabled(false);
                    }
                }else{
                    edt2.setEnabled(true);
                    edt1.setEnabled(true);
                    if(edt1.getText().length() > 0 &&
                            edt2.getText().length() > 0){
                        bt.setEnabled(true);
                    }
                }
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String s1 = edt1.getText().toString();
                String s2 = edt2.getText().toString();
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("content1", s1);
                i.putExtra("content2", s2);
                startActivityForResult(i, REGISTERCODE);
            }
        });
    }

    @Override
    public void onFinishDialog(int requestcode, String ok) {
        switch (requestcode)
        {
            case MyDialog.EXIT_DIALOG:
                finish();
                System.exit(0);
            case MyDialog.PRECISION_DIALOG:



        }
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
                if(edt1.getText().length() > 0&& edt2.getText().length() > 0) {
                    edt2.setEnabled(true);
                    edt1.setEnabled(true);
                    bt.setEnabled(false);
                } else if(edt2.getText().length() > 0){
                    edt1.setEnabled(false);
                    bt.setEnabled(true);
                }else if(edt1.getText().length() > 0){
                    edt2.setEnabled(false);
                    bt.setEnabled(true);
                }

            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTERCODE && resultCode == RESULT_OK){
            if(data.getStringExtra("content1").length()>0){
                edt1.setText(data.getStringExtra("content1"));
            }
            if(data.getStringExtra("content2").length()>0){
                edt2.setText(data.getStringExtra("content2"));
            }
            if(edt1.getText().length() > 0 &&
                    edt2.getText().length() > 0&&
                    index>0)
                bt.setEnabled(false);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context= getApplicationContext();
        CharSequence text;
        int duration= Toast.LENGTH_SHORT;
        Toast toast;
        switch (item.getItemId()) {
            case R.id.settings:
                MyDialog.newInstance(MyDialog.PRECISION_DIALOG).show(getFragmentManager(), "precisin");
                return true;
            case R.id.help:
                Intent i = new Intent(Intent.ACTION_VIEW);
                String url = "https://he.wikipedia.org/wiki/מעלות_פרנהייט\n";
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            case R.id.exit:
                MyDialog.newInstance(MyDialog.EXIT_DIALOG).show(getFragmentManager(),"Exit Dialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
