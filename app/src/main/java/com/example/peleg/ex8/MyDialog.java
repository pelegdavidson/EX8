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
        tvnum.setText(String.format("%.0f",123.0));
        sk.setProgress(0);
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
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(), "You pushed cancel",
                                        Toast.LENGTH_LONG).show();
                                dismiss();
                                }
                        })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.onFinishDialog(regcode,sk.getProgress());
                        dismiss();
                    }
                });
    }
    public interface ResultListener{
        void onFinishDialog(int requestcode, Object results);
    }
}
