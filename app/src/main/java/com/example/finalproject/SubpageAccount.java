package com.example.finalproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SubpageAccount extends Fragment
        implements View.OnClickListener,View.OnLongClickListener {

    // SQLite
    static final String dbName = "FinalProjectDB";
    private static SQLiteDatabase db;

    // widget
    TextView showNetAssets;
    TextView showAllAssets,showDebt,initAssets;
    TextView showAccountMoney,showAccountBank,showAccountCard;
    FloatingActionButton floatingButton;
    double dMoneyInitNumber = 0.0; String moneyInitNumber = String.valueOf(dMoneyInitNumber);
    double dBankInitNumber = 0.0;  String bankInitNumber = String.valueOf(dBankInitNumber);
    double dCardInitNumber = 0.0;  String cardInitNumber = String.valueOf(dCardInitNumber);
    double dMoneyNowNumber = 0.0;  String moneyNowNumber = String.valueOf(dMoneyNowNumber);
    double dBankNowNumber = 0.0;   String bankNowNumber = String.valueOf(dBankNowNumber);
    double dCardNowNumber = 0.0;   String cardNowNumber = String.valueOf(dCardNowNumber);

    public SubpageAccount(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sub_account, container, false);

        //widget
        showNetAssets = (TextView) view.findViewById(R.id.account_showNetAssets);
        showAllAssets = (TextView) view.findViewById(R.id.account_showAllAssets);
        showDebt = (TextView) view.findViewById(R.id.account_showDebt);
        initAssets = (TextView) view.findViewById(R.id.account_initAssets);
        initAssets.setOnLongClickListener(this);
        showAccountMoney = (TextView) view.findViewById(R.id.account_show_account_money);
        showAccountBank = (TextView) view.findViewById(R.id.account_show_account_bank);
        showAccountCard = (TextView) view.findViewById(R.id.account_show_account_card);

        floatingButton = view.findViewById(R.id.AccountFloatingButton);
        floatingButton.setOnClickListener(this);

        initPageNumber();
        showPageNumber();

        return view;
    }

    //----------------------編輯初始資產-------------------

    @Override
    public boolean onLongClick(View view) {
        if(view.getId()==R.id.account_initAssets){
            ToolEditInitAssets dialog = ToolEditInitAssets.newInstance(moneyInitNumber,bankInitNumber,cardInitNumber);
            dialog.setTargetFragment(this, 200);
            dialog.show(getFragmentManager(), "dialog");
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                try{
                    dMoneyInitNumber = Double.parseDouble(data.getExtras().getString("moneyText"));
                    dBankInitNumber = Double.parseDouble(data.getExtras().getString("bankText"));
                    dCardInitNumber = Double.parseDouble(data.getExtras().getString("cardText"));
                }catch (Exception e){
                    ToolDevDebug.catchException(e);
                    dMoneyInitNumber = 0.0;
                    dBankInitNumber = 0.0;
                    dCardInitNumber = 0.0;
                }
                moneyInitNumber = String.valueOf(dMoneyInitNumber);
                bankInitNumber = String.valueOf(dBankInitNumber);
                cardInitNumber = String.valueOf(dCardInitNumber);
                showPageNumber();
                if ((dMoneyInitNumber+dBankInitNumber-dCardInitNumber)>=0.0){
                    initAssets.setTextColor(getResources().getColor(R.color.moneyIncreaseTextColor));
                } else {
                    initAssets.setTextColor(getResources().getColor(R.color.moneyDecreaseTextColor));
                }
                //TODO 資料庫更新初始餘額
            }
        }
    }

    //-----------------------新增帳戶---------------------

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.AccountFloatingButton){
            //todo 新增帳戶
            Intent intent = new Intent();
            intent.setClass(getActivity(), ToolDefaultActivity.class);
            startActivity(intent);
        }
    }

    //----------------------顯示數值-----------------------

    public void initPageNumber(){
        db = getActivity().openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+"UserAccount",null);
        if(cursor.getCount()==0){
            System.out.println("cursor.getCount()==0");
        } else {
            System.out.println("\n\n總共有"+cursor.getCount()+"筆資料\n\n"); //always 3
            cursor.moveToFirst();//移到第1筆資料
            do{//逐筆讀出資料
                int itemid = cursor.getInt(0);//id
                String name = cursor.getString(1);//accountName
                String initV = cursor.getString(2);//initNumber
                String nowV = cursor.getString(3);//nowNumber
                if(name.equals(getString(R.string.init_account_money_text))){
                    moneyInitNumber = initV; moneyNowNumber = nowV;
                } else if(name.equals(getString(R.string.init_account_bank_text))){
                    bankInitNumber = initV;  bankNowNumber = nowV;
                } else if(name.equals(getString(R.string.init_account_card_text))){
                    cardInitNumber = initV;  cardNowNumber = nowV;
                }
            }while(cursor.moveToNext());//有一下筆就繼續迴圈 = 3
        }
        db.close();
    }

    public void showPageNumber(){

        dMoneyInitNumber = Double.parseDouble(moneyInitNumber);
        dBankInitNumber = Double.parseDouble(bankInitNumber);
        dCardInitNumber = Double.parseDouble(cardInitNumber);
        dMoneyNowNumber = Double.parseDouble(moneyNowNumber);
        dBankNowNumber = Double.parseDouble(bankNowNumber);
        dCardNowNumber = Double.parseDouble(cardNowNumber);

        showNetAssets.setText(String.valueOf((dMoneyNowNumber+dBankNowNumber+dCardNowNumber)));
        showAllAssets.setText(String.valueOf((dMoneyNowNumber+dBankNowNumber)));
        showDebt.setText(cardNowNumber);
        initAssets.setText(String.valueOf((dMoneyInitNumber+dBankInitNumber-dCardInitNumber)));
        showAccountMoney.setText(moneyNowNumber);
        showAccountBank.setText(bankNowNumber);
        showAccountCard.setText(cardNowNumber);

        if (dMoneyNowNumber >= 0.0){
            showAccountMoney.setTextColor(Color.parseColor("#FF00A600"));
        } else {
            showAccountMoney.setTextColor(Color.parseColor("#FF930000"));
        }

        if (dBankNowNumber >= 0.0){
            showAccountBank.setTextColor(Color.parseColor("#FF00A600"));
        } else {
            showAccountBank.setTextColor(Color.parseColor("#FF930000"));
        }

        if (dCardNowNumber >= 0.0){
            showAccountCard.setTextColor(Color.parseColor("#FF00A600"));
        } else {
            showAccountCard.setTextColor(Color.parseColor("#FF930000"));
        }

    }

}