package com.pxz.pxzutils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 类说明：震动
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 14:40
 */
public class VibratorUtil {
    /**
     * 震动
     *
     * @param context      上下文
     * @param milliseconds 震动时长，单位是毫秒
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 震动
     *
     * @param context  上下文
     * @param pattern  自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * @param isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void Vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}