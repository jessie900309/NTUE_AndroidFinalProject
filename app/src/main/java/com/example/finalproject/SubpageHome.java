package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SubpageHome extends Fragment
        implements View.OnClickListener {

    FloatingActionButton floatingButton;

    public SubpageHome(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sub_home, container, false);

        (view.findViewById(R.id.homeButtonToBookkeep)).setOnClickListener(this);
        (view.findViewById(R.id.homeButtonToTransBook)).setOnClickListener(this);
        (view.findViewById(R.id.homeButtonToCalendar)).setOnClickListener(this);

        floatingButton = view.findViewById(R.id.HomeFloatingButton);
        floatingButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        try{
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.homeButtonToBookkeep:
                    intent.setClass(getActivity(), SubpageHomeBookkeep.class);
                    startActivity(intent);
                    break;
                case R.id.homeButtonToTransBook:
                    intent.setClass(getActivity(), SubpageHomeBooktrans.class);
                    startActivity(intent);
                    break;
                case R.id.homeButtonToCalendar: //todo 未開發功能(日曆顯示)
                    intent.setClass(getActivity(), SubpageHomeCalendar.class);
                    startActivity(intent);
                    break;
                case R.id.HomeFloatingButton:
                    intent.setClass(getActivity(), SubpageHomeAdd.class);
                    startActivity(intent);
                    break;
                default:
                    //do nothing
            }
        }catch (Exception e){
            ToolDevDebug.catchException(e);
        }

    }
}