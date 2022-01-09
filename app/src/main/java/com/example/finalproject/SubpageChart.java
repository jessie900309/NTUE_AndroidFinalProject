package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class SubpageChart extends Fragment
        implements View.OnClickListener {

    ImageView img1,img2;

    public SubpageChart(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sub_chart, container, false);

        img1 = (ImageView) view.findViewById(R.id.chart1);
        img1.setOnClickListener(this);
        img2 = (ImageView) view.findViewById(R.id.chart2);
        img2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.chart1){
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.VISIBLE);
        } else {
            img1.setVisibility(View.VISIBLE);
            img2.setVisibility(View.INVISIBLE);
        }
    }
}