package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class ToolEditInitAssets extends DialogFragment
        implements View.OnClickListener {

    // SQLite
    static final String dbName = "FinalProjectDB";
    private static SQLiteDatabase db;

    //widget
    TextInputEditText moneyInput,bankInput,cardInput;
    String moneyInit,bankInit,cardInit;
    String moneyText,bankText,cardText;

    //-----------------傳入預設數值-----------------

    public static ToolEditInitAssets newInstance(String moneyInit,String bankInit,String cardInit) {
        ToolEditInitAssets fragment = new ToolEditInitAssets();
        Bundle args = new Bundle();
        //傳入參數
        args.putString("moneyInit", moneyInit);
        args.putString("bankInit", bankInit);
        args.putString("cardInit", cardInit);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------取得傳入參數-----------------

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //取得參數
        moneyInit = getArguments().getString("moneyInit");
        bankInit = getArguments().getString("bankInit");
        cardInit = getArguments().getString("cardInit");
        return super.onCreateDialog(savedInstanceState);
    }

    //------------------畫面呈現----------------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_dialog_account_initnumber, container, false);

        moneyInput = (TextInputEditText) view.findViewById(R.id.edit_initAccountMoney);
        moneyInput.setText(moneyInit);
        bankInput = (TextInputEditText) view.findViewById(R.id.edit_initAccountBank);
        bankInput.setText(bankInit);
        cardInput = (TextInputEditText) view.findViewById(R.id.edit_initAccountCard);
        cardInput.setText(cardInit);

        ((Button) view.findViewById(R.id.edit_initAccountCardSubmitButton)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.edit_initAccountCardSubmitButton){
            moneyText = moneyInput.getText().toString();
            bankText = bankInput.getText().toString();
            cardText = cardInput.getText().toString();
            try{
                double DmoneyText = Double.parseDouble(moneyText);
                double DbankText = Double.parseDouble(bankText);
                double DcardText = Double.parseDouble(cardText);
                if ((DmoneyText<0.0)||(DbankText<0.0)||(DcardText<0.0)){
                    Toast.makeText(getActivity(),getString(R.string.cal_numberOverflow_msg),Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("moneyText",moneyText);
                    bundle.putString("bankText",bankText);
                    bundle.putString("cardText",cardText);
                    Intent intent = new Intent().putExtras(bundle);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

//                UpdateUserAccount(,moneyText);
//                UpdateUserAccount(,bankText);
//                UpdateUserAccount(,cardText);

            } catch (Exception e){
                ToolDevDebug.catchException(e);
                dismiss();
            }
        }
    }



}
