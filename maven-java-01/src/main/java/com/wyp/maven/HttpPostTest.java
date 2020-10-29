package com.wyp.maven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * Class HttpPostTest
 * PackageName com.wyp.maven
 * DATE 2019/3/13 14:55
 * Describe
 */
public class HttpPostTest {
    void testPost(String urlStr) {
        try {
            System.out.println("程序启动2！开始发送。。。");
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            //con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(con
                    .getOutputStream());
            String xmlInfo = getXmlInfo();
            System.out.println("urlStr=" + urlStr);
            System.out.println("xmlInfo=" + xmlInfo);
            out.write(new String(xmlInfo.getBytes("utf-8")));
            out.flush();
            out.close();
            System.out.println("程序发送完毕。。。");
            System.out.println("这是返回值*****************");
            BufferedReader br = new BufferedReader(new InputStreamReader(con
                    .getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getXmlInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<Request>");
        sb.append("<head>");
        sb.append("<Auten>");
        sb.append("83104DF53E33D14AB220E3D49B2833E5");
        sb.append("</Auten>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<SendMsg>");
        sb.append("<phone>110</phone>");
        sb.append("<hphm>车牌号码110</hphm>");
        sb.append("<message>短信内容110</message >");
        sb.append("<kkcode>卡扣代码110</kkcode >");
        sb.append("</SendMsg>");
        sb.append("<SendMsg>");
        sb.append("<phone>119</phone>");
        sb.append("<hphm>车牌号P119</hphm>");
        sb.append("<message>短信内容119</message >");
        sb.append("<kkcode>卡扣代码119</kkcode >");
        sb.append("</SendMsg>");
        sb.append("</body>");
        sb.append("</Request>");
        return sb.toString();
    }

    public static void main(String[] args) {
        //String url = "http://localhost/go4mi/message/toss";
        String url = "http://65.29.100.27/publicJK/SendMsg.aspx";
        new HttpPostTest().testPost(url);
    }
}
