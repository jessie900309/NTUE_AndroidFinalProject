package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SubpageSettingFindDev extends AppCompatActivity
        implements View.OnTouchListener,View.OnClickListener {

    View pageBackground;
    TextView phoneInfo;
    TextInputEditText findDevInput;
    String phoneInfoText,findDevText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_setting_help_finddev);

        pageBackground = (View) findViewById(R.id.setting_help_findDevPage);
        pageBackground.setOnClickListener(this);
        pageBackground.setOnTouchListener(this);
        phoneInfo = (TextView) findViewById(R.id.setting_help_findDev_phoneInfo);
        findDevInput = (TextInputEditText) findViewById(R.id.settingpage_help_findDevTextInput);
        ((ImageButton) findViewById(R.id.setting_help_findDev_addimage)).setOnClickListener(this);
        ((Button) findViewById(R.id.setting_help_findDev_submitButton)).setOnClickListener(this);

        //show 裝置資訊
        try {
            phoneInfoText =
                    "手機廠商："+SystemUtil.getDeviceBrand()+"\n"+
                    "手機型號："+SystemUtil.getSystemModel()+"\n"+
                    "系統語言："+SystemUtil.getSystemLanguage()+"\n"+
                    "Android系統版本號："+SystemUtil.getSystemVersion()+"\n";
        } catch (Exception e){
            Toast.makeText(this, getString(R.string.settingFindDev_getInfo_msg), Toast.LENGTH_SHORT).show();
            phoneInfoText = "";
        } finally {
            phoneInfo.setText(phoneInfoText);
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.setting_help_findDev_submitButton){
            try {
                //整理
                //phoneInfoText
                //findDevText = findDevInput.getText().toString();
                //img

                //上傳
                System.out.println("\n\n\nrecommendText: "+findDevText);
            } catch (Exception e){
                //其他錯誤，重新導向主頁
            }
        } else  if(view.getId()==R.id.setting_help_findDev_addimage){
            //TODO load image and show
        } else {
            // R.id.setting_help_recommendPage
            // (收起鍵盤) 取消焦點(收起輸入法)
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

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
        if(view.getId()==R.id.setting_help_findDevPage){
            autoClickView(view,100,100);
        }
        return true;
    }

}
