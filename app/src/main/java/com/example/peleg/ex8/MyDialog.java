package com.example.peleg.ex8;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
        else
            return null;
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
    public interface ResultListener {
        void onFinishDialog(int requestCode, Object results);
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
}
