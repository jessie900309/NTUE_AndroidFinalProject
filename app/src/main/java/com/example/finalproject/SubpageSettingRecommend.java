package com.example.finalproject;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SubpageSettingRecommend extends AppCompatActivity
        implements View.OnTouchListener,View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    View pageBackground;
    RadioGroup recommendTypeGroup;
    TextInputEditText recommendInput;
    String recommendType,recommendText;
    TextView phoneInfo,phoneInfoTitle;
    String phoneInfoText;
    ImageView addImageButton;

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
        addImageButton = (ImageView) findViewById(R.id.setting_help_recommend_addimage);
        addImageButton.setOnClickListener(this);
        ((Button) findViewById(R.id.setting_help_recommend_submitButton)).setOnClickListener(this);
        phoneInfoTitle = (TextView) findViewById(R.id.setting_help_recommend_phoneInfoTitle);
        phoneInfo = (TextView) findViewById(R.id.setting_help_recommend_phoneInfo);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.setting_help_recommend_submitButton){
            try {
                //todo 整理
                getRecommendType();
                recommendText = recommendInput.getText().toString();
                //img
                //addImageButton
                //上傳
                System.out.println("\n\n\nrecommendType: "+recommendType);
                System.out.println("\n\n\nrecommendText: "+recommendText);

            } catch (Exception e){
                //其他錯誤，重新導向主頁
            }
        } else if(view.getId()==R.id.setting_help_recommend_addimage){
            findImage();
        } else if (view.getId()==R.id.setting_help_recommendPage) {
            closeKeyBoard(view);
        } else {
            //其他錯誤，重新導向主頁
        }
    }

    //---------------------------取得圖片---------------------------

    public void findImage(){
        Intent intent = new Intent();//開啟Pictures畫面Type設定為image
        intent.setType("image/*");
        //使用Intent.ACTION_GET_CONTENT這個Action 會開啟選取圖檔視窗讓您選取手機內圖檔
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //取得相片後返回本畫面
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//當使用者按下確定後
            //取得圖檔的路徑位置
            Uri uri = data.getData();
            //抽象資料的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //由抽象資料接口轉換圖檔路徑為Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //將Bitmap設定到ImageView
                addImageButton.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //----------------------取得RadioGroup選擇----------------------

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

    //---------------------------關閉鍵盤---------------------------

    public void closeKeyBoard(View v){
        //(收起鍵盤) 取消焦點(收起輸入法)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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

}
