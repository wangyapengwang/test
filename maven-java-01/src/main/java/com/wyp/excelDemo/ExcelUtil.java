package com.wyp.excelDemo;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Class ExcelUtil
 * PackageName com.wyp.excelDemo
 * DATE 2019/10/26 15:11
 * Describe
 * 使用需引入
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi</artifactId>
 * <version>3.17</version>
 * </dependency>
 * 原文链接：https://blog.csdn.net/w893932747/article/details/89354979
 */
public class ExcelUtil {


    /*@Test
    public void testEx() {
        String[] headerTitle = {"姓名", "年龄", "日期"};
        String[] headers = {"name", "age", "createDate"};
        List<Demo> list = new ArrayList<Demo>();
        list.add(new Demo("小强", new Date(), 11));
        list.add(new Demo("小大", new Date(), 13));
        list.add(new Demo("小儿", new Date(), 15));
        ExportExcel excel = new ExportExcel();
        String s = excel.create(headerTitle, headers, list, FILE_PATH);
        System.out.println(s);

    }*/

    /**
     *  创建一个默认样式的Excel
     * @param headerTitle 表格属性列名数组（标题）若为null，默认显示headers
     * @param headers 表格属性列名数组,javaBean 的属性，决定顺序
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param path 表格生成到的目录 例如 path=D:/upload
     * @return 表格生成到了哪里 例如，path=D:/upload 则生成到/file/今天日期/时刻.xls
     */
    public static String creatExcel (String[] headerTitle, String[] headers, Collection dataset, String path) {
        System.out.println("》》》》》》开始使用通用方法导出Excel表格。。。");
        long timeStare = System.currentTimeMillis();
        ExportExcel excel = new ExportExcel();
        String s = excel.create(headerTitle, headers, dataset, path);
        long timeEnd = System.currentTimeMillis() - timeStare;
        System.out.println("》》》》》》导出Excel表格耗时【" + timeEnd + "】ms。。。");
        return s;
    }

    /**
     * 自定义表头样式和内容样式创建Excel
     * @param headerTitle 表格属性列名数组（标题）若为null，默认显示headers
     * @param headers 表格属性列名数组,javaBean 的属性，决定顺序
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param path 表格生成到的目录 例如 path=D:/upload
     * @param hssfWorkbook hssfWorkbook对象
     * @param styleTableHeader 表头样式
     * @param styleContent 内容样式
     * @return 表格生成到了哪里 例如，path=D:/upload 则生成到/file/今天日期/时刻.xls
     */
    public static String creatExcel (String[] headerTitle, String[] headers, Collection dataset, String path
            , HSSFWorkbook hssfWorkbook, HSSFCellStyle styleTableHeader, HSSFCellStyle styleContent) {
        System.out.println("》》》》》》开始使用通用方法导出Excel表格。。。");
        long timeStare = System.currentTimeMillis();
        ExportExcel excel = new ExportExcel();
        String s = excel.create(headerTitle, headers, dataset, path, hssfWorkbook, styleTableHeader , styleContent);
        long timeEnd = System.currentTimeMillis() - timeStare;
        System.out.println("》》》》》》导出Excel表格耗时【" + timeEnd + "】ms。。。");
        return s;
    }

    public static HSSFWorkbook getHSSFWorkbook(){
        HSSFWorkbook workbook = new HSSFWorkbook();
        return workbook;
    }




