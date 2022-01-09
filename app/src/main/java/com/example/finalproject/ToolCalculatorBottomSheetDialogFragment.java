package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ToolCalculatorBottomSheetDialogFragment  extends BottomSheetDialogFragment
        implements View.OnClickListener {

    //todo 將數值轉為BidDecimal

    //數字顯示
    TextView showResult;
    String nowShowNumber = "0.0";
    //基本運算
    TextView showSign;
    String nowShowSign = "";
    int funct = 0;//紀錄符號  1: add  2: sub  3: mul  4: div
    boolean number1HasUsed = false;
    boolean SignHasUsed = false;
    double number1 = 0.0; //>0 && < 100 0000
    double number2 = 0.0; //>0 && < 100 0000
    double result = 0.0;
    double lastResult = 0.0;

    //-----------------------跨域傳值-----------------------

    public InterfaceCommunicator interfaceCommunicator;

    public interface InterfaceCommunicator {
        void sendValue(String value);
    }

    private SubpageHomeAdd mCallback;

    @Override
    public void onAttach(Activity activity) {
        //連結來源Activity取得實作介面
        super.onAttach(activity);
        try {
            mCallback = (SubpageHomeAdd) activity;
        }
        catch (ClassCastException e) {
            DevToolDebug.catchException(e);
            System.out.println("MyDialog"+"Activity doesn't implement the ISelectedData interface");
        }
    }

    //-----------------------畫面呈現-----------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tool_bottomsheet_calculator, container, false);

        showSign = (TextView) view.findViewById(R.id.cal_showSign);
        showResult = (TextView) view.findViewById(R.id.cal_showResult);
        int[] listenImageButtonList = {R.id.cal_btn_0,R.id.cal_btn_1,R.id.cal_btn_2,R.id.cal_btn_3,R.id.cal_btn_4, R.id.cal_btn_5,R.id.cal_btn_6,R.id.cal_btn_7,R.id.cal_btn_8,R.id.cal_btn_9, R.id.cal_btn_add,R.id.cal_btn_sub,R.id.cal_btn_mul,R.id.cal_btn_div,R.id.cal_btn_equ,R.id.cal_btn_clear};
        for(int id:listenImageButtonList){ ((ImageButton)view.findViewById(id)).setOnClickListener(this); }

        ((Button)view.findViewById(R.id.cal_btn_submit)).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        nowShowNumber = showResult.getText().toString();
        nowShowSign = showSign.getText().toString();
        try {
            switch (view.getId()){
                case R.id.cal_btn_0: pressNumberButton(0.0);break;
                case R.id.cal_btn_1: pressNumberButton(1.0);break;
                case R.id.cal_btn_2: pressNumberButton(2.0);break;
                case R.id.cal_btn_3: pressNumberButton(3.0);break;
                case R.id.cal_btn_4: pressNumberButton(4.0);break;
                case R.id.cal_btn_5: pressNumberButton(5.0);break;
                case R.id.cal_btn_6: pressNumberButton(6.0);break;
                case R.id.cal_btn_7: pressNumberButton(7.0);break;
                case R.id.cal_btn_8: pressNumberButton(8.0);break;
                case R.id.cal_btn_9: pressNumberButton(9.0);break;
                case R.id.cal_btn_add: pressSignButton(1);break;
                case R.id.cal_btn_sub: pressSignButton(2);break;
                case R.id.cal_btn_mul: pressSignButton(3);break;
                case R.id.cal_btn_div: pressSignButton(4);break;
                case R.id.cal_btn_equ: pressEqualButton();break;
                case R.id.cal_btn_clear: resetValue();break;
                case R.id.cal_btn_submit: submitValue();break;
                default:
                    throw new IllegalStateException("Unexpected value \"onClick view.getId()\": " + view.getId());
            }
        } catch (Exception e){
            getException(e);
        }
        showResult.setText(nowShowNumber);
        showSign.setText(nowShowSign);
        /* Debug
        System.out.println("number1 :" +number1);
        System.out.println("number2 :" +number2);
        System.out.println("result :" +result);
        System.out.println("show :" +nowShowNumber);
        System.out.println("sign :" +nowShowSign);*/
    }

    //-----------------------計算功能-----------------------

    //數字鍵
    public void pressNumberButton(double pressNumber){
        try {
            if(!number1HasUsed){//number1不存在(第一輪運算未按下符號鍵)
                // 更新number1
                number1 = number1*10 + pressNumber;
                if(checkValueNotOverflow(number1)){
                    nowShowNumber = String.valueOf(number1);
                } else {
                    throw new IllegalStateException("Unexpected value \"pressNumberButton number1\": " + number1);
                }
            } else {//number1存在
                if(number1HasUsed&&SignHasUsed){//number1存在、運算符號存在
                    //更新number2
                    number2 = number2*10 + pressNumber;
                    //清空欄位並顯示number2
                    if(checkValueNotOverflow(number2)){
                        nowShowNumber = String.valueOf(number2);
                    } else {
                        throw new IllegalStateException("Unexpected value \"pressNumberButton number2\": " + number2);
                    }
                } else { //!SignHasUsed
                    // 第二輪運算未按下符號鍵 直接清空重新運算
                    resetValue();
                    // 更新number1
                    number1 = number1*10 + pressNumber;
                    if(checkValueNotOverflow(number1)){
                        nowShowNumber = String.valueOf(number1);
                    } else {
                        throw new IllegalStateException("Unexpected value \"pressNumberButton number1\": " + number1);
                    }
                }
            }
        } catch (Exception e){
            getException(e);
        }
    }

    //符號鍵
    public void pressSignButton(int pressFunct){
        try {
            number1HasUsed = SignHasUsed = true;
            funct = pressFunct;
            //下一次按數字鍵更新畫面
            switch (funct) {
                case 1: nowShowSign = "+";break;
                case 2: nowShowSign = "-";break;
                case 3: nowShowSign = "×";break;
                case 4: nowShowSign = "÷";break;
                default:
                    throw new IllegalStateException("Unexpected value \"funct\": " + funct);
            }
        } catch (Exception e){
            getException(e);
        }
    }

    //等號:執行運算
    public void pressEqualButton(){
        if(number1HasUsed){//number1存在
            try {
                //存第二個數字
                //number2 = Double.parseDouble(nowShowNumber);
                //運算
                switch(funct){
                    case 1: result = number1 + number2;break;
                    case 2: result = number1 - number2;break;
                    case 3: result = number1 * number2;break;
                    case 4: result = number1 / number2;break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + funct);
                }
                if(checkValueNotOverflow(result)){
                    //結果正常 第一輪計算結束
                    nowShowNumber = String.valueOf(result);
                    lastResult = result;
                    resetValue();
                    number1 = lastResult;
                    number1HasUsed = true;
                    SignHasUsed = false;
                    nowShowNumber = String.valueOf(lastResult);
                } else {
                    throw new IllegalStateException("Unexpected value \"result\": " + result);
                }
            } catch (Exception e){
                getException(e);
            }
        } else {//直接以number1作為結果
            try {
                //number1 = Double.parseDouble(nowShowNumber);
                result = number1;
                //第一輪計算結束
                nowShowNumber = String.valueOf(result);
                lastResult = result;
                //以第一輪結果作為下一輪number1 可直接進行下一輪計算
                resetValue();
                number1 = lastResult;
                number1HasUsed = true;
                SignHasUsed = false;
            } catch (Exception e){
                getException(e);
            }
        }
    }

    //檢查數值是否 >0 且 < 100 0000
    public boolean checkValueNotOverflow(double value){
        if(value>1000000.0 || value<0.0){//結果異常
            Toast.makeText(getActivity(), getString(R.string.cal_numberOverflow_msg), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    //歸零
    public void resetValue(){
        number1 = number2 = result = 0.0; //lastResult不清除
        funct = 0;
        nowShowSign = "";
        nowShowNumber = "0.0";
        number1HasUsed = SignHasUsed = false;
    }

    //提交
    public void submitValue() {
        try{
            //檢查結果是否可正常轉換為Double
            double DnowShowNumber = Double.parseDouble(nowShowNumber);
            //傳遞結果回Activity
            mCallback.sendValue(nowShowNumber);
            dismiss();
        }catch (Exception e){
            getException(e);
        }
    }

    //除錯用
    public void getException(Exception e){
        resetValue();
        Toast.makeText(getActivity(), getString(R.string.cal_errorSolve_msg), Toast.LENGTH_SHORT).show();
        DevToolDebug.catchException(e);
    }

}
