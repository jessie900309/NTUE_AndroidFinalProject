package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ToolBookTransItemDelDialogFragment extends DialogFragment
        implements View.OnClickListener {

    // SQLite
    static final String dbName = "FinalProjectDB";
    static final String tbName = "TransBook";
    private static SQLiteDatabase db;

    // value
    String contentText,okText,noText;
    int itemID;
    //-----------------傳入預設數值-----------------

    public static ToolBookTransItemDelDialogFragment newInstance(String contentText,String okText,String noText,int itemID) {
        ToolBookTransItemDelDialogFragment fragment = new ToolBookTransItemDelDialogFragment();
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
            try {
                //刪除資料
                db = getActivity().openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
                db.delete(tbName,"_id="+itemID,null);
                db.close();
                //觸發刷新
                done();
            } catch (Exception e) {
                DevToolDebug.catchException(e);
            }
        }
        dismiss();
    }

    //------------------回傳刷新---------------------

    public ToolBookTransItemDelDialogFragment.InterfaceCommunicator interfaceCommunicator;

    public interface InterfaceCommunicator {
        void sendValue(String value);
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

    private void done() {
        try{
            mCallback.sendValue("deltransdone");
        }catch (Exception e){
            DevToolDebug.catchException(e);
        }
    }
}


