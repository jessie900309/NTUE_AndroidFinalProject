package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class SubpageSetting extends Fragment
        implements View.OnClickListener {

    public SubpageSetting(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sub_setting, container, false);

        //widget click
        int[] listenButtonList = {R.id.settings_basic_fixedIncome, R.id.settings_basic_fixedTransfer, R.id.settings_basic_classification, R.id.settings_basic_member,
                R.id.settings_backup_updateCloud,R.id.settings_backup_outputCSV,R.id.settings_backup_findDev,
                R.id.setting_help_recommend,R.id.setting_help_aboutApp,R.id.setting_help_acknowledgments};
        for(int id:listenButtonList){ ((ImageButton)view.findViewById(id)).setOnClickListener(this); }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings_basic_fixedIncome:
                break;
            case R.id.settings_basic_fixedTransfer:
                break;
            case R.id.settings_basic_classification:
                break;
            case R.id.settings_basic_member:
                break;
            case R.id.settings_backup_updateCloud:
                break;
            case R.id.settings_backup_outputCSV:
                break;
            case R.id.settings_backup_findDev:
                break;
            case R.id.setting_help_recommend:
                break;
            case R.id.setting_help_aboutApp:
                break;
            case R.id.setting_help_acknowledgments:
                break;
            default:

        }
    }
}