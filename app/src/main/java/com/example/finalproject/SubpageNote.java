package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SubpageNote extends Fragment implements View.OnClickListener {

    // SQLite
    static final String dbName = "FinalProjectDB";
    private static SQLiteDatabase db;

    // widget
    TextView ShowPath,ShowSize,ShowLimit;
    FloatingActionButton floatingButton;

    public SubpageNote(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sub_note, container, false);

        // widget
        ShowPath = (TextView) view.findViewById(R.id.note_showPath);
        ShowSize = (TextView) view.findViewById(R.id.note_showSize);
        ShowLimit = (TextView) view.findViewById(R.id.note_showLimit);

        // SQLite
        db = getActivity().openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);

        try{
            ShowPath.setText(" "+db.getPath()+"\n");
            ShowSize.setText(" "+db.getPageSize()+"\n");
            ShowLimit.setText(" "+db.getMaximumSize()+"\n");
        } catch (Exception e){
            ShowPath.setText("OuO nothing");
            ShowSize.setText("OuO nothing");
            ShowLimit.setText("OuO nothing");
        }
        db.close();

        floatingButton = view.findViewById(R.id.NoteFloatingButton);
        floatingButton.setOnClickListener(this);

        return view;
    }

    //-----------------------新增記事---------------------

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.NoteFloatingButton){
            //todo 新增記事
            Intent intent = new Intent();
            intent.setClass(getActivity(), ToolDevDefaultActivity.class);
            startActivity(intent);
        }
    }

}