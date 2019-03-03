import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class SendFinger {
    private String serverName;//IP地址
    private int port;  //端口号

    //扫描指纹
    String ScanFinger() throws InterruptedException {
        System.out.println("连接采集器ing......");
        ZKFinger10init();
        Thread.sleep(5000);
        System.out.println("已连接采集器,把食指放在扫描器上便可以自动采集");
        ZKFinger10();
        Thread.sleep(5000);

        ArrayList<String> files = new ArrayList<String>();
        File file = new File("./fingerprint");
        File[] tempList = file.listFiles();
        Random r = new Random();
        for (int i = 0; i < tempList.length; i++) {
            files.add(tempList[i].toString());
        }
        int nn = r.nextInt(files.size());
        String f = files.get(nn);

        int n = 0;
        String name = "./client/" + System.currentTimeMillis() + ".bmp";
        try {
            FileInputStream fis = new FileInputStream(f);
            FileOutputStream fos = new FileOutputStream(name);

            while ((n = fis.read()) != -1) {
                fos.write(n);
            }

            fis.close();
            fos.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("采集成功,连接服务器并发送图片");

        Thread.sleep(5000);
        return name;
    }

    void ConnServer(FileInputStream Finger){
        try {
            Thread.sleep(5000);
            System.out.println("连接到主机：" + serverName + " ，端口号：" + port); //显示连接目标
            Socket client = new Socket(serverName, port);  //初始化socket对象，建立socket连接
            System.out.println("已连接到远程服务器：" + client.getRemoteSocketAddress()); //显示连接目标

            SendFile(Finger, client);//发送图片

            client.close();//注意每次连接的完成后要关闭socket连接
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //发送图片
    void SendFile(FileInputStream f, Socket client) {
        OutputStream outToServer = null;  //发送给服务器的数据的变量
        try {
            outToServer = client.getOutputStream(); //outToServer变量是OutputStream类型，只用来传输字符类型，用来通知服务器连接情况
            DataOutputStream out = new DataOutputStream(outToServer);  //初始化一个输出流对象，把OutputStream变成DataOutputStream类型
            out.writeUTF("来自客户端的消息：我是" + client.getLocalSocketAddress());  //发送消息给服务器

            OutputStream FileOut = client.getOutputStream();//只用来传输文件
            byte[] data = new byte[1024];//每次只发送1024个二进制补码
            int len = 0;//数据大小
            while (  (len = f.read(data)) != -1) {//read方法把文件数据写到data数组中并返回数组大小
                FileOut.write(data, 0, len);//每次向输出流中写入1024个二进制补码
            }
            client.shutdownOutput();//关闭输出流


            InputStream inFromServer = null;   //取出从服务器端发回的数据
            try {
                inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);  //从服务器端发回的数据；初始化一个输入流对象，也是为了转换类型
                System.out.println("服务器响应： " + in.readUTF());  //显示出服务器发来的字符串
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ZKFinger10(){
         String ZKFINGERLIB = "ZKFinger10.dll";
        }

    public void ZKFinger10init(){
        String ZKFINGERLIB = "ZKFinger10.dll";
    }

    public static void main(String[] arg) throws FileNotFoundException, InterruptedException {
        SendFinger send1 = new SendFinger();
        String fingerprint = send1.ScanFinger();

        send1.serverName = "127.0.0.1";//IP地址
        send1.port = 9999;//端口号

        FileInputStream Finger = new FileInputStream(fingerprint);//要发送的图片
        send1.ConnServer(Finger);
    }
}