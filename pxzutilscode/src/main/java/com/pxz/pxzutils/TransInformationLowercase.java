package com.pxz.pxzutils;

import android.text.method.ReplacementTransformationMethod;

/**
 * 类说明：默认输入大写字母自动转换成小写
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/26 13:47
 */
public class TransInformationLowercase extends ReplacementTransformationMethod {
    /**
     * 原本输入的大写字母
     */
    @Override
    protected char[] getOriginal() {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }

    /**
     * 替换的小写字母
     */
    @Override
    protected char[] getReplacement() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }
}