package com.dxh.base_library_module.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by XHD on 2021/06/06
 */
public class ScreenUtil {
    /**
     * 获取屏幕数据
     */
    public static float getScreenAttribute(Activity activity, int attributeType) {
        DisplayMetrics metric = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metric);

        Configuration config = activity.getResources().getConfiguration();
        int smallestScreenWidthDp = config.smallestScreenWidthDp;

        // since SDK_INT = 1;
        int widthPixels = metric.widthPixels;
        int heightPixels = metric.heightPixels;
        try {
            // used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
            widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
            heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
        } catch (Exception e) {
            widthPixels = -1;
            heightPixels = -1;
        }
        try {
            // used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            widthPixels = realSize.x;
            heightPixels = realSize.y;
        } catch (Exception e) {
            widthPixels = -1;
            heightPixels = -1;
        }
        float value = -1;
        switch (attributeType) {
            case 1:
                value = widthPixels;          //屏幕宽度（像素）
                break;
            case 2:
                value = heightPixels;         //屏幕高度（像素）
                break;
            case 3:
                value = widthPixels;          //屏幕宽度（dp）
                value = px2dip(activity, value);
                break;
            case 4:
                value = heightPixels;         //屏幕高度（dp）
                value = px2dip(activity, value);
                break;
            case 5:
                value = metric.density;       //屏幕密度（0.75 / 1.0 / 1.5）
                break;
            case 6:
                value = metric.densityDpi;    //屏幕密度DPI（120 / 160 / 240）
                break;
            case 7:
                value = smallestScreenWidthDp;//屏幕最小宽度
                break;
        }
        return value;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);//+0.5的作用是为了适用于程序中的四舍五入
    }
}
