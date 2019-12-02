package com.pxz.pxzutils;

import java.math.BigDecimal;

/**
 * 类说明：金钱计算
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 13:48
 */
public class MoneyCalculationUtil {
    /**
     * 相加(a1+b1)
     * @param a1 参数一
     * @param b1 参数二
     * @return 返回值
     */
    public static double add(double a1, double b1) {
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.add(b2).doubleValue();
    }

    /**
     * 相减(a1-b1)
     * @param a1 参数一
     * @param b1 参数二
     * @return 返回值
     */
    public static double sub(double a1, double b1) {
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.subtract(b2).doubleValue();
    }

    /**
     * 相乘(a1*b1)
     * @param a1 参数一
     * @param b1 参数二
     * @return 返回值
     */
    public static double mul(double a1, double b1) {
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.multiply(b2).doubleValue();
    }

    /**
     * 相除（a1/b1）
     * @param a1 被除数
     * @param b1 除数
     * @param scale 除不尽精度
     * @return 返回值
     */
    public static double div(double a1, double b1, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("error");
        }
        BigDecimal a2 = new BigDecimal(Double.toString(a1));
        BigDecimal b2 = new BigDecimal(Double.toString(b1));
        return a2.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}