package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ToolDatabaseOpenHelper extends SQLiteOpenHelper {

    private final static int _DataBaseVersion = 1; //<-- 版本
    private final static String _DataBaseName = "FinalProjectDB";  //<-- db name

    private final static String _AccountTableName = "UserAccount";
    private final static String _BookTableName = "BookKeep";
    private final static String _TransTableName = "TransBook";

    public ToolDatabaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, _DataBaseName, factory, _DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create UserAccount Table (1+3)
        //允許更新，不允許新增或刪除
        String createUserAccount = "CREATE TABLE IF NOT EXISTS " +
                _AccountTableName +
                "(" +
                "_id integer primary key autoincrement, " +
                "accountName varchar(60), " +
                "initNumber varchar(60), " +
                "nowNumber varchar(60)" +
                ")";
        sqLiteDatabase.execSQL(createUserAccount);

        //create BookKeep Table (1+6)
        String createBookKeep = "CREATE TABLE IF NOT EXISTS " +
                _BookTableName +
                "(" +
                "_id integer primary key autoincrement, " +
                "date varchar(60)," +
                "money varchar(60)," +
                "account varchar(60)," +
                "classification varchar(60)," +
                "member varchar(60)," +
                "memo varchar(600)" +
                ")";
        sqLiteDatabase.execSQL(createBookKeep);

        //create TransBook Table (1+5)
        String createTransBook = "CREATE TABLE IF NOT EXISTS " +
                _TransTableName +
                "(" +
                "_id integer primary key autoincrement, " +
                "date varchar(60)," +
                "money varchar(60)," +
                "accountStart varchar(60)," +
                "accountEnd varchar(60)," +
                "memo varchar(600)" +
                ")";
        sqLiteDatabase.execSQL(createTransBook);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+_AccountTableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+_BookTableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+_TransTableName);
        onCreate(sqLiteDatabase);
    }

}
