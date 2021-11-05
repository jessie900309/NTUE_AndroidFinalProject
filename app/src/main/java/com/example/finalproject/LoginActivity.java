package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //優先檢查是否已存在帳號
    //若有則登入->導向mainpage
    //若無則註冊並登入->導向mainpage



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

    }

    @Override
    public void onClick(View view) {

    }
}
