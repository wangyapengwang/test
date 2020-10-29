package com.wyp.wordDemo;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Class ImportWord
 * PackageName com.wyp.wordDemo
 * DATE 2020/8/3 15:53
 * Describe
 */
public class ImportWord {


    public static void main(String[] args) {
        //doc文档路径
        String filePath = "E:\\test.docx";
        //test.print(filePath,"第一个表");

        //System.out.println(ImportWord.read(filePath,"第一个表"));

        testWord(filePath);
    }

    public static String read(String filePath,String tableName) {
        StringBuilder sb=new StringBuilder();


        try (FileInputStream in = new FileInputStream(filePath); // 载入文档
             POIFSFileSystem pfs = new POIFSFileSystem(in);
             HWPFDocument hwpf = new HWPFDocument(pfs);) {

            Range range = hwpf.getRange();// 得到文档的读取范围
            TableIterator it = new TableIterator(range);
            // 迭代文档中的表格

            while (it.hasNext()) {
                Table tb = (Table) it.next();

                // 迭代行，默认从0开始,可以依据需要设置i的值,改变起始行数，也可设置读取到那行，只需修改循环的判断条件即可
                outer:for (int i = 0; i < tb.numRows(); i++) {
                    TableRow tr = tb.getRow(i);
                    // 迭代列，默认从0开始
                    for (int j = 0; j < tr.numCells(); j++) {
                        TableCell td = tr.getCell(j);// 取得单元格
                        // 取得单元格的内容
                        for (int k = 0; k < td.numParagraphs(); k++) {
                            Paragraph para = td.getParagraph(k);
                            String s = para.text();
                            // 去除后面的特殊符号
                            if (null != s && !"".equals(s)) {
                                s = s.substring(0, s.length() - 1);
                            }
                            s=s.trim();
                            if(tableName.trim().equals(s)||i!=0) {
                                sb.append(s + "\t");
                            } else {
                                break outer;
                            }
                        }
                    }
                    sb.append( "\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void testWord(String filePath){
        try{
            FileInputStream in = new FileInputStream(filePath);//载入文档 //如果是office2007  docx格式
            if(filePath.toLowerCase().endsWith("docx")){
                //word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
                XWPFDocument xwpf = new XWPFDocument(in);//得到word文档的信息
//             List<XWPFParagraph> listParagraphs = xwpf.getParagraphs();//得到段落信息
                Iterator<XWPFTable> it = xwpf.getTablesIterator();//得到word中的表格

                while(it.hasNext()){

                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows=table.getRows();
                    //读取每一行数据
                    for (int i = 1; i < rows.size(); i++) {
                        XWPFTableRow  row = rows.get(i);
                        //读取每一列数据
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (int j = 0; j < cells.size(); j++) {
                            XWPFTableCell cell=cells.get(j);
                            //输出当前的单元格的数据
                            System.out.println(cell.getText());
                        }
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


}
