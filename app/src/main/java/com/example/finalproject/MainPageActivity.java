package com.example.finalproject;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    int SpecialCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);

        ((BottomNavigationView) findViewById(R.id.MainNavigation)).setOnNavigationItemSelectedListener(this);
        ((BottomNavigationView) findViewById(R.id.MainNavigation)).setSelectedItemId(R.id.nav_home);//初始畫面

        SpecialCount = 0;

    }

    SubpageAccount pageAccount = new SubpageAccount();
    SubpageNote pageNote = new SubpageNote();
    SubpageHome pageHome = new SubpageHome();
    SubpageChart pageChart = new SubpageChart();
    SubpageSetting pageSetting = new SubpageSetting();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_note:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage_screen, pageNote).commit();
                return true;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage_screen, pageAccount).commit();
                return true;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage_screen, pageHome).commit();
                return true;
            case R.id.nav_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage_screen, pageChart).commit();
                return true;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage_screen, pageSetting).commit();
                return true;
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if((event.getKeyCode()==KeyEvent.KEYCODE_BACK) && (event.getAction()==KeyEvent.ACTION_DOWN)){
            SpecialCount++;
            if(SpecialCount>3){
                String dialogContent = getString(R.string.main_dialog_finishappT);
                String dialogOK = getString(R.string.main_dialog_finishappOKT);
                String dialogNO = getString(R.string.main_dialog_finishappNOT);
                ToolFinishAppDialogFragment dialog = ToolFinishAppDialogFragment.newInstance(dialogContent,dialogOK,dialogNO,SpecialCount);
                dialog.show(getSupportFragmentManager(), "dialog");
            } else {
                String dialogContent = getString(R.string.main_dialog_finishapp);
                String dialogOK = getString(R.string.main_dialog_finishappOK);
                String dialogNO = getString(R.string.main_dialog_finishappNO);
                ToolFinishAppDialogFragment dialog = ToolFinishAppDialogFragment.newInstance(dialogContent,dialogOK,dialogNO,SpecialCount);
                dialog.show(getSupportFragmentManager(), "dialog");
            }
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

}
