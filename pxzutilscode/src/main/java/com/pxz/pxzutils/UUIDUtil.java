package com.pxz.pxzutils;

import java.util.Random;
import java.util.UUID;

/**
 * 类说明：随机数
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 13:28
 */
public class UUIDUtil {
    /**
     * 生成uuid
     *
     * @return 返回uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 格式化uuid
     *
     * @return 返回格式化后的uuid
     */
    public static String getUUIDFormat(String uuid) {
        return uuid.replace("-", "");
    }

    /**
     * 格式化uuid
     *
     * @return 返回格式化后的uuid
     */
    public static String getUUIDFormat() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 随机数生成（min-max）
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomNumber(final int min, final int max) {
        if (min > max) {
            return 0;
        }
        Random rand = new Random();
        int tmp = Math.abs(rand.nextInt());
        return (tmp % (max - min + 1) + min);
    }
}