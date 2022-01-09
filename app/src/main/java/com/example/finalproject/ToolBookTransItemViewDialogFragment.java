package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ToolBookTransItemViewDialogFragment extends DialogFragment
        implements View.OnClickListener {

    // value
    String contentText,okText,noText;
    int itemID;

    //-----------------傳入預設數值-----------------

    public static ToolBookTransItemViewDialogFragment newInstance(String contentText,String okText,String noText,int itemID) {
        ToolBookTransItemViewDialogFragment fragment = new ToolBookTransItemViewDialogFragment();
        Bundle args = new Bundle();
        //傳入參數
        args.putString("contentText", contentText);
        args.putString("okText", okText);
        args.putString("noText", noText);
        args.putInt("itemID",itemID);
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
        itemID = getArguments().getInt("itemID");
        return super.onCreateDialog(savedInstanceState);
    }

    //------------------畫面呈現---------------------

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
        if(view.getId()==R.id.dialogCheckOK){
            //神喵也不做
        } else if (view.getId()==R.id.dialogCheckNO){
            //觸發刪除確認
            clickDelete();
        }
        dismiss();
    }

    //------------------回傳刷新---------------------

    public ToolBookTransItemViewDialogFragment.InterfaceCommunicator interfaceCommunicator;

    public interface InterfaceCommunicator {
        void sendDelValue(String value);
    }

    private SubpageHomeBooktrans mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (SubpageHomeBooktrans) activity;
        }
        catch (ClassCastException e) {
            DevToolDebug.catchException(e);
        }
    }

    private void clickDelete(){
        try{
            mCallback.sendDelValue("400");
        }catch (Exception e){
            DevToolDebug.catchException(e);
        }
    }
}
