package com.pxz.pxzutils;

import android.app.Activity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 类说明：activity控制器
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 13:25
 */
public class ActivityManagerUtil {
    /**
     * 管理activity
     */
    private static List<Activity> mActivitys = Collections.synchronizedList(new LinkedList<Activity>());

    /**
     * 添加activity
     *
     * @param activity 添加的activity
     */
    public static void pushActivity(Activity activity) {
        mActivitys.add(activity);
    }

    /**
     * 移除activity
     *
     * @param activity 移除的activity
     */
    public static void popActivity(Activity activity) {
        mActivitys.remove(activity);
    }

    /**
     * 返回当前activity
     *
     * @return 当前activity
     */
    public static Activity currentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return null;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        return activity;
    }

    /**
     * 结束当前activity
     */
    public static void finishCurrentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的activity
     *
     * @param activity 指定的activity
     */
    public static void finishActivity(Activity activity) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类型的activity
     *
     * @param cls 指定的activity
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        for (int i = 0; i < mActivitys.size(); i++) {
            if (mActivitys.get(i).getClass().equals(cls)) {
                finishActivity(mActivitys.get(i));
            }
        }
    }

    /**
     * 结束所有activity
     */
    public static void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (int i = 0; i < mActivitys.size(); i++) {
            mActivitys.get(i).finish();
        }
        mActivitys.clear();
    }

    /**
     * 返回指定activity(主要返回首页)
     */
    public static void returnSpecifyActivity(Class<?> cls) {
        if (mActivitys == null) {
            return;
        }
        for (int i = 0; i < mActivitys.size(); i++) {
            if (!mActivitys.get(i).getClass().equals(cls)) {
                mActivitys.get(i).finish();
            }
        }
        mActivitys.clear();
    }

    /**
     * 退出程序
     */
    public static void appExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}