package com.pxz.pxzutils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类说明：
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/11/28 12:44
 */
public class RegularUtil {
    private static boolean flag = false;
    /**
     * 验证手机号码
     */
    public static final String CELL_PHONE = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    /**
     * 验证身份证
     */
    public static final String ID_CARD = "^\\d{15}$|^\\d{17}[0-9Xx]$";
    /**
     * 验证车牌号
     */
    public static final String PLATE_NUMBER = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
    /**
     * 验证固话号码
     */
    public static final String LAND_LINE = "^(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7,8})|(0\\d{2}\\d{8})|(0\\d{3}\\d{7,8})$";
    /**
     * 验证是否是邮箱
     */
    public static final String EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    /**
     * 验证是否是IP地址
     */
    public static final String IP = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 验证是否是网址Url
     */
    public static final String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 验证是否是姓名(只能输入字母和汉字)
     */
    public static final String NAME = "[a-zA-Z\\u4e00-\\u9fa5]+";
    /**
     * 验证是否是汉字(+表示一个或多个中文)
     */
    public static final String CHINESE = "[\\u4e00-\\u9fa5]+";
    /**
     * 验证是否是数字
     */
    public static final String NUMBER = "^[0-9]*$";
    /**
     * 验证是否是英文字母
     */
    public static final String ENGLISH = "[A-Z,a-z]*";

