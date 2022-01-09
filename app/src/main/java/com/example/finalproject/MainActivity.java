package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button AppStartButton = (Button) findViewById(R.id.app_start_button);
        AppStartButton.setOnClickListener(this);

        //資料庫創建
        try {
            ToolDatabaseOpenHelper db = new ToolDatabaseOpenHelper(this,"FinalProjectDB",null,1);
            db.getWritableDatabase();
            db.close();
        } catch (Exception e){
            DevToolDebug.catchException(e);
        }

        //帳戶創建
        try {
            SQLiteDatabase db = openOrCreateDatabase("FinalProjectDB", Context.MODE_PRIVATE,null);
            Cursor cursor = db.rawQuery("SELECT * FROM "+"UserAccount",null);
            if(cursor.getCount()==0){
                db.insert("UserAccount",null,addDataToAccount(getString(R.string.init_account_money_text)));
                db.insert("UserAccount",null,addDataToAccount(getString(R.string.init_account_bank_text)));
                db.insert("UserAccount",null,addDataToAccount(getString(R.string.init_account_card_text)));
            }
            db.close();
        } catch (Exception e){
            DevToolDebug.catchException(e);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.app_start_button){

            Intent intent = new Intent();
            intent.setClass(MainActivity.this,MainPageActivity.class);
            startActivity(intent);
            finish();

            //if 有登入檔 MainPageActivity(main_page.xml)

            //else LoginActivity(login_page.xml)
            //intent.setClass(MainActivity.this,LoginActivity.class);
            //startActivity(intent);

        }
    }

    private ContentValues addDataToAccount(String accountName){
        ContentValues cv = new ContentValues(3);
        cv.put("accountName",accountName);
        cv.put("initNumber","0");
        cv.put("nowNumber","0");
        return cv;
    }

}