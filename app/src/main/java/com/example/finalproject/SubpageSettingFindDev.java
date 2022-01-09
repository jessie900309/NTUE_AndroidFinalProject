package com.example.finalproject;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;

public class SubpageSettingFindDev extends AppCompatActivity
        implements View.OnTouchListener,View.OnClickListener {

    View pageBackground;
    TextView phoneInfo;
    TextInputEditText findDevInput;
    String phoneInfoText,findDevText;
    ImageView addImageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_setting_help_finddev);

        pageBackground = (View) findViewById(R.id.setting_help_findDevPage);
        pageBackground.setOnClickListener(this);
        pageBackground.setOnTouchListener(this);
        phoneInfo = (TextView) findViewById(R.id.setting_help_findDev_phoneInfo);
        findDevInput = (TextInputEditText) findViewById(R.id.settingpage_help_findDevTextInput);
        addImageButton = (ImageView) findViewById(R.id.setting_help_findDev_addimage);
        addImageButton.setOnClickListener(this);
        ((Button) findViewById(R.id.setting_help_findDev_submitButton)).setOnClickListener(this);

        //show 裝置資訊
        try {
            phoneInfoText =
                    "手機廠商："+ToolDevSystemUtil.getDeviceBrand()+"\n"+
                    "手機型號："+ToolDevSystemUtil.getSystemModel()+"\n"+
                    "系統語言："+ToolDevSystemUtil.getSystemLanguage()+"\n"+
                    "Android系統版本號："+ToolDevSystemUtil.getSystemVersion()+"\n";
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
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"s110816032@stu.ntue.edu.tw"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[OuO]傳送資料給開發者");
                emailIntent.putExtra(Intent.EXTRA_TEXT   , phoneInfoText+"\n"+findDevText);
                //todo addImageButton
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SubpageSettingFindDev.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                //其他錯誤，重新導向主頁
            }
        } else if(view.getId()==R.id.setting_help_findDev_addimage){
            findImage();
        } else {
            closeKeyBoard(view);
        }
    }

    //---------------------------取得圖片---------------------------

    private void findImage(){
        Intent intent = new Intent();//開啟Pictures畫面Type設定為image
        intent.setType("image/*");
        //使用Intent.ACTION_GET_CONTENT這個Action 會開啟選取圖檔視窗 選取手機內圖檔
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

    //---------------------------關閉鍵盤---------------------------

    private void closeKeyBoard(View v){
        //(收起鍵盤) 取消焦點(收起輸入法)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void autoClickView(View view,float x,float y){
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
