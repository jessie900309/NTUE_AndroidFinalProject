package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button AppStartButton = (Button) findViewById(R.id.app_start_button);
        AppStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.app_start_button){
            Intent intent = new Intent();
            //if 有登入檔 MainPageActivity(main_page.xml)
            intent.setClass(MainActivity.this,MainPageActivity.class);
            startActivity(intent);
            //else LoginActivity(login_page.xml)
            //intent.setClass(MainActivity.this,LoginActivity.class);
            //startActivity(intent);
        }
    }
}