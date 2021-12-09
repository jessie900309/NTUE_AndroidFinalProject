package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    //優先檢查是否已存在帳號
    //若有則登入->導向mainpage
    //若無則註冊並登入->導向mainpage

    View pageBackground;
    TextInputEditText emailInput,passwordInput;
    String emailText,passwordText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        //start
        pageBackground = findViewById(R.id.loginPage);
        pageBackground.setOnClickListener(this);//
        pageBackground.setOnTouchListener(this);//
        emailInput = findViewById(R.id.login_TextField_email);
        passwordInput = findViewById(R.id.login_TextField_password);
        loginButton = findViewById(R.id.login_submit_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login_submit_button){
            emailText = emailInput.getText().toString();
            passwordText = passwordInput.getText().toString();
            try {
                //google
                try{
                    //登入
                    //成功Toast
                }catch (Exception e){
                    //不存在帳號即註冊
                    //註冊Toast
                }
            } catch (Exception e){
                //其他錯誤，重新導向主頁
            }
        } else if(view.getId()==R.id.loginPage) {
            //(收起鍵盤) 取消焦點(收起輸入法)
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            //其他錯誤，重新導向主頁
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
        if(view.getId()==R.id.loginPage){
            //解除焦點 getWindow().getDecorView().clearFocus();
            //boolean keyboardIsOpen=imm.isActive();//若返回true，則表示輸入法開啟
            //imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);//強制顯示鍵盤
            //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);//強制隱藏鍵盤
            //(自動點擊) 收起預設鍵盤
            autoClickView(view,100,100);
        }
        return true;
    }
}
