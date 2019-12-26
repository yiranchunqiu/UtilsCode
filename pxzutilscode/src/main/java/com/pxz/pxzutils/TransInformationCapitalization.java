package com.pxz.pxzutils;

import android.text.method.ReplacementTransformationMethod;

/**
 * 类说明：默认输入小写字母自动转换成大写
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/26 13:43
 */
public class TransInformationCapitalization extends ReplacementTransformationMethod {
    /**
     * 原本输入的小写字母
     */
    @Override
    protected char[] getOriginal() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    /**
     * 替代为大写字母
     */
    @Override
    protected char[] getReplacement() {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }
}