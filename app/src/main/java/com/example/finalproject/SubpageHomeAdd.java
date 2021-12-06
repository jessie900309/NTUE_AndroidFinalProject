package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubpageHomeAdd extends AppCompatActivity {

    //收支:
    //home_add_money_cal
    //home_add_accountSingle*  home_add_accountSingleLayout
    //home_add_classification*  home_add_classificationLayout
    //home_add_member*  home_add_memberLayout
    //home_add_TextInput

    //轉帳:
    //home_add_money_cal
    //home_add_accountStart*  home_add_accountStartLayout
    //home_add_accountEnd*  home_add_accountEndLayout
    //home_add_TextInput

    //home_add_submitButton


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_add);

    }
}
