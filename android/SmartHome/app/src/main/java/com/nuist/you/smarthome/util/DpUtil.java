package com.nuist.you.smarthome.util;

import android.content.Context;

public class DpUtil {
    /**
     * dp转px
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density+0.5f);
    }

    /**
     * px转dp
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
