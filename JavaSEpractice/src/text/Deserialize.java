package text;

import java.io.*;
//反序列化
public class Deserialize {
    public static void main(String[] args){
        File file = new File("./text1.ser");//对象文件
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));//对象输入流
            text1 newUser = (text1) ois.readObject();//读取对象
            newUser.getA();//可以调用对象了
             System.out.println(newUser.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }



    }
}

