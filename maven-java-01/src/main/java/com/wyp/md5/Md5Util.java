package com.wyp.md5;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * Class Md5Util
 * PackageName com.wyp.md5
 * DATE 2019/10/29 11:15
 * Describe
 */
public class Md5Util {

    @Test
    public void md5(){
        String passWord = DigestUtils.md5Hex("盐" + "123456");
        System.out.println(passWord);
    }


    @Test
    public void jieMd5(){
        String passWord = DigestUtils.md5Hex("盐" + "970d52d48082f3fb0c59d5611d25ec1e".trim());
        System.out.println(passWord);
    }

}
