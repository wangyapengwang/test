package com.wyp.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class ShaUtil
 * PackageName com.wyp.md5
 * DATE 2019/11/7 16:40
 * Describe
 */
public class ShaUtil {

    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }

    /**
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String msg = "郭XX-精品相声技术";
        ShaUtil sha = new ShaUtil();
        byte[] resultBytes = sha.eccrypt(msg);
        System.out.println("明文是：" + msg);
        System.out.println("密文是：" + new String(resultBytes));

    }

}
