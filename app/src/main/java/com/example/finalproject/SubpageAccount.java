package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
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

        //todo 新增帳戶
        floatingButton = view.findViewById(R.id.AccountFloatingButton);
        floatingButton.setOnClickListener(this);
        floatingButton.setVisibility(View.GONE);

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
        //TODO read 資料庫 -> TextView setText("")
        dMoneyInitNumber = 0.0; moneyInitNumber = String.valueOf(dMoneyInitNumber);
        dBankInitNumber = 0.0;  bankInitNumber = String.valueOf(dBankInitNumber);
        dCardInitNumber = 0.0;  cardInitNumber = String.valueOf(dCardInitNumber);
        dMoneyNowNumber = 0.0;  moneyNowNumber = String.valueOf(dMoneyNowNumber);
        dBankNowNumber = 0.0;   bankNowNumber = String.valueOf(dBankNowNumber);
        dCardNowNumber = 0.0;   cardNowNumber = String.valueOf(dCardNowNumber);
    }

    public void showPageNumber(){
        showNetAssets.setText(String.valueOf((dMoneyNowNumber+dBankNowNumber+dCardNowNumber)));
        showAllAssets.setText(String.valueOf((dMoneyNowNumber+dBankNowNumber)));
        showDebt.setText(cardNowNumber);
        initAssets.setText(String.valueOf((dMoneyInitNumber+dBankInitNumber-dCardInitNumber)));
        showAccountMoney.setText(moneyNowNumber);
        showAccountBank.setText(bankNowNumber);
        showAccountCard.setText(cardNowNumber);
    }

}