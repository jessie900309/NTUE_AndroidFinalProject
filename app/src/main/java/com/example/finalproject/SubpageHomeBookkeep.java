package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubpageHomeBookkeep extends AppCompatActivity
        implements AdapterView.OnItemClickListener, View.OnClickListener, ToolBookTableItemDelDialogFragment.InterfaceCommunicator {

    // SQLite
    static final String dbName = "FinalProjectDB";
    static final String[] FROM = new String[] {"date","money","account","classification","member","memo"};
    private static SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    // widget
    ListView listView;
    Button ViewButton,EditButton,DelButton;

    // value
    String datetime,money,account,classification,member,memo;
    int selectID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_bookkeep);

        //widget
        listView = (ListView) findViewById(R.id.home_bookkeepListView);
        ViewButton = (Button) findViewById(R.id.bookkeepAction_view);
        ViewButton.setOnClickListener(this);
        ViewButton.setEnabled(false);
        EditButton = (Button) findViewById(R.id.bookkeepAction_edit);
        EditButton.setOnClickListener(this);
        EditButton.setEnabled(false);
        DelButton = (Button) findViewById(R.id.bookkeepAction_delete);
        DelButton.setOnClickListener(this);
        DelButton.setEnabled(false);

        showBookTable();

    }

    //---------------------讀取資料庫------------------------

    private void showBookTable(){
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
                listView.setOnItemClickListener(this);
            }
            db.close();
        } catch (Exception e){
            DevToolDebug.catchException(e);
        }
    }

    //---------------------讀單筆資料------------------------

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        cursor.moveToPosition(i);

        ViewButton.setEnabled(true);
        EditButton.setEnabled(true);
        DelButton.setEnabled(true);

        selectID = cursor.getInt(0);
        datetime = cursor.getString(1);
        money = cursor.getString(2);
        account = cursor.getString(3);
        classification = cursor.getString(4);
        member = cursor.getString(5);
        memo = cursor.getString(6);

    }

    //---------------------用戶action------------------------

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bookkeepAction_view){

        } else if(view.getId()==R.id.bookkeepAction_edit){

        } else if(view.getId()==R.id.bookkeepAction_delete){
            String dialogContent = getString(R.string.delItemAlertText)
                    + getString(R.string.home_add_dateText)+ " : " +datetime + "\n"
                    + getString(R.string.home_add_moneyText)+ " : " +money + "\n"
                    + getString(R.string.home_add_accountSingleText)+ " : " +account + "\n"
                    + getString(R.string.home_add_classificationText)+ " : " +classification + "\n"
                    + getString(R.string.home_add_memberText)+ " : " +member + "\n"
                    + getString(R.string.setting_menuRecommend_textText)+ " : " +memo + "\n";
            String dialogOK = getString(R.string.delItemAlertOK);
            String dialogNO = getString(R.string.delItemAlertNO);
            ToolBookTableItemDelDialogFragment dialog = ToolBookTableItemDelDialogFragment.newInstance(dialogContent,dialogOK,dialogNO,selectID);
            dialog.show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    public void sendValue(String returnValue) {
        if(returnValue.equals("200")){
            showBookTable();
        }
    }

}
