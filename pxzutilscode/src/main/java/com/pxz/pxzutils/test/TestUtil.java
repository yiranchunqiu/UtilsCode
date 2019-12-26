package com.pxz.pxzutils.test;

import com.pxz.pxzutils.EncryptionUtil;
import com.pxz.pxzutils.StringUtil;
import com.pxz.pxzutils.UUIDUtil;

/**
 * 类说明：测试工具类
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/26 10:17
 */
public class TestUtil {
    public static void main(String[] args) {
        encryptionUtil();
        uUIDUtil();
        stringUtil();
    }

    /**
     * 加密工具类测试
     */
    public static void encryptionUtil(){
        /**
         * 第一种
         */
        String content = "abc123456";
        String password = "12345678312321323213213213213213213213123213213213213213213213213213211321321312321321321321321321";
        //加密
        System.out.println("加密前：" + content);
        String encryptResultStr = EncryptionUtil.encrypt(content, password);
        System.out.println("AES加密后：" + encryptResultStr);
        //解密
        String decryptResult = EncryptionUtil.decrypt(encryptResultStr, password);
        System.out.println("AES解密后：" + decryptResult);
        //md5加密
        String MD5Result = EncryptionUtil.string2MD5(content);
        System.out.println("MD5加密后：" + MD5Result);
    }

    /**
     * UUID工具类测试
     */
    public static void uUIDUtil(){
        String getUUIDString= UUIDUtil.getUUID();
        String getUUIDFormatString=UUIDUtil.getUUIDFormat(getUUIDString);
        String getUUIDFormatString1=UUIDUtil.getUUIDFormat();
        int randomNumber=UUIDUtil.randomNumber(10,20);
        System.out.println("uuid：" + getUUIDString);
        System.out.println("格式化之后的uuid：" + getUUIDFormatString);
        System.out.println("格式化之后的uuid：" + getUUIDFormatString1);
        System.out.println("随机数：" + randomNumber);
    }

    /**
     * 字符串处理工具类测试
     */
    public static void stringUtil(){
        String a=null;
        String b="123456789087";
        String c="";
        String d="12 3456 78 9087";
        String e="12345678";
        String test0= StringUtil.ellipsis(b,20);
        String test1= StringUtil.ellipsis(b,12);
        String test2= StringUtil.ellipsis(b,5);
        String test3= StringUtil.ellipsis(a,5);
        String test4= StringUtil.ellipsis(c,5);
        String test5= StringUtil.ellipsis(d,5);
        System.out.println("test0：" + test0);
        System.out.println("test1：" + test1);
        System.out.println("test2：" + test2);
        System.out.println("test3：" + test3);
        System.out.println("test4：" + test4);
        System.out.println("test5：" + test5);
        String test6= StringUtil.replaceBlankAll(a);
        String test7= StringUtil.replaceBlankAll(b);
        String test8= StringUtil.replaceBlankAll(c);
        String test9= StringUtil.replaceBlankAll(d);
        System.out.println("test6：" + test6);
        System.out.println("test7：" + test7);
        System.out.println("test8：" + test8);
        System.out.println("test9：" + test9);
//        String test10= StringUtil.justifyString(a,5).toString();
//        String test11= StringUtil.justifyString(b,5).toString();
//        String test12= StringUtil.justifyString(b,25).toString();
//        String test13= StringUtil.justifyString(c,5).toString();
//        String test14= StringUtil.justifyString(d,5).toString();
//        String test15= StringUtil.justifyString(d,25).toString();
//        System.out.println("test10：" + test10);
//        System.out.println("test11：" + test11);
//        System.out.println("test12：" + test12);
//        System.out.println("test13：" + test13);
//        System.out.println("test14：" + test14);
//        System.out.println("test15：" + test15);
        String test16= StringUtil.isStringPhone(a);
        String test17= StringUtil.isStringPhone(b);
        String test18= StringUtil.isStringPhone(c);
        String test19= StringUtil.isStringPhone(d);
        String test20= StringUtil.isStringPhone(e);
        System.out.println("test16：" + test16);
        System.out.println("test17：" + test17);
        System.out.println("test18：" + test18);
        System.out.println("test19：" + test19);
        System.out.println("test20：" + test20);
//        String test21= StringUtil.isStringBankCard(a);
//        String test22= StringUtil.isStringBankCard(b);
//        String test23= StringUtil.isStringBankCard(c);
//        String test24= StringUtil.isStringBankCard(d);
//        String test25= StringUtil.isStringBankCard(e);
//        System.out.println("test21：" + test21);
//        System.out.println("test22：" + test22);
//        System.out.println("test23：" + test23);
//        System.out.println("test24：" + test24);
//        System.out.println("test25：" + test25);
    }
}