    /***************************下面是一些操作Excel的案例，不做工具类使用！！********************/
    //读取excel内容
    public void testExcel1() {
        try {
            //获取系统文档
            POIFSFileSystem fspoi = new POIFSFileSystem(new FileInputStream("E:\\temp.xls"));
            //创建工作薄对象
            HSSFWorkbook workbook = new HSSFWorkbook(fspoi);
            //创建工作表对象
            HSSFSheet sheet = workbook.getSheet("sheet1");
            //得到Excel表格
            HSSFRow row = sheet.getRow(1);
            //得到Excel工作表指定行的单元格
            HSSFCell cell = row.getCell(1);
            System.out.println(cell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExcelUtil excelTest=new ExcelUtil();
        Workbook wb = excelTest.getExcel("E:\\temp.xls");
        if(wb==null)
            System.out.println("文件读入出错");
        else {
            excelTest.analyzeExcel(wb);
        }
    }

    public Workbook getExcel(String filePath){
        Workbook wb=null;
        File file=new File(filePath);
        if(!file.exists()){
            System.out.println("文件不存在");
            wb=null;
        }
        else {
            String fileType=filePath.substring(filePath.lastIndexOf("."));//获得后缀名
            try {
                InputStream is = new FileInputStream(filePath);
                if(".xls".equals(fileType)){
                    wb = new HSSFWorkbook(is);
                }else if(".xlsx".equals(fileType)){
                    wb = new XSSFWorkbook(is);
                }else{
                    System.out.println("格式不正确");
                    wb=null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return wb;
    }

    public void analyzeExcel(Workbook wb){
        Sheet sheet=wb.getSheetAt(0);//读取sheet(从0计数)
        int rowNum=sheet.getLastRowNum();//读取行数(从0计数)
        for(int i=1;i<=rowNum;i++){
            Row row=sheet.getRow(i);//获得行
            int colNum=row.getLastCellNum();//获得当前行的列数
            TNaNavyPeople x = new TNaNavyPeople();
            for(int j=0;j<colNum;j++){
                Cell cell=row.getCell(j);//获取单元格
                if (j == 0) {
                    x.setNavyName(cell.toString());
                }
                if (j == 1) {
                    x.setNavyWeixin(cell.toString());
                }
                if (j == 2) {
                    x.setNavyDuty(cell.toString());
                }
                if (j == 3) {
                    x.setNavyOutlook(cell.toString());
                }
                if (j == 4) {
                    x.setFillPhone(cell.toString());
                }
                if (j == 5) {
                    x.setWorkId(cell.toString());
                }
                if (j == 6) {
                    x.setNavyPerson(cell.toString());
                }
                if (j == 7) {
                    x.setFillPhone(cell.toString());
                }
                if (j == 8) {
                    String split = cell.toString().split("\\.")[0];
                    x.setNavyWorkload(Integer.valueOf(split));
                }
                if (j == 9) {
                    x.setWorkloadRemarks(cell.toString());
                }
                if (j == 10) {
                    x.setNavyRemarks(cell.toString());
                }

               /* if(cell==null)
                    System.out.print("null     ");
                else
                    System.out.print(cell.toString()+"     ");
*/
            }
            System.out.println(x);
            System.out.println();
        }
    }



    //创建Excel对象
    @Test
    public void testExcel2(String path) throws IOException {
        //创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();//这里也可以设置sheet的Name
        //创建工作表对象
        HSSFSheet sheet = workbook.createSheet();
        //创建工作表的行
        HSSFRow row = sheet.createRow(0);//设置第一行，从零开始
        row.createCell(2).setCellValue("aaaaaaaaaaaa");//第一行第三列为aaaaaaaaaaaa
        row.createCell(0).setCellValue(new Date());//第一行第一列为日期
        workbook.setSheetName(0, "sheet的Name");//设置sheet的Name
        //文档输出
        FileOutputStream out = new FileOutputStream(path + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建文档摘要信息
    @Test
    public void testExcel3() throws IOException {
        //创建HSSFWorkbook工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = workbook.createSheet("sheet1");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格,从0开始
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("a");
        //一下为简写
        row.createCell(1).setCellValue("aa");
        row.createCell(2).setCellValue("aaa");
        row.createCell(3).setCellValue("aaaa");

        //创建文档信息
        workbook.createInformationProperties();
        //获取DocumentSummaryInformation对象
        DocumentSummaryInformation documentSummaryInformation = workbook.getDocumentSummaryInformation();
        documentSummaryInformation.setCategory("类别:Excel文件");//类别
        documentSummaryInformation.setManager("管理者:王军");//管理者
        documentSummaryInformation.setCompany("公司:Action");//公司

        //文档输出
        FileOutputStream out = new FileOutputStream("E:/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建批注
    /*
     *  创建批注位置HSSFPatriarch.createAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2)方法参数说明：
     *  1、dx1 第1个单元格中x轴的偏移量
     *  2、dy1 第1个单元格中y轴的偏移量
     *  3、dx2 第2个单元格中x轴的偏移量
     *  4、dy2 第2个单元格中y轴的偏移量
     *  5、col1 第1个单元格的列号
     *  6、row1 第1个单元格的行号
     *  7、col2 第2个单元格的列号
     *  8、row2 第2个单元格的行号
     * */
    @Test
    public void testExcel4() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        //创建批注位置(row1-row3:直接理解为高度，col1-col2：直接理解为宽度)
        HSSFClientAnchor anchor = patr.createAnchor(0, 0, 0, 0, 5, 1, 8, 3);
        //创建批注
        HSSFComment comment = patr.createCellComment(anchor);
        //设置批注内容
        comment.setString(new HSSFRichTextString("这是一个批注段落！"));
        //设置批注作者
        comment.setAuthor("wangjun");
        //设置批注默认显示
        comment.setVisible(true);
        HSSFCell cell = sheet.createRow(2).createCell(1);
        cell.setCellValue("测试");
        //把批注赋值给单元格
        cell.setCellComment(comment);

        //文档输出
        FileOutputStream out = new FileOutputStream("E:/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

    //创建页眉页脚
    @Test
    public void testExcel5() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Test");// 创建工作表(Sheet)
        HSSFHeader header = sheet.getHeader();//得到页眉
        header.setLeft("页眉左边");
        header.setRight("页眉右边");
        header.setCenter("页眉中间");
        HSSFFooter footer = sheet.getFooter();//得到页脚
        footer.setLeft("页脚左边");
        footer.setRight("页脚右边");
        footer.setCenter("页脚中间");

        //文档输出
        FileOutputStream out = new FileOutputStream("E:/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }


    //Excel的单元格操作
    /*
     *   HSSFDataFormat.getFormat和HSSFDataFormat.getBuiltinFormat的区别：
     *   当使用Excel内嵌的（或者说预定义）的格式时，直接用HSSFDataFormat.getBuiltinFormat静态方法即可。当使用
     *   自己定义的格式时，必须先调用HSSFWorkbook.createDataFormat()，因为这时在底层会先找有没有匹配的内嵌
     *   FormatRecord，如果没有就会新建一个FormatRecord，所以必须先调用这个方法，然后你就可以用获得的
     *   HSSFDataFormat实例的getFormat方法了，当然相对而言这种方式比较麻烦，所以内嵌格式还是用
     *   HSSFDataFormat.getBuiltinFormat静态方法更加直接一些。
     * */
    @Test
    public void testExcel6() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //创建单元格样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);
        //设置保留2位小数--使用Excel内嵌的格式
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue(12.3456789);
        style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell1.setCellStyle(style);
        //设置货币格式--使用自定义的格式
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellValue(12345.6789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
        cell2.setCellStyle(style);
        //设置百分比格式--使用自定义的格式
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellValue(0.123456789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        cell3.setCellStyle(style);
        //设置中文大写格式--使用自定义的格式
        HSSFCell cell4 = row.createCell(4);
        cell4.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));
        cell4.setCellStyle(style);
        //设置科学计数法格式--使用自定义的格式
        HSSFCell cell5 = row.createCell(5);
        cell5.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
        cell5.setCellStyle(style);

        //文档输出
        FileOutputStream out = new FileOutputStream("E:/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }


    //合并单元格
    /*
     *   CellRangeAddress对象其实就是表示一个区域，其构造方法如下：
     *   CellRangeAddress(firstRow, lastRow, firstCol, lastCol)，参数的说明：
     *   1、firstRow 区域中第一个单元格的行号
     *   2、lastRow 区域中最后一个单元格的行号
     *   3、firstCol 区域中第一个单元格的列号
     *   4、lastCol 区域中最后一个单元格的列号
     *   即使你没有用CreateRow和CreateCell创建过行或单元格，也完全可以直接创建区域然后把这一区域合并，Excel
     *   的区域合并信息是单独存储的，和RowRecord、ColumnInfoRecord不存在直接关系。
     * */
    @Test
    public void testExcel7() throws IOException {
        //创建Excel工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建Excel工作表对象
        HSSFSheet sheet = workbook.createSheet("wj");
        //创建行的单元格，从0开始
        HSSFRow row = sheet.createRow(0);
        //创建单元格
        HSSFCell cell = row.createCell(0);
        //设置值
        cell.setCellValue(new Date());
        //合并列
        cell.setCellValue("合并列");
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region);
        //合并行
        HSSFCell cell1 = row.createCell(6);
        cell1.setCellValue("合并行");
        region = new CellRangeAddress(0, 5, 6, 6);
        sheet.addMergedRegion(region);
        //文档输出
        FileOutputStream out = new FileOutputStream("E:/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + ".xls");
        workbook.write(out);
        out.close();
    }

}
