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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;

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
                getRecommendType();
                //??????
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"s110816032@stu.ntue.edu.tw"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[OuO]???????????????????????? - "+recommendType);
                emailIntent.putExtra(Intent.EXTRA_TEXT   , phoneInfoText+"\n"+recommendText);
                //todo addImageButton
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SubpageSettingRecommend.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                //?????????????????????????????????
            }
        } else if(view.getId()==R.id.setting_help_recommend_addimage){
            findImage();
        } else if (view.getId()==R.id.setting_help_recommendPage) {
            closeKeyBoard(view);
        } else {
            //?????????????????????????????????
        }
    }

    //---------------------------????????????---------------------------

    private void findImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //??????????????????????????????
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {//???????????????????????????
            //???????????????????????????
            Uri uri = data.getData();
            //?????????????????????
            ContentResolver cr = this.getContentResolver();
            try {
                //??????????????????????????????????????????Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                //???Bitmap?????????ImageView
                addImageButton.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //----------------------??????RadioGroup??????----------------------

    private void getRecommendType(){
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
                //show ????????????
                phoneInfoTitle.setVisibility(View.VISIBLE);
                phoneInfo.setVisibility(View.VISIBLE);
                try {
                    phoneInfoText =
                            "???????????????"+ToolDevSystemUtil.getDeviceBrand()+"\n"+
                            "???????????????"+ToolDevSystemUtil.getSystemModel()+"\n"+
                            "???????????????"+ToolDevSystemUtil.getSystemLanguage()+"\n"+
                            "Android??????????????????"+ToolDevSystemUtil.getSystemVersion()+"\n";
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

    //---------------------------????????????---------------------------

    private void closeKeyBoard(View v){
        //(????????????) ????????????(???????????????)
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
        if(view.getId()==R.id.setting_help_recommendPage){
            autoClickView(view,100,100);
        }
        return true;
    }

}
