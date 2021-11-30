package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SubpageAccount extends Fragment implements View.OnLongClickListener {

    TextView showNetAssets;
    TextView showAllAssets,showDebt,initAssets;
    TextView showAccountMoney,showAccountBank,showAccountCard;

    //id列表
    // account_showNetAssets
    // account_showAllAssets / account_showDebt / account_initAssets
    // account_show_account_money / account_show_account_bank / account_show_account_card


    public SubpageAccount(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_account, container, false);
        //widget
        showNetAssets = view.findViewById(R.id.account_showNetAssets);
        showAllAssets = view.findViewById(R.id.account_showAllAssets);
        showDebt = view.findViewById(R.id.account_showDebt);
        initAssets = view.findViewById(R.id.account_initAssets);
        showAccountMoney = view.findViewById(R.id.account_show_account_money);
        showAccountBank = view.findViewById(R.id.account_show_account_bank);
        showAccountCard = view.findViewById(R.id.account_show_account_card);
        //longClick
        initAssets.setOnLongClickListener(this);

        //TODO read 資料庫 -> TextView setText()

        //
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public boolean onLongClick(View view) {
        if(view.getId()==R.id.account_initAssets){
            //TODO 修改初始餘額
        } else {
            //do nothing
        }
        return false;
    }
}