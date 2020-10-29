package com.wyp.ioliu;

import org.junit.Test;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Class TestOne
 * PackageName com.wyp.ioliu
 * DATE 2019/10/27 9:26
 * Describe
 */
public class TestOne {


    /*------------------------------------------字节流--------------------------------------------------*/
    /*
     * 读写文件步骤
     * 1、建立流通道
     * 2、读写文件
     * 3、关闭流通道
     * */
    @Test
    public void fileInputStream() {
        //读取文件内容
        try {
            //在当前程序和文件建立流通道
            FileInputStream fis = new FileInputStream("E:/xxx.txt");
            //读取文件内容方法read()方法读取一个字节，读到文件末尾返回-1
            int read = fis.read();
            while (read != -1) {
                System.out.println(read);//当前txt全是英文，一个英文一个字节
                read = fis.read();
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 读取文件内容保存到字节数组中
     * */
    @Test
    public void fileInputStreamSaveArr() {
        try {
            //建立流通道
            FileInputStream fis = new FileInputStream("E:/xxx.txt");
            //读取字节保存到字节数组中
            byte[] bytes = new byte[4];
            //从流中读取字节保存到字节数组中，返回渎到的字节数，读到文件末尾返回-1
            int read = fis.read(bytes);
            while (read != -1) {
                System.out.println(read);
                System.out.println(Arrays.toString(bytes));
                //把读到的字节转换为字符串
                System.out.println(new String(bytes, 0, read));
                read = fis.read(bytes);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileOutputStream() {
        try (
                //建立流通道 如果文件不存在，程序自动创建文件
                FileOutputStream fos = new FileOutputStream("E:/uou.txt");
        ) {
            fos.write(66);
            fos.write(67);
            fos.write(68);
            //换行，在win下，换行需要两个字节 \r\n
            fos.write('\r');
            fos.write('\n');
            //一次写一个字节数组
            byte[] bytes = "asdasdasdasd".getBytes();
            fos.write(bytes);
            //换行
            fos.write('\r');
            fos.write('\n');
            //吧字节数组中的部分写入
            fos.write(bytes, 2, 3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 文件复制
     * */
    @Test
    public void copyFile() {
        String srcFile = "E:/xxx.txt";
        String destFile = "E:/xxxx.txt";
//        copyFileByte(srcFile, destFile);
        copyFileArray(srcFile, destFile);
    }

    /**
     * 以字节为单位复制
     *
     * @param srcFile
     * @param destFile
     */
    private void copyFileByte(String srcFile, String destFile) {
        //读取源文件
        try (
                FileInputStream fis = new FileInputStream(srcFile);
                FileOutputStream fos = new FileOutputStream(destFile);
        ) {
            int read = fis.read();
            while (read != -1) {
                System.out.println(read);
                fos.write(read);
                read = fis.read();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以字节数组为单位复制，效率高
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
            byte[] bytes = new byte[4];
            int read = fis.read(bytes);
            while (read != -1) {
                System.out.println(Arrays.toString(bytes));
                fos.write(bytes, 0, read);
                read = fis.read(bytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /********************************缓冲流 BufferedInputStream 、 BufferedOutputStream***********************************/
    @Test
    public void bufferedInputStream() {
        try {
            //创建一个字节流
            InputStream is = new FileInputStream("E:/xxx.txt");
            //BufferedInputStream是对字节流进行包装 默认最多缓冲8K
            BufferedInputStream bis = new BufferedInputStream(is);
            //从bis缓冲区中读取数据
            int read = bis.read(); //从缓冲区读取一个字节
            System.out.println(read);
            byte[] bytes = new byte[4];
            int read1 = bis.read(bytes);
            System.out.println(Arrays.toString(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bufferedOutputStream() {
        try {
            OutputStream os = new FileOutputStream("E:/oiuys7.txt");
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(89);
            bos.write(90);
            bos.write(91);
            bos.write("asdasdasdasdasdasd".getBytes());
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /********************************缓冲流 DataOutputStream 、 DataInputStream***********************************/
    @Test
    public void dataOutputStream() {
        try {
            OutputStream os = new FileOutputStream("E:/op.txt");
            DataOutputStream dos = new DataOutputStream(os);
            //向流中写数据，可以带有数据格式
            dos.writeInt(123);
            dos.writeDouble(3.14);
            dos.writeBoolean(true);
            dos.writeUTF("sasdasfssw");
            dos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dataInputStream() {
        try {
            InputStream is = new FileInputStream("E:/op.txt");
            DataInputStream dis = new DataInputStream(is);
            //读取的顺序要和写入的顺序一致
            System.out.println(dis.readInt());
            System.out.println(dis.readDouble());
            System.out.println(dis.readBoolean());
            System.out.println(dis.readUTF());
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 对象序列化
     * 把对象转换为 0 1 二进制序列
     * 一般吧对象转换为01序列保存到文件中，使用
     * 对象反序列化
     * 把0 1二进制序列转化为对象
     * 一般从文件中读取二进制序列转化为对象
     * */

    class Person implements Serializable {

        private static final long serialVersionUID = -4851530502822598633L;

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Person() {

        }

        public Person(String name, int age) {
            super();
            this.name = name;
            this.age = age;
        }
    }


    /************************************打印流**************************************/
    @Test
    public void printStream() {
        PrintStream ps = null;
        try {
            OutputStream os = new FileOutputStream("E:/log.txt",true);//为true时，以追加的方式打开文件
            ps  = new PrintStream(os);
            ps.println("hellow");
            ps.println("word");
            //将控制台信息打印到log中
            System.setOut(ps);
            System.out.println("xosuwysgaw");
            int num = 10 / 0;
        } catch (Exception e) {
            e.printStackTrace(ps);
        }
    }


    /*------------------------------------------字符流--------------------------------------------------*/

    /**
     * 以字符为单位读取文件
     * 字符流用于读写纯文本文件，
     */
    @Test
    public void fileReader() {
        try {
            FileReader fr = new FileReader("E:/xxx.txt");
            /*//一次读取一个字符，读到文件末尾返回-1
            int read = fr.read();//读取一个字符，返回字符的码值
            while (read != -1) {
                System.out.print( (char) read);
                read = fr.read();
            }*/
            char[] chars = new char[2048];
            int read = fr.read(chars);//从流中读取字符放到char数组中，返回读到的数字个数
            while (read != -1) {
                System.out.print(new String(chars,0,read));
                read = fr.read(chars);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void fileWriter() {
        try {
            FileWriter fw = new FileWriter("E:/xt.txt",true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            fw.write(sdf.format(new Date()) + "START=======================");
            fw.write("\r\n");
            fw.write("添加用户，数值为".toCharArray());
            fw.write("\r\n");
            fw.write("添加用户，返回内容为".toCharArray());
            fw.write("\r\n");
            fw.write(sdf.format(new Date()) + "END=======================");
            fw.write("\r\n");
            fw.write("\r\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*------------------------------------------转换流--------------------------------------------------*/
    /**
     * InputStreamReader / OutputStreamWriter
     * 当读取的文本文件和当前环境编码不一致时，使用转换流
     */


    @Test
    public void inputStreamReader () {
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("E:/xxx.txt"),"GBK");
            char[] chars = new char[8];
            int read = isr.read(chars);
            while (read != -1) {
                System.out.println(new String(chars,0,read));
                read = isr.read(chars);
            }
            isr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void outputStreamWriter () {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("E:/xlksh.txt"),"GBK");
            osw.write("哈哈哈");
            osw.append("ss");
            osw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void bufferedReader() {
        try {
            //Reader r = new FileReader("E:/xxx.txt");//文件编码和当前环境一致
            Reader r = new InputStreamReader(new FileInputStream("E:/xxx.txt"),"GBK");//文件编码和当前环境不一致
            BufferedReader br = new BufferedReader(r);
            //字符缓存流，可以一次读取一行，读到文件末尾返回null
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        try {
            Writer w = new FileWriter("E:/xx.txt");
            //Writer ww = new OutputStreamWriter(new FileOutputStream("E:/xxx.txt"),"UTF-8");
            BufferedWriter bw = new BufferedWriter(w);
            bw.write("哈哈");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
