package com.wyp.zipDemo;

import java.io.*;
import java.util.zip.*;

/**
 * Class CopyOfMyzipDecompressing
 * PackageName com.wyp.zipDemo
 * DATE 2019/10/26 10:04
 * Describe
 */
public class CopyOfMyzipDecompressing {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        long startTime = System.currentTimeMillis();
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(
                    "E:\\xxx.zip"));//输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = "E:\\xxx"; //输出路径（文件夹目录）
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("解压缩完成，耗费时间： " + (endTime - startTime) + " ms");
    }


}
