package com.wyp.excelDemo;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ImportExcelUtil
 * PackageName com.wyp.excelDemo
 * DATE 2019/11/11 9:24
 * Describe
 */
public class ImportExcelUtil {


    /**
     * 定义模板填充字符 只允许将A1内容替换为数字！！！正整数！！！
     */
    private static final String FILL_CHARACTER = "A1";


    public static void main(String[] args) {
        /*//获取模板 读取A1坐标
        List<ExUtEntity> entityList = analysisExcel("E:\\工作簿1.xls");
        try {
            String[] li = {"E:\\工作簿2假数据.xls", "E:\\工作簿3假数据.xls"};
            List<List<ExUtEntity>> data = getData(li, entityList);
            List<ExUtEntity> hebing = merge(data);
            copyFileArray("E:\\工作簿1.xls", "E:\\工作簿2.xls");
            dataInput("E:\\工作簿2.xls", hebing);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String[] li = {"E:\\工作簿2假数据.xls", "E:\\工作簿3假数据.xls"};
        fanxiej("E:\\\\工作簿1.xls", li, "E:\\工作簿2.xls");
    }


    private static void fanxiej(String templatePath, String[] dataExcel, String descPath) {
        try {
            System.out.println("》》》》》》START=======反邪教专用自定义报表。。。");
            long timeStare = System.currentTimeMillis();
            ImportExcelUtil util = new ImportExcelUtil();
            //第一步，读取模板
            List<ExUtEntity> entityList = util.analysisExcel(templatePath);
            //第二步，根据模板读取数据
            List<List<ExUtEntity>> data = util.getData(dataExcel, entityList);
            //第三步，合并数据
            List<ExUtEntity> merge = util.merge(data);
            //第四步，复制一个模板出来
            util.copyFileArray(templatePath, descPath);
            //第五步，将合并的数据写入复制的模板中
            util.dataInput(descPath, merge);
            long timeEnd = System.currentTimeMillis() - timeStare;
            System.out.println("》》》》》》END=======反邪教专用自定义报表结束耗时【" + timeEnd + "】ms。。。请查看路径" + descPath);
        } catch (Exception e) {
            throw new RuntimeException("反邪教专用自定义报表异常！请按规则进行导入模板，并按模板填写数据！");
        }

    }


    /**
     * 数据填充至模板
     *
     * @param excelPath
     * @param data
     * @throws Exception
     */
    private void dataInput(String excelPath, List<ExUtEntity> data) throws Exception {
        //获取系统文档
        POIFSFileSystem fspoi = new POIFSFileSystem(new FileInputStream(excelPath));
        //创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook(fspoi);
        //创建工作表对象
        HSSFSheet sheet = workbook.getSheet("sheet1");
        //HSSFSheet sheet = getSheet(excelPath);
        for (ExUtEntity datum : data) {
            HSSFRow row = sheet.getRow(datum.getRow());
            HSSFCell cell = row.getCell(datum.getCell());
            cell.setCellValue(datum.getValue());
        }
        FileOutputStream out = new FileOutputStream(excelPath);
        workbook.write(out);
        out.close();
    }

    /**
     * 数据合并
     *
     * @param data
     * @return
     * @throws Exception
     */
    private List<ExUtEntity> merge(List<List<ExUtEntity>> data) throws Exception {
        int x = 0;
        List<ExUtEntity> re = new ArrayList<>();
        for (int i = 0; i < data.get(0).size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                //System.out.println(data.get(j).get(i).getValue());
                x += data.get(j).get(i).getValue();
                if (j == 1) {
                    re.add(new ExUtEntity(data.get(j).get(i).getRow(), data.get(j).get(i).getCell(), x));
                    x = 0;
                }
            }
        }
        for (ExUtEntity exUtEntity : re) {
            System.out.println(exUtEntity);
        }
        return re;
    }

    /**
     * 获取全部数据
     *
     * @param strings
     * @param entityList
     * @return
     * @throws Exception
     */
    private List<List<ExUtEntity>> getData(String[] strings, List<ExUtEntity> entityList) throws Exception {
        List<List<ExUtEntity>> dataList = new ArrayList<>();
        for (String string : strings) {
            HSSFSheet sheet = getSheet(string);
            List<ExUtEntity> newArray = getNewArray();
            for (ExUtEntity exUtEntity : entityList) {
                newArray.add(new ExUtEntity(exUtEntity.getRow(), exUtEntity.getCell(), getExcel1Value(exUtEntity, sheet)));
            }
            dataList.add(newArray);
        }
        return dataList;
    }

    /**
     * 获取sheet对象
     *
     * @param excelPath
     * @return
     * @throws IOException
     */
    private HSSFSheet getSheet(String excelPath) throws IOException {
        FileInputStream fis = new FileInputStream(excelPath);
        //获取系统文档
        POIFSFileSystem fspoi = new POIFSFileSystem(fis);
        //创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook(fspoi);
        //创建工作表对象
        return workbook.getSheet("sheet1");
    }


    /**
     * 获取模板信息
     *
     * @param filePath
     * @return
     */
    private List<ExUtEntity> analysisExcel(String filePath) {
        //读取excel模板信息，获取坐标信息数组
        List<ExUtEntity> entityList = new ArrayList<>();
        File xlsFile = new File(filePath);
        // 工作表
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(xlsFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        // 表个数。
        int numberOfSheets = workbook.getNumberOfSheets();
        // 遍历表。
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            // 行数。
            int rowNumbers = sheet.getLastRowNum() + 1;
            // Excel第一行。
            Row temp = sheet.getRow(0);
            if (temp == null) {
                continue;
            }
            int cells = temp.getPhysicalNumberOfCells();
            // 读数据。
            for (int row = 0; row < rowNumbers; row++) {
                Row r = sheet.getRow(row);
                for (int col = 0; col < cells; col++) {
                    if (r.getCell(col) != null) {
                        //System.out.print(r.getCell(col).toString()+" ");
                        if (FILL_CHARACTER.equals(r.getCell(col).toString()) || r.getCell(col).toString() == FILL_CHARACTER) {
                            /*System.out.print(row);
                            System.out.print(col);*/
                            entityList.add(new ExUtEntity(row, col));
                        }
                    }
                }
                // 换行。
                /*System.out.println();*/
            }
        }
        return entityList;
    }


    public int getExcel1Value(ExUtEntity exUtEntity, HSSFSheet sheet) {
        int value = 0;
        try {
            //得到Excel表格
            HSSFRow row = sheet.getRow(exUtEntity.getRow());
            //得到Excel工作表指定行的单元格
            HSSFCell cell = row.getCell(exUtEntity.getCell());
            String s = cell.toString().substring(0, cell.toString().length() - 2);
            value = Integer.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取新的ExUtEntity集合
     *
     * @return
     */
    private List<ExUtEntity> getNewArray() {
        return new ArrayList<>();
    }


    /**
     * 文件复制
     *
     * @param srcFile
     * @param destFile
     */
    private void copyFileArray(String srcFile, String destFile) {
        //读取源文件
        try (
                FileInputStream fis = new FileInputStream(srcFile);
                FileOutputStream fos = new FileOutputStream(destFile);
        ) {
            //创建数组
            byte[] bytes = new byte[50];
            int read = fis.read(bytes);
            while (read != -1) {
                fos.write(bytes, 0, read);
                read = fis.read(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
