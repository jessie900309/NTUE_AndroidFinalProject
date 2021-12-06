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


        floatingButton = view.findViewById(R.id.HomeFloatingButton);

        //click
        floatingButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.HomeFloatingButton){
            Intent intent = new Intent();
            intent.setClass(getActivity(), SubpageHomeAdd.class);
            startActivity(intent);
        } else {
            //do nothing
        }
    }
}