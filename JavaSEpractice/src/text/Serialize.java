package text;

import javax.sql.rowset.spi.SyncResolver;
import java.io.*;
import text.text1;
//序列化测试

public class Serialize {
    public static void main(String[] args) {

        text1 a = new text1();//初始化对象

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("./text1.cer");//储存对象的文件
            ObjectOutputStream out = new ObjectOutputStream(fileOut);//读写对象流ObjectIn/OutputStream
            out.writeObject(a);//把对象写进输出流
            out.close();//关闭流
            fileOut.close();//关闭文件
            System.out.printf("Serialized data is saved");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}