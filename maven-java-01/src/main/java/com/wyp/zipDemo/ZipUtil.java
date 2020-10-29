package com.wyp.zipDemo;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩工具类，包含 压缩/解压 文件或文件夹
 * <p>
 * Class ZipUtil
 * PackageName com.wyp.zipDemo
 * DATE 2019/11/4 18:52
 * Describe
 */
public class ZipUtil {


    public static int k = 1; // 定义递归次数变量


    /**
     * 压缩文件
     *
     * @param zipFileName 输出文件路径 例如：E:\xxx.zip
     * @param inputFile   输入文件路径 例如：E:\MyQRCode.png
     * @throws Exception
     */
    public static void zip(String zipFileName, File inputFile) throws Exception {
        Long timeStart = System.currentTimeMillis();
        System.out.println("压缩中...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zip(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close(); // 输出流关闭
        Long timeEnd = System.currentTimeMillis() - timeStart;
        System.out.println("压缩完成! 耗时" + timeEnd + "ms");
    }

    /**
     * 压缩文件 建议使用上面的方法
     *
     * @param out  输出压缩流
     * @param f    输入文件
     * @param base 文件名
     * @param bo   字节输出流
     * @throws Exception
     */
    public static void zip(ZipOutputStream out, File f, String base,
                           BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()) {
            //压缩文件夹
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
                System.out.println("压缩文件名：" + base + "/");
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
            System.out.println("第" + k + "个文件");
            k++;
        } else {
            //压缩文件
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            System.out.println("压缩文件名：" + base);
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }


    /**
     * 解压缩
     *
     * @param zipPath 例如 ：E:\xxx.zip
     * @param outPath 例如 ：E:\xxxxx
     */
    public static void unZip(String zipPath, String outPath) {
        long startTime = System.currentTimeMillis();
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(
                    zipPath));//输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = outPath; //输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry;
            try {
                while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                    Fout = new File(Parent, entry.getName());
                    if (!Fout.exists()) {
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream Bout = new BufferedOutputStream(out);
                    int b;
                    while ((b = Bin.read()) != -1) {
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    System.out.println(Fout + "解压成功");
                }
                Bin.close();
                Zin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("解压缩完成，耗费时间： " + (endTime - startTime) + " ms");
    }

     /*public static void main(String[] args) {
        try {
            ZipUtil.zip("E:\\xxx.zip", new File("E:\\www"));
            //ZipUtil.unZip("E:\\xxx.zip","E:\\xxxxx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
