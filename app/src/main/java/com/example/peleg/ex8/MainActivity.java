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

public class MainActivity extends Activity implements MyDialog.ResultListener, MyDialog.PrecisionListener{
    private int indexpre = 0;
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
                i.putExtra("content3", indexpre);
                startActivityForResult(i, REGISTERCODE);
            }
        });
    }

    @Override
    public void onFinishDialog(int requestcode, Object results) {
        this.indexpre=(int) results;
        switch (requestcode)
        {
            case MyDialog.EXIT_DIALOG:
                finish();
                System.exit(0);
            case MyDialog.PRECISION_DIALOG:
                setPrecision((Integer) results);


        }
    }
    public void setPrecision(int newPrecision){
        if(edt1.getText().length()>0){
            Double d1 = Double.parseDouble(edt1.getText().toString());
            edt1.setText(String.format("%."+newPrecision+"f",d1));
        }
        if(edt2.getText().length()>0){
            Double d2 = Double.parseDouble(edt2.getText().toString());
            edt2.setText(String.format("%."+newPrecision+"f",d2));
        }
    }

    @Override
    public Integer getCorrentPrecision() {
        return indexpre;
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
        String s1 = data.getStringExtra("content1");
        String s2 = data.getStringExtra("content2");
        if (requestCode == REGISTERCODE && resultCode == RESULT_OK){
            if(s1.length()>0){
                edt1.setText(String.format("%."+indexpre+"f",(Double.parseDouble(s1))));
            }
            if(s2.length()>0){
                edt2.setText(String.format("%."+indexpre+"f",(Double.parseDouble(s2))));
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
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("preindex",indexpre);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null) {
            indexpre = savedInstanceState.getInt("preindex");

        }
    }
}
