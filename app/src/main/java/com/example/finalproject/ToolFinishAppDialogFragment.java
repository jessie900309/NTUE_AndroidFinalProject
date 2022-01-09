package com.example.finalproject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ToolFinishAppDialogFragment extends DialogFragment
        implements View.OnClickListener {

    String contentText,okText,noText;
    int count;

    //-----------------傳入預設數值-----------------

    public static ToolFinishAppDialogFragment newInstance(String contentText,String okText,String noText,int count) {
        ToolFinishAppDialogFragment fragment = new ToolFinishAppDialogFragment();
        Bundle args = new Bundle();
        //傳入參數
        args.putString("contentText", contentText);
        args.putString("okText", okText);
        args.putString("noText", noText);
        args.putInt("count", count);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------取得傳入參數-----------------

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //取得參數
        contentText = getArguments().getString("contentText");
        okText = getArguments().getString("okText");
        noText = getArguments().getString("noText");
        count = getArguments().getInt("count");
        return super.onCreateDialog(savedInstanceState);
    }

    //------------------畫面呈現----------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tool_dialog_hint, container, false);

        ((TextView) view.findViewById(R.id.dialogContent)).setText(contentText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setText(okText);
        ((Button) view.findViewById(R.id.dialogCheckOK)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setText(noText);
        ((Button) view.findViewById(R.id.dialogCheckNO)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(count>3){
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            dismiss();
        }
    }

}
