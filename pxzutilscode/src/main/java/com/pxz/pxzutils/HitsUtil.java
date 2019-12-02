package com.pxz.pxzutils;

import android.os.SystemClock;
import android.view.View;

import java.util.Calendar;

/**
 * 类说明：点击
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 14:42
 */
public abstract class HitsUtil implements View.OnClickListener {
    /**
     * 开始默认值
     */
    private static long lastClick = 0L;
    /**
     * 2秒时间（判断点击间隔时间）
     */
    private static final int THRESHOLD = 2000;
    /**
     * 默认值
     */
    private static long lastClickTime = 0L;
    /**
     * 1秒时间（判断点击间隔时间）
     */
    private static final int MIN_CLICK_DELAY_TIME = 1000;

    /**
     * 双击退出检测(2秒内)
     *
     * @return 是否两次点击
     */
    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - lastClick < THRESHOLD;
        lastClick = now;
        return b;
    }

    /**
     * 多次点击判断
     *
     * @param duration 规定有效时间
     * @param mHits    数组 long[] mHits = new long[5];  5:点击次数
     * @return 是否连续点击五次
     */
    public static boolean exitAfterMany(Long duration, long[] mHits) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if ((mHits[mHits.length - 1] - mHits[0] <= duration)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 防止两次点击
     *
     * @param v 控件
     */
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    /**
     * 实现
     *
     * @param v 控件
     */
    protected abstract void onNoDoubleClick(View v);
}