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
        implements AdapterView.OnItemClickListener,
        ToolBookTransItemDelDialogFragment.InterfaceCommunicator,
        ToolBookTransItemViewDialogFragment.InterfaceCommunicator{

    // SQLite
    static final String dbName = "FinalProjectDB";
    static final String[] FROM = new String[] {"date","money","accountStart","accountEnd","memo"};
    private static SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    // widget
    ListView listView;

    // value
    String datetime,money,accountStart,accountEnd,memo;
    int selectID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_bookkeep_trans);

        //widget
        listView = (ListView) findViewById(R.id.home_bookkeepTransListView);

        showTransTable();

    }


    //---------------------讀取資料庫------------------------

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
        selectID = cursor.getInt(0);
        datetime = cursor.getString(1);
        money = cursor.getString(2);
        accountStart = cursor.getString(3);
        accountEnd = cursor.getString(4);
        memo = cursor.getString(5);
        showDetail();
    }

    private void showDetail(){
        String dialogContent = "\n"
                + getString(R.string.home_add_dateText)+ " : " +datetime + "\n"
                + getString(R.string.home_add_moneyText)+ " : " +money + "\n"
                + getString(R.string.home_add_accountStartText)+ " : " +accountStart + "\n"
                + getString(R.string.home_add_accountEndText)+ " : " +accountEnd + "\n"
                + getString(R.string.setting_menuRecommend_textText)+ " : " +memo + "\n";
        String dialogOK = getString(R.string.home_bookkeepingAction_ViewText);
        String dialogNO = getString(R.string.home_bookkeepingAction_DelText);
        ToolBookTransItemViewDialogFragment dialog = ToolBookTransItemViewDialogFragment.newInstance(dialogContent,dialogOK,dialogNO,selectID);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    //---------------------用戶action------------------------

    @Override
    public void sendValue(String returnValue) {
        if(returnValue.equals("200")){
            showTransTable();
        }
    }

    @Override
    public void sendDelValue(String returnValue) {
        if(returnValue.equals("400")){
            checkToDelete();
        }
    }

    private void checkToDelete(){
        String dialogContent = getString(R.string.delItemAlertText) + "\n"
                + getString(R.string.home_add_dateText)+ " : " +datetime + "\n"
                + getString(R.string.home_add_moneyText)+ " : " +money + "\n"
                + getString(R.string.home_add_accountStartText)+ " : " +accountStart + "\n"
                + getString(R.string.home_add_accountEndText)+ " : " +accountEnd + "\n"
                + getString(R.string.setting_menuRecommend_textText)+ " : " +memo + "\n";
        String dialogOK = getString(R.string.delItemAlertOK);
        String dialogNO = getString(R.string.delItemAlertNO);
        ToolBookTransItemDelDialogFragment dialog = ToolBookTransItemDelDialogFragment.newInstance(dialogContent,dialogOK,dialogNO,selectID);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

}
