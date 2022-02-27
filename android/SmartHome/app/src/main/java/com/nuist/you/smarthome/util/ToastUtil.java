package com.nuist.you.smarthome.util;

import android.content.Context;
import android.widget.Toast;

import com.nuist.you.smarthome.view.toast.Toasty;


public class ToastUtil {

    private static Toast sToasty;


    public static void Success(Context mContext, String content) {
        Toasty.success(mContext, content).show();
    }

    public static void Error(Context mContext, String content) {
        Toasty.error(mContext, content).show();
    }

    public static void Warning(Context mContext, String content) {
        Toasty.warning(mContext, content).show();
    }

    public static void Info(Context mContext, String content) {
        Toasty.info(mContext, content).show();
    }

    public static void Normal(Context mContext, String content) {
        Toasty.normal(mContext, content).show();
    }


}
