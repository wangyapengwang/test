package com.wyp.md5;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Class Base64Util
 * PackageName com.wyp.md5
 * DATE 2019/11/7 16:33
 * Describe
 */
public class Base64Util {

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }




}
