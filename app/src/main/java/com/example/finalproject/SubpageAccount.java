package com.example.finalproject;

import android.os.Bundle;
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

    //id列表
    // account_showNetAssets
    // account_showAllAssets / account_showDebt / account_initAssets
    // account_show_account_money / account_show_account_bank / account_show_account_card


    public SubpageAccount(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sub_account, container, false);

        //widget
        showNetAssets = view.findViewById(R.id.account_showNetAssets);
        showAllAssets = view.findViewById(R.id.account_showAllAssets);
        showDebt = view.findViewById(R.id.account_showDebt);
        initAssets = view.findViewById(R.id.account_initAssets);
        showAccountMoney = view.findViewById(R.id.account_show_account_money);
        showAccountBank = view.findViewById(R.id.account_show_account_bank);
        showAccountCard = view.findViewById(R.id.account_show_account_card);
        floatingButton = view.findViewById(R.id.AccountFloatingButton);

        //click
        floatingButton.setOnClickListener(this);
        //longClick
        initAssets.setOnLongClickListener(this);

        //TODO read 資料庫 -> TextView setText()

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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.AccountFloatingButton){
            //TODO 新增帳戶 or 記帳
        } else {
            //do nothing
        }
    }
}