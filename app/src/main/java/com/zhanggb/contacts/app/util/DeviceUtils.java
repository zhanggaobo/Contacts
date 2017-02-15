package com.zhanggb.contacts.app.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author zhanggaobo
 * @since 11/01/2016
 */
public class DeviceUtils {

    public static int getPx(Context context, int dp) {
        float deviceDs = 0;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        deviceDs = dm.density;
        return (int) (dp * deviceDs);
    }
}
