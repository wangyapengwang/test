package com.wyp.excelDemo;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;


/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 *
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 *            Class ExportExcel
 *            PackageName com.wyp.excelDemo
 *            DATE 2019/10/28 9:25
 *            Describe
 */
public class ExportExcel<T> {

    /**
     * 使用默认样式导出表格
     *
     * @param headerTitle 看下面
     * @param headers     看下面
     * @param dataset     看下面
     * @param path        看下面
     * @return 表格的路径
     */
    public String create(String[] headerTitle, String[] headers, Collection<T> dataset, String path) {
        try {
            HSSFWorkbook hssfWorkbook = getHSSFWorkbook();
            String pathRe = exportExcel("sheet1", headerTitle, headers, dataset, path + "//", "yyyy-MM-dd",
                    hssfWorkbook, getTableHeaderStyle(hssfWorkbook), getContentStyle(hssfWorkbook));
            return pathRe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出失败");
        }
    }

    /**
     * 自定义表头和内容样式
     *
     * @param headerTitle      看下面
     * @param headers          看下面
     * @param dataset          看下面
     * @param path             看下面
     * @param hssfWorkbook     表格 使用getHSSFWorkbook(); 生成
     * @param styleTableHeader 表头样式
     * @param styleContent     内容样式
     * @return 表格的路径
     */
    public String create(String[] headerTitle, String[] headers, Collection<T> dataset, String path
            , HSSFWorkbook hssfWorkbook, HSSFCellStyle styleTableHeader, HSSFCellStyle styleContent) {
        try {
            String pathRe = exportExcel("sheet1", headerTitle, headers, dataset, path + "//", "yyyy-MM-dd",
                    hssfWorkbook, styleTableHeader, styleContent);
            return pathRe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出失败");
        }
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title            表格标题名（sheet1）
     * @param headerTitle      表格属性列名数组（标题）若为null，默认显示headers
     * @param headers          表格属性列名数组,javaBean 的属性，决定顺序
     * @param dataset          需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                         javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param path             表格生成到的目录 例如 path=D:/upload
     * @param pattern          如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     * @param workbook         workbook对象
     * @param styleTableHeader 表头样式
     * @param styleContent     内容样式
     * @return 表格生成到了哪里 例如，path=D:/upload 则生成到/file/今天日期/时刻.xls
     */
    public String exportExcel(String title, String[] headerTitle, String[] headers, Collection<T> dataset, String path,
                              String pattern, HSSFWorkbook workbook, HSSFCellStyle styleTableHeader, HSSFCellStyle styleContent) {
        OutputStream out = null;
        String lastPath = "";
        // 声明一个工作薄
        //HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个样式---表头
        //HSSFCellStyle style = getTableHeaderStyle(workbook);
        // 生成并设置另一个样式---内容
        //HSSFCellStyle style2 = getContentStyle(workbook);
        int sheetCount = 1000;

        try {
            if (dataset.size() > sheetCount) {
                Iterator<T> it = dataset.iterator();
                for (int i = 0; i <= 4; i++) {
                    int index = 0;
                    List<T> list = new ArrayList<T>();
                    while (it.hasNext()) {
                        index++;
                        if (index < sheetCount) {
                            list.add(it.next());
                        } else {
                            break;
                        }
                    }
                    generateSheet(list, styleTableHeader, styleContent, workbook, pattern, headerTitle, headers, title + "_" + (i + 1));
                }
            } else {
                generateSheet(dataset, styleTableHeader, styleContent, workbook, pattern, headerTitle, headers, title);
            }

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File file2 = new File(path + "file" + File.separator + date);

            if (!file2.exists()) {
                file2.mkdirs();
            }

            Long time = System.currentTimeMillis();
            lastPath = path + "file" + File.separator + date + File.separator + time + ".xls";
            out = new FileOutputStream(lastPath);
            workbook.write(out);
            return lastPath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出失败");
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressWarnings({"deprecation"})
    public void generateSheet(Collection<T> dataset, HSSFCellStyle styleTableHeader, HSSFCellStyle styleContent, HSSFWorkbook workbook,
                              String pattern, String[] headerTitle, String[] headers, String title) throws Exception {
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("author");
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        String[] showTitle = headers;
        if (headerTitle != null && headerTitle.length != 0)
            showTitle = headerTitle;
        int indexTitle = 0;// excel列下标

        for (short i = 0; i < showTitle.length; i++) {
            if (showTitle[i] == null || showTitle[i] == "")
                continue;
            HSSFCell cell = row.createCell(indexTitle);
            cell.setCellStyle(styleTableHeader);
            HSSFRichTextString text = new HSSFRichTextString(showTitle[i]);
            cell.setCellValue(text);
            indexTitle++;
        }
        if (dataset == null || dataset.size() == 0)
            return;
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // Class tCls = t.getClass();
            // Method[] methods = t.getClass().getMethods();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = new Field[headers.length];
            HSSFFont font3 = workbook.createFont();
            font3.setColor(HSSFColor.BLACK.index);
            for (short j = 0; j < headers.length; j++) {
                if (headers[j] == null || headers[j] == "")
                    continue;
                Field field1 = t.getClass().getDeclaredField(headers[j]);
                fields[j] = field1;
            }
            int indexText = 0;// excel列下标
            for (short i = 0; i < fields.length; i++) {
                if (fields[i] == null || "".equals(fields[i]))
                    continue;

                Field field = fields[i];
                HSSFCell cell = row.createCell(indexText);

                cell.setCellStyle(styleContent);

                String fieldName = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, t.getClass());
                Method getMethod = pd.getReadMethod();
                Object value = getMethod.invoke(t, new Object[]{});
                // 判断值的类型后进行强制类型转换
                String textValue = null;
                if (value instanceof Boolean) {
                    boolean bValue = (Boolean) value;
                    textValue = "是";
                    if (!bValue)
                        textValue = "否";
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    textValue = sdf.format(date);
                } else if (value instanceof byte[]) {
                    // 有图片时，设置行高为60px;
                    row.setHeightInPoints(60);
                    // 设置图片所在列宽度为80px,注意这里单位的一个换算
                    sheet.setColumnWidth(indexText, (short) (35.7 * 80));
                    // sheet.autoSizeColumn(i);
                    byte[] bsValue = (byte[]) value;
                    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
                    anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
                    patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                } else {
                    // 其它数据类型都当作字符串简单处理
                    if (value == null)
                        value = "";

                    textValue = value.toString();
                }
                // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                if (textValue != null) {
                    Pattern p = Pattern.compile("^//d+(//.//d+)?\\{1}quot;");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);

                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                }
                indexText++;
            }
        }
    }

    /**
     * 获取通用样式
     *
     * @param workbook workbook对象
     * @return 通用样式
     */
    private HSSFCellStyle getHssfCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    /**
     * 生成表格表头样式
     *
     * @param workbook workbook对象
     * @return 表头样式
     */
    private HSSFCellStyle getTableHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle styleTableHeader = getHssfCellStyle(workbook);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        styleTableHeader.setFont(font);
        return styleTableHeader;

    }

    /**
     * 生成表格内容样式
     *
     * @param workbook workbook对象
     * @return 内容样式
     */
    private HSSFCellStyle getContentStyle(HSSFWorkbook workbook) {
        HSSFCellStyle styleContent = getHssfCellStyle(workbook);
        styleContent.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBold(true);
        // 把字体应用到当前的样式
        styleContent.setFont(font2);
        return styleContent;
    }

    /**
     * 获取workbook 对象
     *
     * @return workbook 对象
     */
    private HSSFWorkbook getHSSFWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        return workbook;
    }

}