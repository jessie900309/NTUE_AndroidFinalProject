package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubpageHomeBooktrans extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    // SQLite
    static final String dbName = "FinalProjectDB";
    static final String[] FROM = new String[] {"date","money","accountStart","accountEnd","memo"};
    private static SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    // widget
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_bookkeep_trans);

        //widget
        listView = (ListView) findViewById(R.id.home_bookkeepTransListView);

        showTransTable();

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO
    }

    private void showTransTable(){
        try {
            db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * FROM "+"TransBook",null);
            if(cursor.getCount()==0){
                System.out.println("BookKeep cursor.getCount()==0");
            } else {
                System.out.println("BookKeep cursor.getCount() = "+cursor.getCount());
                adapter = new SimpleCursorAdapter(this,
                        R.layout.tool_listview_bookkeeping_trans,
                        cursor,
                        FROM,
                        new int[] {R.id.showTransBookItem_date,R.id.showTransBookItem_money,
                                R.id.showTransBookItem_acStart,R.id.showTransBookItem_acEnd,
                                R.id.showTransBookItem_memo
                        },
                        0);
                listView.setAdapter(adapter);
            }
            db.close();
        } catch (Exception e){
            DevToolDebug.catchException(e);
        }
    }

}
