package com.nuist.you.smarthome.util;

import android.app.ProgressDialog;
import android.content.Context;

//Dialog封装类
public class DialogUtil {

    /**
     * 只带message
     * @param context
     * @param message
     */

    public static ProgressDialog mDialog;
    public static void showProgressDialog(Context context, String message) {

         mDialog = new ProgressDialog(context);

        if (mDialog != null) {
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setTitle("提示：");
            mDialog.setMessage(message);
            mDialog.show();
        }
    }

    public static void closeProgressDialog(){
        if (null !=mDialog){
            mDialog.cancel();
        }
    }


    /**
     *带message和title
     * @param context
     * @param title
     * @param message
     */
    public static void showProgressDialog(Context context, String title, String message) {

        ProgressDialog mDialog = new ProgressDialog(context);

        if (mDialog != null) {
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setTitle(title);
            mDialog.setMessage(message);
            mDialog.show();
        }
    }
}
