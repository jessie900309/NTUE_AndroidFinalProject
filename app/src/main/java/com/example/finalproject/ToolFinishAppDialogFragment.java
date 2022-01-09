package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class ToolFinishAppDialogFragment extends DialogFragment
        implements View.OnClickListener {

    // widget
    TextView dialogContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tool_dialog_hint, container, false);

        String dialogText = getString(R.string.main_dialog_finishapp);
        ((TextView) view.findViewById(R.id.dialogContent)).setText(dialogText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.dialogCheckOK){
//            TODO android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(0);
        } else {
            dismiss();
        }
    }

}
