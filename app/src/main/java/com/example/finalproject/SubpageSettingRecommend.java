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

public class SubpageSettingRecommend extends AppCompatActivity
        implements View.OnTouchListener,View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    View pageBackground;
    RadioGroup recommendTypeGroup;
    TextInputEditText recommendInput;
    String recommendType,recommendText;
    TextView phoneInfo,phoneInfoTitle;
    String phoneInfoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_setting_help_recommend);

        pageBackground = (View) findViewById(R.id.setting_help_recommendPage);
        pageBackground.setOnClickListener(this);
        pageBackground.setOnTouchListener(this);
        recommendTypeGroup = (RadioGroup) findViewById(R.id.setting_help_recommend_typeRadio);
        recommendTypeGroup.check(R.id.settingRecommend_otherType);
        recommendTypeGroup.setOnCheckedChangeListener(this);
        recommendInput = (TextInputEditText) findViewById(R.id.settingpage_help_recommendTextInput);
        ((ImageButton) findViewById(R.id.setting_help_recommend_addimage)).setOnClickListener(this);
        ((Button) findViewById(R.id.setting_help_recommend_submitButton)).setOnClickListener(this);
        phoneInfoTitle = (TextView) findViewById(R.id.setting_help_recommend_phoneInfoTitle);
        phoneInfo = (TextView) findViewById(R.id.setting_help_recommend_phoneInfo);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.setting_help_recommend_submitButton){
            try {
                //整理
                getRecommendType();
                recommendText = recommendInput.getText().toString();
                //img

                //上傳
                System.out.println("\n\n\nrecommendType: "+recommendType);
                System.out.println("\n\n\nrecommendText: "+recommendText);
            } catch (Exception e){
                //其他錯誤，重新導向主頁
            }
        } else if(view.getId()==R.id.setting_help_recommend_addimage){
            //TODO load image and show
        } else if (view.getId()==R.id.setting_help_recommendPage) {
            //(收起鍵盤) 取消焦點(收起輸入法)
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            //其他錯誤，重新導向主頁
        }
    }

    public void getRecommendType(){
        switch (recommendTypeGroup.getCheckedRadioButtonId()){
            case R.id.settingRecommend_QnAType:
                recommendType =  getString(R.string.settingRecommend_type_QnAText);
                break;
            case R.id.settingRecommend_recommendType:
                recommendType =  getString(R.string.settingRecommend_type_recommendText);
                break;
            case R.id.settingRecommend_bugRepoType:
                recommendType =  getString(R.string.settingRecommend_type_bugRepoText);
                break;
            case R.id.settingRecommend_otherType:
                recommendType =  getString(R.string.settingRecommend_type_otherText);
                break;
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
        if(view.getId()==R.id.setting_help_recommendPage){
            autoClickView(view,100,100);
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if(radioGroup==recommendTypeGroup){
            if (recommendTypeGroup.getCheckedRadioButtonId()==R.id.settingRecommend_bugRepoType){
                //show 裝置資訊
                phoneInfoTitle.setVisibility(View.VISIBLE);
                phoneInfo.setVisibility(View.VISIBLE);

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
            } else {
                phoneInfoTitle.setVisibility(View.GONE);
                phoneInfo.setVisibility(View.GONE);
            }
        }
    }
}
