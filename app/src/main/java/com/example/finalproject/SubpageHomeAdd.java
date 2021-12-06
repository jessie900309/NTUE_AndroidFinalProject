package com.example.finalproject;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SubpageHomeAdd extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener, RadioGroup.OnCheckedChangeListener {


    View pageBackground;
    RadioGroup DataTypeGroup;
    String dataType;
    TextInputEditText textInput;


    //收支:
    //pick date
    //home_add_money_cal
    //home_add_accountSingle*  home_add_accountSingleLayout
    //home_add_classification*  home_add_classificationLayout
    //home_add_member*  home_add_memberLayout
    //home_add_TextInput


    //轉帳:
    //pick date
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


    @Override
    public void onClick(View view) {

    }

    //----------------------取得RadioGroup選擇----------------------
    public void getDataType(){
        switch (DataTypeGroup.getCheckedRadioButtonId()){
            case R.id.home_add_income:
                dataType =  getString(R.string.home_add_incomeText);
                break;
            case R.id.home_add_expend:
                dataType =  getString(R.string.home_add_expendText);
                break;
            case R.id.home_add_trans:
                dataType =  getString(R.string.home_add_transText);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if(radioGroup==DataTypeGroup){
            if (DataTypeGroup.getCheckedRadioButtonId()==R.id.home_add_income){
                ((LinearLayout) findViewById(R.id.home_add_accountSingleLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_classificationLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_memberLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_accountStartLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_accountEndLayout)).setVisibility(View.GONE);
            } else {
                ((LinearLayout) findViewById(R.id.home_add_accountSingleLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_classificationLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_memberLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_accountStartLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_accountEndLayout)).setVisibility(View.VISIBLE);
            }
        }
    }

    //---------------------------關閉鍵盤---------------------------
    public void autoClickView(View view,float x,float y){
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime,downTime,MotionEvent.ACTION_DOWN,x,y,0);
        downTime += 10;
        final MotionEvent upEvent = MotionEvent.obtain(downTime,downTime,MotionEvent.ACTION_UP,x,y,0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()==R.id.setting_help_recommendPage){
            autoClickView(view,100,100);
        }
        return true;
    }

}
