package com.wyp.wordDemo;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.FileInputStream;

/**
 * 读取word内容，无法读取经程序导出的xml格式word
 *
 * Class DocUtil
 * PackageName com.wyp.wordDemo
 * DATE 2019/10/29 13:46
 * Describe
 */
public class DocUtil {

    /*public static void main(String[] args) {
        DocUtil tp=new DocUtil();
        //.docx和doc文件的读取
        String content = tp.readWord("E:\\target.doc");
        System.out.println("content===="+content);
    }*/

    /**
     * 读取word文件内容
     *
     * @param path 文件绝对路径
     * @return buffer
     */
    public static String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                FileInputStream is = new FileInputStream(path);
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                is.close();
            } else if (path.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                opcPackage.close();
            } else {
                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
