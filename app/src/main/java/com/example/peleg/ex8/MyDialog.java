package com.example.peleg.ex8;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialog extends DialogFragment{

    public final static int EXIT_DIALOG=1;
    public final static int PRECISION_DIALOG=2;
    private int regcode;
    private static MyDialog dlg = null;
    private ResultListener listener;
    private PrecisionListener resolutionListener;

    public static MyDialog newInstance(int requestCode){
        if(dlg==null)
            dlg = new MyDialog();
        Bundle args = new Bundle();
        args.putInt("rc",requestCode);
        dlg.setArguments(args);
        return dlg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.regcode = getArguments().getInt("rc");
        if(regcode == EXIT_DIALOG)
            return buildExitDialog().create();
        return buildPrecisinDialog().create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (ResultListener) context;
            this.resolutionListener = (PrecisionListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException("hosting activity must implement ResultListener");
        }
    }

    private AlertDialog.Builder buildExitDialog(){
        return new AlertDialog.Builder(getActivity())
                .setTitle("closing the application")
                .setMessage("are you sure?")
                .setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               if(listener!=null)
                                   listener.onFinishDialog(regcode, "ok");
                               dismiss();
                            }
                        }
                )
                .setNegativeButton("no",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dismiss();
                            }
                        }
                );

    }
    private AlertDialog.Builder buildPrecisinDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layoutprecision,null);
        final SeekBar sk = view.findViewById(R.id.seekbar);
        final TextView tvnum = view.findViewById(R.id.tvnum);
        tvnum.setText(String.format("%." + resolutionListener.getCorrentPrecision() +"f",123.0));
        sk.setProgress(resolutionListener.getCorrentPrecision());
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvnum.setText(String.format("%."+i+"f",123.0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(listener!=null)
                                    listener.onFinishDialog(regcode,sk.getProgress());
                                dismiss();
                                }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
    }
    public interface ResultListener{
        void onFinishDialog(int requestcode, Object results);
    }
    public interface PrecisionListener{
        Integer getCorrentPrecision();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(), "destroyyyyyyyyyyyy",
                Toast.LENGTH_LONG).show();
    }
}
