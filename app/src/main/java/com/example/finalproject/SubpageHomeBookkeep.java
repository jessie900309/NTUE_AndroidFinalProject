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

public class SubpageHomeBookkeep extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        ToolBookTableItemDelDialogFragment.InterfaceCommunicator,
        ToolBookTableItemViewDialogFragment.InterfaceCommunicator{

    // SQLite
    static final String dbName = "FinalProjectDB";
    static final String tbName = "BookKeep";
    static final String[] FROM = new String[] {"date","money","account","classification","member","memo"};
    private static SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    // widget
    ListView listView;

    // value
    String datetime,money,account,classification,member,memo;
    int selectID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_bookkeep);

        //widget
        listView = (ListView) findViewById(R.id.home_bookkeepListView);

        showBookTable();

    }

    //---------------------讀取資料庫------------------------

    private void showBookTable(){
        try {
            db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * FROM "+tbName,null);
            if(cursor.getCount()==0){
                System.out.println("BookKeep cursor.getCount()==0");
                listView.setVisibility(View.INVISIBLE);
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
        selectID = cursor.getInt(0);
        System.out.println("selectID"+selectID);
        datetime = cursor.getString(1);
        money = cursor.getString(2);
        account = cursor.getString(3);
        classification = cursor.getString(4);
        member = cursor.getString(5);
        memo = cursor.getString(6);
        showDetail();
    }

    private void showDetail(){
        String dialogContent = "\n"
                + getString(R.string.home_add_dateText)+ " : " +datetime + "\n"
                + getString(R.string.home_add_moneyText)+ " : " +money + "\n"
                + getString(R.string.home_add_accountSingleText)+ " : " +account + "\n"
                + getString(R.string.home_add_classificationText)+ " : " +classification + "\n"
                + getString(R.string.home_add_memberText)+ " : " +member + "\n"
                + getString(R.string.setting_menuRecommend_textText)+ " : " +memo + "\n";
        String dialogOK = getString(R.string.home_bookkeepingAction_ViewText);
        String dialogNO = getString(R.string.home_bookkeepingAction_DelText);
        ToolBookTableItemViewDialogFragment dialog = ToolBookTableItemViewDialogFragment.newInstance(dialogContent,dialogOK,dialogNO,selectID);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    //---------------------用戶action------------------------

    @Override
    public void sendValue(String returnValue) {
        if(returnValue.equals("del")){
            showBookTable();
            System.out.println("del done back");
        }
    }

    @Override
    public void sendDelValue(String returnValue) {
        if(returnValue.equals("400")){
            checkToDelete();
            System.out.println("choose del");
        }
    }

    private void checkToDelete(){
        String dialogContent = getString(R.string.delItemAlertText) + "\n"
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
