package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
        int[] listenButtonList = {
                R.id.settings_basic_fixedIncome, R.id.settings_basic_fixedTransfer,
                R.id.settings_basic_classification, R.id.settings_basic_member,
                R.id.settings_backup_updateCloud,R.id.settings_backup_outputCSV,
                R.id.setting_help_recommend,R.id.settings_help_findDev,
                R.id.setting_help_aboutApp,R.id.setting_help_acknowledgments};
        for(int id:listenButtonList){ ((Button)view.findViewById(id)).setOnClickListener(this); }

        return view;
    }

    @Override
    public void onClick(View view) {
        
        try {
            Intent intent = new Intent();
            switch (view.getId()){

                // HELP
                case R.id.setting_help_recommend:
                    intent.setClass(getActivity(), SubpageSettingRecommend.class);
                    startActivity(intent);
                    break;
                case R.id.settings_help_findDev:
                    intent.setClass(getActivity(), SubpageSettingFindDev.class);
                    startActivity(intent);
                    break;
                case R.id.setting_help_aboutApp:
                    intent.setClass(getActivity(), SubpageSettingAbout.class);
                    startActivity(intent);
                    break;
                case R.id.setting_help_acknowledgments:
                    intent.setClass(getActivity(), SubpageSettingAcknowledgment.class);
                    startActivity(intent);
                    break;

                //todo 未開發功能(動態menu&雲端備份)

                case R.id.settings_basic_fixedIncome:
                case R.id.settings_basic_fixedTransfer:
                case R.id.settings_basic_classification:
                case R.id.settings_basic_member:
                case R.id.settings_backup_updateCloud:
                case R.id.settings_backup_outputCSV:
                    intent.setClass(getActivity(), ToolDefaultActivity.class);
                    startActivity(intent);
                    break;
                default:
                    //do nothing
            }
        } catch (Exception e) {
            System.out.println("\n\n\n"+e+"\n\n\n");
        }

    }
}