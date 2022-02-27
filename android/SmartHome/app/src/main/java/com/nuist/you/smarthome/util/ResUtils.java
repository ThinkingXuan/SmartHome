package com.nuist.you.smarthome.util;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * created by youxuan
 * on 2020/4/2
 */
public class ResUtils {
    public static int getColor(Context context,@ColorRes int colorRes) {
        return ContextCompat.getColor(context,colorRes);
    }
}
