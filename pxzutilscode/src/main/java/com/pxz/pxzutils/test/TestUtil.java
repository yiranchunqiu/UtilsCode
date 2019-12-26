package com.pxz.pxzutils.test;

import com.pxz.pxzutils.EncryptionUtil;

/**
 * 类说明：测试工具类
 * 联系：530927342@qq.com
 *
 * @author peixianzhong
 * @date 2019/12/26 10:17
 */
public class TestUtil {
    public static void main(String[] args) {
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
}