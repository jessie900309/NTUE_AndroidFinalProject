package com.example.finalproject;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class SubpageHomeAdd extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener,
        RadioGroup.OnCheckedChangeListener, ToolCalculatorBottomSheetDialogFragment.InterfaceCommunicator {

    // SQLite
    static final String dbName = "FinalProjectDB";
    private static SQLiteDatabase db;

    // widget
    View pageBackground;
    RadioGroup DataTypeGroup;
    TextInputEditText textMemoInput;
    String dataType;
    Button chooseDateButton, calMoneyButton;
    int nowYear, nowMonth, nowDay;
    String datetime, calMoney;
    Button accountSingleINButton, accountSingleEXButton, classificationINButton, classificationEXButton, memberButton;
    Button accountStartButton, accountEndButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_home_add);

        pageBackground = (View) findViewById(R.id.home_add_page);
        pageBackground.setOnClickListener(this);
        pageBackground.setOnTouchListener(this);

        textMemoInput = (TextInputEditText) findViewById(R.id.home_add_TextInput);
        DataTypeGroup = (RadioGroup) findViewById(R.id.home_add_typeRadio);
        DataTypeGroup.setOnCheckedChangeListener(this);
        DataTypeGroup.check(R.id.home_add_income);
        dataType = getString(R.string.home_add_incomeText);
        ((Button) findViewById(R.id.home_add_submitButton)).setOnClickListener(this);

        //日期初始化
        chooseDateButton = (Button) findViewById(R.id.home_add_date);
        Calendar calendar = Calendar.getInstance();
        nowYear = calendar.get(Calendar.YEAR);
        nowMonth = calendar.get(Calendar.MONTH);
        nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        datetime = nowYear + " - " + (nowMonth + 1) + " - " + nowDay;
        chooseDateButton.setText(datetime);
        chooseDateButton.setOnClickListener(this);

        //金額初始化
        calMoneyButton = (Button) findViewById(R.id.home_add_money_cal);
        calMoneyButton.setText("0.0");
        calMoneyButton.setOnClickListener(this);

        //收入&支出
        accountSingleINButton = (Button) findViewById(R.id.home_add_accountINSingle);
        accountSingleINButton.setText(getString(R.string.init_account_money_text));
        accountSingleINButton.setOnClickListener(this);
        accountSingleEXButton = (Button) findViewById(R.id.home_add_accountEXSingle);
        accountSingleEXButton.setText(getString(R.string.init_account_money_text));
        accountSingleEXButton.setOnClickListener(this);
        classificationINButton = (Button) findViewById(R.id.home_add_classificationIN);
        classificationINButton.setText(getString(R.string.typeNameText_in_other));
        classificationINButton.setOnClickListener(this);
        classificationEXButton = (Button) findViewById(R.id.home_add_classificationEX);
        classificationEXButton.setText(getString(R.string.typeNameText_ex_other));
        classificationEXButton.setOnClickListener(this);
        memberButton = (Button) findViewById(R.id.home_add_member);
        memberButton.setText(getString(R.string.memberName_other));
        memberButton.setOnClickListener(this);

        //轉帳
        accountStartButton = (Button) findViewById(R.id.home_add_accountStart);
        accountStartButton.setOnClickListener(this);
        accountEndButton = (Button) findViewById(R.id.home_add_accountEnd);
        accountEndButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.home_add_submitButton) {
            try {
                Toast formErrorMsg = Toast.makeText(this, getString(R.string.home_add_formMsg), Toast.LENGTH_SHORT);
                if (dataType == getString(R.string.home_add_incomeText)) {
                    if (checkFrom() && checkIncomeFrom()) {
                        IncomeFromToSQL();
                    } else {
                        formErrorMsg.show();
                    }
                } else if (dataType == getString(R.string.home_add_expendText)) {
                    if (checkFrom() && checkExpendFrom()) {
                        ExpendFromToSQL();
                    } else {
                        formErrorMsg.show();
                    }
                } else if (dataType == getString(R.string.home_add_transText)) {
                    if (checkFrom() && checkTransFrom()) {
                        TransFromToSQL();
                    } else {
                        formErrorMsg.show();
                    }
                }
            } catch (Exception e) {
                //其他錯誤，重新導向主頁
                ToolDevDebug.catchException(e);
            }
        } else if (view.getId() == R.id.home_add_date) {//選擇日期
            datePicker(view);
        } else if (view.getId() == R.id.home_add_money_cal) {//開啟算盤
            moneyCounter();
        } else if (view.getId() == R.id.home_add_accountINSingle) {//一般記帳
            accountPickerIN();
        } else if (view.getId() == R.id.home_add_accountEXSingle) {//一般記帳
            accountPickerEX();
        } else if (view.getId() == R.id.home_add_classificationIN) {//一般記帳(收)
            classificationPickerIN();
        } else if (view.getId() == R.id.home_add_classificationEX) {//一般記帳(支)
            classificationPickerEX();
        } else if (view.getId() == R.id.home_add_member) {//一般記帳
            memberPicker();
        } else if (view.getId() == R.id.home_add_accountStart) {//轉帳
            accountStartPicker();
        } else if (view.getId() == R.id.home_add_accountEnd) {//轉帳
            accountEndPicker();
        } else if (view.getId() == R.id.home_add_page) { //其他
            closeKeyBoard(view);
        }
    }

    //---------------------儲存至資料庫------------------------

    public void IncomeFromToSQL() {

        String accountValue = accountSingleINButton.getText().toString();
        String classificationValue = classificationINButton.getText().toString();
        String memberValue = memberButton.getText().toString();
        String memoValue = textMemoInput.getText().toString();

        try {
            ContentValues cv = new ContentValues(6);
            cv.put("date", datetime);
            cv.put("money", calMoney);
            cv.put("account", accountValue);
            cv.put("classification", classificationValue);
            cv.put("member", memberValue);
            cv.put("memo", memoValue);
            SQLiteDatabase db = openOrCreateDatabase("FinalProjectDB", Context.MODE_PRIVATE, null);
            db.insert("BookKeep", null, cv);
            db.close();
        } catch (Exception e) {
            ToolDevDebug.catchException(e);
        }

        UpdateUserAccount(accountValue, calMoney);

        finish();

    }

    public void ExpendFromToSQL() {

        String moneyValue = "-" + calMoney;//金額 -calMoney
        String accountValue = accountSingleEXButton.getText().toString();
        String classificationValue = classificationEXButton.getText().toString();
        String memberValue = memberButton.getText().toString();
        String memoValue = textMemoInput.getText().toString();

        try {
            ContentValues cv = new ContentValues(6);
            cv.put("date", datetime);
            cv.put("money", moneyValue);
            cv.put("account", accountValue);
            cv.put("classification", classificationValue);
            cv.put("member", memberValue);
            cv.put("memo", memoValue);
            SQLiteDatabase db = openOrCreateDatabase("FinalProjectDB", Context.MODE_PRIVATE, null);
            db.insert("BookKeep", null, cv);
            db.close();
        } catch (Exception e) {
            ToolDevDebug.catchException(e);
        }

        UpdateUserAccount(accountValue, moneyValue);

        finish();

    }

    public void TransFromToSQL() {

        String accountStartValue = accountStartButton.getText().toString();
        String accountEndValue = accountEndButton.getText().toString();
        String memoValue = textMemoInput.getText().toString();

        try {
            ContentValues cv = new ContentValues(5);
            cv.put("date", datetime);
            cv.put("money", calMoney);
            cv.put("accountStart", accountStartValue);
            cv.put("accountEnd", accountEndValue);
            cv.put("memo", memoValue);
            db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            db.insert("TransBook", null, cv);
            db.close();
        } catch (Exception e) {
            ToolDevDebug.catchException(e);
        }

        try {
            db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM " + "UserAccount", null);
            if (cursor.getCount() == 0) {
                System.out.println("cursor.getCount()==0");
            } else {
                cursor.moveToFirst();
                do {
                    int itemid = cursor.getInt(0);//id
                    String name = cursor.getString(1);//accountName
                    String initV = cursor.getString(2);//initNumber
                    String nowV = cursor.getString(3);//nowNumber
                    if (name.equals(accountStartValue)) {
                        //讀到轉出錢錢的帳戶 更新現在數值: 讀到的nowNumber - calMoney
                        double newNowNumber = Double.parseDouble(nowV) - Double.parseDouble(calMoney);
                        ContentValues cv = new ContentValues(3);
                        cv.put("accountName", name);
                        cv.put("initNumber", initV);
                        cv.put("nowNumber", String.valueOf(newNowNumber));
                        db.update("UserAccount", cv, "_id=" + itemid, null);
                    } else if (name.equals(accountEndValue)) {
                        //讀到轉入錢錢的帳戶 更新現在數值: 讀到的nowNumber + calMoney
                        double newNowNumber = Double.parseDouble(nowV) + Double.parseDouble(calMoney);
                        ContentValues cv = new ContentValues(3);
                        cv.put("accountName", name);
                        cv.put("initNumber", initV);
                        cv.put("nowNumber", String.valueOf(newNowNumber));
                        db.update("UserAccount", cv, "_id=" + itemid, null);
                    }
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            ToolDevDebug.catchException(e);
        }

        finish();

    }

    public void UpdateUserAccount(String accountName, String AddNewValue) {//更新帳戶餘額(收入或支出)
        try {
            db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM " + "UserAccount", null);
            if (cursor.getCount() == 0) {
                System.out.println("cursor.getCount()==0");
            } else {
                cursor.moveToFirst();
                do {
                    int itemid = cursor.getInt(0);//id
                    String name = cursor.getString(1);//accountName
                    String initV = cursor.getString(2);//initNumber
                    String nowV = cursor.getString(3);//nowNumber
                    if (name.equals(accountName)) {
                        //讀到這次記帳的帳戶 更新現在數值: 讀到的nowNumber + AddNewValue
                        double newNowNumber = Double.parseDouble(nowV) + Double.parseDouble(AddNewValue);
                        ContentValues cv = new ContentValues(3);
                        cv.put("accountName", name);
                        cv.put("initNumber", initV);
                        cv.put("nowNumber", String.valueOf(newNowNumber));
                        db.update("UserAccount", cv, "_id=" + itemid, null);
                    }
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            ToolDevDebug.catchException(e);
        }
    }

    //---------------------檢查表單選項------------------------

    public boolean checkFrom() {
        //Date&Money
        try {
            double DcalMoney = Double.parseDouble(calMoney);
            if ((datetime != "") && (calMoney != "0.0") && (DcalMoney > 0.0)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIncomeFrom() {
        if ((accountSingleINButton.getText().toString() != "")
                && (classificationINButton.getText().toString() != "")
                && (memberButton.getText().toString() != "")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkExpendFrom() {
        if ((accountSingleEXButton.getText().toString() != "")
                && (classificationEXButton.getText().toString() != "")
                && (memberButton.getText().toString() != "")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkTransFrom() {
        if ((accountStartButton.getText().toString() != "")
                && (accountEndButton.getText().toString() != "")
                && (accountStartButton.getText().toString() != accountEndButton.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    //---------------------一般菜單選項(共同)------------------------

    public void datePicker(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//取得現在的日期年月日
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(v.getContext(), R.style.ThemeOverlay_App_DatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String datetime = year + "-" + (month + 1) + "-" + day;
                chooseDateButton.setText(datetime);
            }
        }, year, month, day).show();
    }

    public void moneyCounter() {
        ToolCalculatorBottomSheetDialogFragment dialog = new ToolCalculatorBottomSheetDialogFragment();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void sendValue(String returnValue) {
        //實作計算機介面方法(sendValue)以傳遞計算結果
        try {
            calMoney = returnValue;
        } catch (Exception e) {
            calMoney = "0.0";
        }
        calMoneyButton.setText(calMoney);
    }

    //---------------------一般菜單選項(收支)------------------------

    public void accountPickerIN() {
        PopupMenu popup = new PopupMenu(this, accountSingleINButton);
        popup.getMenuInflater().inflate(R.menu.account_income, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inac_money:
                        accountSingleINButton.setText(getString(R.string.init_account_money_text));
                        break;
                    case R.id.inac_bank:
                        accountSingleINButton.setText(getString(R.string.init_account_bank_text));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void accountPickerEX() {
        PopupMenu popup = new PopupMenu(this, accountSingleEXButton);
        popup.getMenuInflater().inflate(R.menu.account_expenditure, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.exac_money:
                        accountSingleEXButton.setText(getString(R.string.init_account_money_text));
                        break;
                    case R.id.exac_bank:
                        accountSingleEXButton.setText(getString(R.string.init_account_bank_text));
                        break;
                    case R.id.exac_card:
                        accountSingleEXButton.setText(getString(R.string.init_account_card_text));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void classificationPickerIN() {
        getDataType();
        PopupMenu popup = new PopupMenu(this, classificationINButton);
        popup.getMenuInflater().inflate(R.menu.classification_income_type, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.in_work:
                        classificationINButton.setText(getString(R.string.typeNameText_in_work));
                        break;
                    case R.id.in_bonus:
                        classificationINButton.setText(getString(R.string.typeNameText_in_bonus));
                        break;
                    case R.id.in_invest:
                        classificationINButton.setText(getString(R.string.typeNameText_in_invest));
                        break;
                    case R.id.in_interest:
                        classificationINButton.setText(getString(R.string.typeNameText_in_interest));
                        break;
                    case R.id.in_other:
                        classificationINButton.setText(getString(R.string.typeNameText_in_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void classificationPickerEX() {
        getDataType();
        PopupMenu popup = new PopupMenu(this, classificationEXButton);
        popup.getMenuInflater().inflate(R.menu.classification_expenditure_type, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ex_food:
                        classificationEXButton.setText(getString(R.string.typeNameText_ex_food));
                        break;
                    case R.id.ex_clothes:
                        classificationEXButton.setText(getString(R.string.typeNameText_ex_clothes));
                        break;
                    case R.id.ex_houserent:
                        classificationEXButton.setText(getString(R.string.typeNameText_ex_houserent));
                        break;
                    case R.id.ex_trafic:
                        classificationEXButton.setText(getString(R.string.typeNameText_ex_trafic));
                        break;
                    case R.id.ex_medicine:
                        classificationEXButton.setText(getString(R.string.typeNameText_ex_medicine));
                        break;
                    case R.id.ex_other:
                        classificationEXButton.setText(getString(R.string.typeNameText_ex_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void memberPicker() {
        PopupMenu popup = new PopupMenu(this, memberButton);
        popup.getMenuInflater().inflate(R.menu.list_for_member, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.member_self:
                        memberButton.setText(getString(R.string.memberName_self));
                        break;
                    case R.id.member_woman:
                        memberButton.setText(getString(R.string.memberName_woman));
                        break;
                    case R.id.member_parent:
                        memberButton.setText(getString(R.string.memberName_parent));
                        break;
                    case R.id.member_child:
                        memberButton.setText(getString(R.string.memberName_child));
                        break;
                    case R.id.member_all:
                        memberButton.setText(getString(R.string.memberName_all));
                        break;
                    case R.id.member_other:
                        memberButton.setText(getString(R.string.memberName_other));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    //---------------------一般菜單選項(轉帳)------------------------

    public void accountStartPicker() {
        PopupMenu popup = new PopupMenu(this, accountStartButton);
        popup.getMenuInflater().inflate(R.menu.list_for_account, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ac_money:
                        accountStartButton.setText(getString(R.string.init_account_money_text));
                        break;
                    case R.id.ac_bank:
                        accountStartButton.setText(getString(R.string.init_account_bank_text));
                        break;
                    case R.id.ac_card:
                        accountStartButton.setText(getString(R.string.init_account_card_text));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    public void accountEndPicker() {
        PopupMenu popup = new PopupMenu(this, accountEndButton);
        popup.getMenuInflater().inflate(R.menu.list_for_account, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ac_money:
                        accountEndButton.setText(getString(R.string.init_account_money_text));
                        break;
                    case R.id.ac_bank:
                        accountEndButton.setText(getString(R.string.init_account_bank_text));
                        break;
                    case R.id.ac_card:
                        accountEndButton.setText(getString(R.string.init_account_card_text));
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    //----------------------取得RadioGroup選擇----------------------

    public void getDataType() {
        switch (DataTypeGroup.getCheckedRadioButtonId()) {
            case R.id.home_add_income:
                dataType = getString(R.string.home_add_incomeText);
                break;
            case R.id.home_add_expend:
                dataType = getString(R.string.home_add_expendText);
                break;
            case R.id.home_add_trans:
                dataType = getString(R.string.home_add_transText);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        getDataType();
        if (radioGroup == DataTypeGroup) {
            if (DataTypeGroup.getCheckedRadioButtonId() == R.id.home_add_trans) {
                ((LinearLayout) findViewById(R.id.home_add_accountINSingleLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_accountEXSingleLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_classificationINLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_classificationEXLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_memberLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_accountStartLayout)).setVisibility(View.VISIBLE);
            } else if (DataTypeGroup.getCheckedRadioButtonId() == R.id.home_add_income) {
                ((LinearLayout) findViewById(R.id.home_add_accountINSingleLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_classificationINLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_memberLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_accountEXSingleLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_classificationEXLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_accountStartLayout)).setVisibility(View.GONE);
            } else if (DataTypeGroup.getCheckedRadioButtonId() == R.id.home_add_expend) {
                ((LinearLayout) findViewById(R.id.home_add_accountEXSingleLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_classificationEXLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_memberLayout)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.home_add_accountINSingleLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_classificationINLayout)).setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.home_add_accountStartLayout)).setVisibility(View.GONE);
            }
        }
    }

    //---------------------------關閉鍵盤---------------------------

    public void closeKeyBoard(View v) {
        //(收起鍵盤) 取消焦點(收起輸入法)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void autoClickView(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 10;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.home_add_page) {
            autoClickView(view, 100, 100);
        }
        return true;
    }

}
