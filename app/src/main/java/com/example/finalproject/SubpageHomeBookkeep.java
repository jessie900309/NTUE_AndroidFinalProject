package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubpageHomeBookkeep extends AppCompatActivity {

    // SQLite
    static final String dbName = "FinalProjectDB";
    static final String[] FROM = new String[] {"date","money","account","classification","member","memo"};
    private static SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_bookkeep);

        //widget
        ListView listView = (ListView) findViewById(R.id.home_bookkeepListView);

        try {
            db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * FROM "+"BookKeep",null);
            if(cursor.getCount()==0){
                System.out.println("BookKeep cursor.getCount()==0");
            } else {
                System.out.println("BookKeep cursor.getCount() = "+cursor.getCount());
                adapter = new SimpleCursorAdapter(this,
                        R.layout.tool_listview_bookkeeping,
                        cursor,
                        FROM,
                        new int[] {R.id.showBookItem_date,R.id.showBookItem_money,
                                R.id.showBookItem_account,R.id.showBookItem_class,
                                R.id.showBookItem_member,R.id.showBookItem_memo
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
