package com.example.finalproject;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);

        bottomNavigationView = findViewById(R.id.MainNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.nav_account);

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
}
