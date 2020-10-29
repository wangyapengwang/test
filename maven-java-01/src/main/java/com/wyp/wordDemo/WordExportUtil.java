package com.wyp.wordDemo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *          模板制作方法
 *          1、模板制作一定要谨慎，最好是样式全部调试好了，位置都OK了，需求基本不会变了再制作，
 *              因为每次修改哪怕一点点东西，都有可能导致全部从头再来一次，非常麻烦。
 *          2、尽量使用office制作，不要用wps制作，然后再用office开发，因为这两者编码和解析方式
 *              都有很大的不同，可能导致你导出的word样式变形。
 *          3、如果导出的样式涉及到了图片（多张图片）时，一定要使用小图片制作，最好只有几十K的，不要使用大图片然后缩
 *              小，没用的，base64还是会占那么多位置，替换图片时长到你怀疑人生。二是，如果最终导出是有多张图片的，一定要
 *              使用多张图片，如果是复制粘贴的，最终xml里只有一段base64，给替换造成非常大的麻烦。
 *          4、如果调试好自己要的所有样式后，另存为Word 2003XML，wps没有这个模式，只有xml格式，使用这个模式最终效
 *              果并不理想。转好xml格式后，然后把xml文档后缀改为ftl文档。。
 *          5、替换源码里相应位置的信息换成freemarker的标识。其实就是Map<String, Object>中key。如：姓名替换为${name}
 *
 * ---------------原文链接 https://blog.csdn.net/qq_41253573/article/details/83545536
 * ---------------https://www.jianshu.com/p/89a80afb5f4f
 *
 * Class WordExportUtil
 * PackageName com.wyp.wordDemo
 * DATE 2019/10/29 9:42
 * Describe
 */
public class WordExportUtil {

    private static WordExportUtil service = null;

    private WordExportUtil() {
        super();
    }

    public static WordExportUtil getInstance() {
        if (service == null) {
            synchronized (WordExportUtil.class) {
                if (service == null) {
                    service = new WordExportUtil();
                }
            }
        }
        return service;
    }

    /**
     * @param templateFilePath eg: /template/test/test.ftl
     * @param dataMap
     * @param exportFilePath   eg: /tmp/test/test123.doc
     * @param loadType         设置路径加载方式。1-绝对路径，2-项目相对路径
     * @return
     * @throws Exception
     */
    public File createDocFile(String templateFilePath, Map<String, Object> dataMap, String exportFilePath, int loadType) throws Exception {
        Template t = null;
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("UTF-8");
        try {
            templateFilePath = pathReplace(templateFilePath);
            String ftlPath = templateFilePath.substring(0, templateFilePath.lastIndexOf("/"));
            if (loadType == 1) {
                configuration.setDirectoryForTemplateLoading(new File(ftlPath)); // FTL文件所存在的位置
            } else {
                configuration.setClassForTemplateLoading(this.getClass(), ftlPath);//以类加载的方式查找模版文件路径
            }


            String ftlFile = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1);
            t = configuration.getTemplate(ftlFile); // 模板文件名

            File outFile = new File(exportFilePath);
            Writer out = null;

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));

            t.process(dataMap, out);
        } catch (Exception e) {
            //LOGGER.error("导出word文档出错", e);
            throw e;
        }

        return null;
    }

    /**
     * 把路径的\替换成/
     *
     * @param path
     * @return
     */
    private String pathReplace(String path) {
        while (path != null && path.contains("\\")) {
            path = path.replace("\\", "/");
        }
        return path;
    }

    public static void main(String[] args) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        getData(dataMap);
        String templateFile = "E:/titlee.ftl";
        String exportFile = "E:/luedfe.doc";

        try {
            WordExportUtil.getInstance().createDocFile(templateFile, dataMap, exportFile, 1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 测试用的
     *
     * @param dataMap
     */
    public static void getData(Map<String, Object> dataMap) {
        dataMap.put("title", "10");
        dataMap.put("num", "2012");
        dataMap.put("num1", "2");
    }

}
