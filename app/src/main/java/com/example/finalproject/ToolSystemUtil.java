package com.example.finalproject;

import java.util.Locale;

public class ToolSystemUtil {

    //當前手機系統語言
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }
    //當前系統上的語言列表
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }
    //手機系統版本號
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    //手機型號
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    //手機廠商
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

//    //獲取手機IMEI
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }

}