    /**
     * 判断
     *
     * @param str   字符串
     * @param regex 规则
     * @return 返回值
     */
    public static boolean isCheck(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证String字符串是否出现特定字符 （第一个参数s1中是否包含s2）
     *
     * @param s1 第一个参数
     * @param s2 二个参数
     * @return true:包含  false:不包含
     */
    public static boolean isCheckContain(String s1, String s2) {
        return s1.contains(s2);
    }

    /**
     * 验证字符串是否为null 长度为0 全是空格
     *
     * @param space 字符串
     * @return true:字符串是 null 长度为0 全是空格  false:字符串不是 null 长度为0 全是空格
     */
    public static boolean isCheckEmpty(String space) {
        if (null == space) {
            return true;
        }
        if (space.length() == 0) {
            return true;
        }
        if (space.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证为指定长度的字符串
     *
     * @param string    被验证的字符串
     * @param minLength 指定最小长度
     * @param maxLength 指定最大长度
     * @return true:是指定长度 false:不是指定长度
     */
    public static boolean isBetweenMinAndMax(String string, int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException("MinLength Or MaxLength Is Must Big Then 0");
        }
        if (minLength > maxLength) {
            throw new IllegalArgumentException("MaxLength Is Must Big Then MinLength");
        }
        String regex = ".{" + minLength + "," + maxLength + "}";
        return isCheck(string, regex);
    }

    /**
     * 验证手机号码
     *
     * @param cellphone 被验证的字符串
     * @return true:是手机号码 false:不是手机号码
     */
    public static boolean isCheckCellphone(String cellphone) {
        return isCheck(cellphone, CELL_PHONE);
    }

    /**
     * 验证身份证
     *
     * @param iDCard 被验证的字符串
     * @return true:是身份证 false:不是身份证
     */
    public static boolean isCheckIDCard(String iDCard) {
        return isCheck(iDCard, ID_CARD);
    }

    /**
     * 验证车牌号
     *
     * @param licensePlateNumber 被验证的字符串
     * @return true:是车牌号 false:不是车牌号
     */
    public static boolean isCheckLicensePlateNumber(String licensePlateNumber) {
        return isCheck(licensePlateNumber, PLATE_NUMBER);
    }

    /**
     * 验证固话号码
     *
     * @param landline 被验证的字符串
     * @return true:是固话号码 false:不是固话号码
     */
    public static boolean isCheckLandline(String landline) {
        return isCheck(landline, LAND_LINE);
    }

    /**
     * 验证是否是邮箱
     *
     * @param email 被验证的字符串
     * @return true:是邮箱 false:不是邮箱
     */
    public static boolean isCheckEmail(String email) {
        return isCheck(email, EMAIL);
    }

    /**
     * 验证是否是IP地址
     *
     * @param ip 被验证的字符串
     * @return true:是ip地址 false:不是ip地址
     */
    public static boolean isCheckIp(String ip) {
        String regex = "^" + IP + "\\." + IP + "\\." + IP + "\\." + IP + "$";
        return isCheck(ip, regex);
    }

    /**
     * 验证是否是网址Url
     *
     * @param url 被验证的字符串
     * @return true:是网址 false:不是网址
     */
    public static boolean isCheckUrl(String url) {
        return isCheck(url, URL);
    }

    /**
     * 验证是否是姓名(只能输入字母和汉字)
     *
     * @param name 被验证的字符串
     * @return true:是姓名 false:不是姓名
     */
    public static boolean isCheckName(String name) {
        return isCheck(name, NAME);
    }

    /**
     * 验证是否是汉字
     *
     * @param chinese 被验证的字符串
     * @return true:是数字 false:不是数字
     */
    public static boolean isCheckChinese(String chinese) {
        return isCheck(chinese, CHINESE);
    }

    /**
     * 验证是否是数字
     *
     * @param number 被验证的字符串
     * @return true:是数字 false:不是数字
     */
    public static boolean isCheckNumber(String number) {
        return isCheck(number, NUMBER);
    }

    /**
     * 验证是否是英文字母
     *
     * @param english 被验证的字符串
     * @return true:是字母 false:不是字母
     */
    public static boolean isCheckEnglish(String english) {
        return isCheck(english, ENGLISH);
    }

    /**
     * 验证企业组织机构
     *
     * @param code 被验证的字符串
     * @return 是:返回true 否:返回false
     */
    public static boolean isCheckOrganizationCode(String code) {
        final String[] codeNo = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};
        final String[] staVal = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
                "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
        Map map = new HashMap();
        for (int i = 0; i < codeNo.length; i++) {
            map.put(codeNo[i], staVal[i]);
        }
        final int[] wi = {3, 7, 9, 10, 5, 8, 4, 2};
        Pattern pat = Pattern.compile("^[0-9A-Z]{8}-[0-9X]$");
        Matcher matcher = pat.matcher(code);
        if (!matcher.matches()) {
            return false;
        }
        String[] all = code.split("-");
        final char[] values = all[0].toCharArray();
        int parity = 0;
        for (int i = 0; i < values.length; i++) {
            final String val = Character.toString(values[i]);
            parity += wi[i] * Integer.parseInt(map.get(val).toString());
        }
        String cheak = (11 - parity % 11) == 10 ? "X" : Integer.toString((11 - parity % 11));
        return cheak.equals(all[1]);
    }

    /**
     * 验证是否有emoji表情
     *
     * @param emoji 被验证的字符串
     * @return true:有emoji表情 false:没有emoji表情
     */
    public static boolean isCheckEmoji(String emoji) {
        int len = emoji.length();
        for (int i = 0; i < len; i++) {
            char emojiCharacter = emoji.charAt(i);
            if (!isCheckEmojiCharacter(emojiCharacter)) {
                /*如果不能匹配,则该字符是Emoji表情*/
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是emoji,比较的单个字符
     *
     * @param emojiCharacter 被验证的字符串
     * @return true:是emoji字符 false:不是emoji字符
     */
    private static boolean isCheckEmojiCharacter(char emojiCharacter) {
        return (emojiCharacter == 0x0) || (emojiCharacter == 0x9) || (emojiCharacter == 0xA) ||
                (emojiCharacter == 0xD) || ((emojiCharacter >= 0x20) && (emojiCharacter <= 0xD7FF)) ||
                ((emojiCharacter >= 0xE000) && (emojiCharacter <= 0xFFFD)) || ((emojiCharacter >= 0x10000)
                && (emojiCharacter <= 0x10FFFF));
    }
}