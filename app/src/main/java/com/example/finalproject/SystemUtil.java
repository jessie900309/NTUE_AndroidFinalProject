package com.example.finalproject;

import java.util.Locale;

/**
 * 系統工具類
 * Created by zhuwentao on 2016-07-18.
 */
public class SystemUtil {
    /**
     * 獲取當前手機系統語言。
     *
     * @return 返回當前系統語言。例如：當前設定的是“中文-中國”，則返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }
    /**
     * 獲取當前系統上的語言列表(Locale列表)
     *
     * @return  語言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }
    /**
     * 獲取當前手機系統版本號
     *
     * @return  系統版本號
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 獲取手機型號
     *
     * @return  手機型號
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 獲取手機廠商
     *
     * @return  手機廠商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }
//    /**
//     * 獲取手機IMEI(需要“android.permission.READ_PHONE_STATE”許可權)
//     *
//     * @return  手機IMEI
//     */
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }
}