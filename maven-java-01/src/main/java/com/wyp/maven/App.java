package com.wyp.maven;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception{
        //创建信息
        List<MsgEntity> msgList = getMsgList();
        System.out.println("程序1启动中。。。");
        //发送xml请求
        URL url = new URL("http://65.29.100.27/publicJK/SendMsg.aspx");
       // String xml = "<aaa><ddd>cccccc客户端请求的xml数据cccccccc</ddd></aaa>";
        String strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
        strXml += "<Request>";
        //head值
        strXml += "<head>";
        strXml += "<Auten>";
        strXml += "83104DF53E33D14AB220E3D49B2833E5";
        strXml += "</Auten>";
        strXml += "</head>";
        //body值
        strXml += "<body>";
        strXml += "<SendMsg>";
        strXml += "<phone>15810755905</phone>";
        strXml += "<hphm>京P253</hphm>";
        strXml += "<message>这个是短信内容【测试】</message >";
        strXml += "<kkcode>卡扣代码</kkcode >";
        strXml += "</SendMsg>";
        strXml += "<SendMsg>";
        strXml += "<phone>13126951273</phone>";
        strXml += "<hphm>京P253</hphm>";
        strXml += "<message>这个是短信内容【测试】</message >";
        strXml += "<kkcode>卡扣代码</kkcode >";
        strXml += "</SendMsg>";
        strXml += "</body>";
        strXml += "</Request>";
        URLConnection conn = null;
        System.out.println("请求路径为：http://65.29.100.27/publicJK/SendMsg.aspx");
        System.out.println("请求内容为");
        System.out.println(strXml);
        conn = url.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Length", Integer.toString(strXml.length()));
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        OutputStream ops = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(ops, "utf-8");
        osw.write(strXml);
        osw.flush();
        osw.close();
        System.out.println("发送成功...");
        //发送成功后，获取服务器的响应xml串：
        StringBuffer sb = new StringBuffer();
        String line = "";
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));//三层包装
        while ((line = br.readLine()) != null) {
            sb.append(line+ "\r\n");
        }
        System.out.println("【返回内容】*****************************************");
        System.out.println(sb.toString());
    }

    public static List<MsgEntity> getMsgList() throws Exception{
        MsgEntity msg1 = new MsgEntity();
        msg1.setPhone("110");
        msg1.setHphm("京P110");
        msg1.setMessage("短信内容110");
        msg1.setKkcode("卡扣代码110");
        MsgEntity msg2 = new MsgEntity();
        msg2.setPhone("119");
        msg2.setHphm("京P119");
        msg2.setMessage("短信内容119");
        msg2.setKkcode("卡扣代码119");
        List<MsgEntity> msgList = new ArrayList<MsgEntity>();
        msgList.add(msg1);
        msgList.add(msg2);
        return msgList;
    }
}
