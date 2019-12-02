package com.pxz.pxzutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 类说明：activity跳转工具类
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/10/16 14:55
 */
public class ActivityIntentUtil {
    /**
     * 跳转activity（有数据传输，不关闭页面，有跳转动画）
     */
    public static void startActivityNoFinishAndAnimation(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.act_right_in, R.anim.act_home);
    }

    /**
     * 跳转activity（没有数据传输，不关闭页面，有跳转动画）
     */
    public static void startActivityNoFinishAndAnimation(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.act_right_in, R.anim.act_home);
    }

    /**
     * 跳转activity(有数据传输，关闭页面，有跳转动画)
     */
    public static void startActivityAndFinishAndAnimation(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.act_right_in, R.anim.act_home);
    }

    /**
     * 跳转activity(没有数据传输，关闭页面，有跳转动画)
     */
    public static void startActivityAndFinishAndAnimation(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.act_right_in, R.anim.act_home);
    }

    /**
     * 跳转activity(有数据传输，关闭页面，没有跳转动画)
     */
    public static void startActivityAndFinishNoAnimation(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 跳转activity(没有数据传输，关闭页面，没有跳转动画)
     */
    public static void startActivityAndFinishNoAnimation(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * 跳转activity(有数据传输，不关闭页面，没有跳转动画)
     */
    public static void startActivityNoFinishNoAnimation(Activity activity, Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * 跳转activity(没有数据传输，不关闭页面，没有跳转动画)
     */
    public static void startActivityNoFinishNoAnimation(Activity activity, Class<?> targetClass) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转activity（带返回值得）（没有数据传输,没有跳转动画）
     */
    public static void startActivityForResultNoAnimation(Activity activity, Class<?> targetClass, int code) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivityForResult(intent, code);
    }

    /**
     * 跳转activity（带返回值得）（没有数据传输，有跳转动画）
     */
    public static void startActivityForResultAndAnimation(Activity activity, Class<?> targetClass, int code) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivityForResult(intent, code);
        activity.overridePendingTransition(R.anim.act_right_in, R.anim.act_home);
    }

    /**
     * 跳转activity（带返回值得）（有数据传输,有跳转动画）
     */
    public static void startActivityForResultAndAnimation(Activity activity, Class<?> targetClass, int code, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, code);
        activity.overridePendingTransition(R.anim.act_right_in, R.anim.act_home);
    }

    /**
     * 跳转activity（带返回值得）（有数据传输,没有跳转动画）
     */
    public static void startActivityForResulNoAnimation(Activity activity, Class<?> targetClass, int code, Bundle bundle) {
        Intent intent = new Intent(activity, targetClass);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, code);
    }
}