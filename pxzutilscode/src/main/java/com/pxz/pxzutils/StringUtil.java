package com.pxz.pxzutils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ScaleXSpan;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 类说明：字符串处理
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/6/5 15:39
 */
public class StringUtil {
    /**
     * 大于长度i后面加省略号
     *
     * @param str 判断长度的字符串
     * @param i   长度
     * @return 返回的字符串
     */
    public static String ellipsis(String str, int i) {
        if (str.length() >= i) {
            String string = str.substring(0, i - 1) + "…";
            return string;
        } else {
            return str;
        }
    }

    /**
     * 去除string中所有空格
     *
     * @param str 字符串
     * @return 去除空格的字符串
     */
    public static String replaceBlankAll(String str) {
        String str2;
        if (null != str && !str.equals("")) {
            str2 = str.replaceAll(" ", "");
        } else {
            str2 = "";
        }
        return str2;
    }

    /**
     * 文字两端对齐
     *
     * @param str  要对齐的文字
     * @param size 要对齐的长度
     * @return 返回的字符串
     */
    public static SpannableStringBuilder justifyString(String str, int size) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (TextUtils.isEmpty(str)) {
            return spannableStringBuilder;
        }
        char[] chars = str.toCharArray();
        if (chars.length >= size || chars.length == 1) {
            return spannableStringBuilder.append(str);
        }
        int l = chars.length;
        float scale = (float) (size - l) / (l - 1);
        for (int i = 0; i < l; i++) {
            spannableStringBuilder.append(chars[i]);
            if (i != l - 1) {
                SpannableString s = new SpannableString("　");
                s.setSpan(new ScaleXSpan(scale), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append(s);
            }
        }
        return spannableStringBuilder;
    }

    /**
     * 格式化手机号（111****1234）
     *
     * @param string 格式化的字符串
     * @return 格式化后的字符串
     */
    public static String isStringPhone(String string) {
        String str = string.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return str;
    }

    /**
     * 格式化银行卡号（1111 1111 1111）
     *
     * @param string 格式化的字符串
     * @return 格式化后的字符串
     */
    public static String isStringBankCard(String string) {
        StringBuilder sb = new StringBuilder(string);
        int length = string.length() / 4 + string.length();
        for (int i = 0; i < length; i++) {
            if (i % 5 == 0) {
                sb.insert(i, " ");
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * 字符串截取成string列表(通过s截取指定字符串)
     *
     * @param str 需要截取的字符串
     * @param s   标志
     * @return string列表
     */
    public static List<String> stringListIntercept(String str, String s) {
        List<String> strings;
        String[] array = str.split(s);
        strings = Arrays.asList(array);
        return strings;
    }

    /**
     * 字符串截取成string数组(通过s截取指定字符串)
     *
     * @param str 需要截取的字符串
     * @param s   标志
     * @return string数组
     */
    public static String[] stringIntercept(String str, String s) {
        String[] array = str.split(s);
        return array;
    }

    /**
     * 取出字符串中大写字母
     *
     * @param s 字符串
     * @return 大写字母组成的字符串
     */
    public static String stringLetterString(String s) {
        String s1 = "";
        if (null != s) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int y = (int) chars[i];
                if ((y >= 65 && y <= 90)) {
                    s1 = s1 + chars[i];
                }
            }
        }
        return s1;
    }

    /**
     * 取出字符串中数字
     *
     * @param s 字符串
     * @return 数字组成的字符串
     */
    public static String stringNumberString(String s) {
        String s1 = "";
        if (null != s) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                int y = (int) chars[i];
                if ((y >= 48 && y <= 57)) {
                    s1 = s1 + chars[i];
                }
            }
        }
        return s1;
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param s1 字符串
     * @param s2 字符串
     * @return 是否相等
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) {
            return true;
        }
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两个字符串忽略大小写是否相等
     *
     * @param s1 字符串
     * @param s2 字符串
     * @return 是否相等
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 如果字符串为null，将它转换成""空字符串
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串的长度
     *
     * @param s 字符串
     * @return 字符串的长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 需要首字母大写的字符串
     * @return 首字母大写的字符串
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (!Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 需要首字母小写的字符串
     * @return 首字母小写的字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (!Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String reverse(final String s) {
        if (s == null) {
            return "";
        }
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 去除string中的html标签
     *
     * @param inputString string
     * @return 文字
     */
    public static String Html2Text(String inputString) {
        //含html标签的字符串
        String htmlStr = inputString;
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            //定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            //过滤script标签
            htmlStr = m_script.replaceAll("");
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            //过滤style标签
            htmlStr = m_style.replaceAll("");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            //过滤html标签
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {
        }
        //返回文本字符串
        return textStr;
    }
}