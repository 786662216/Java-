package socket;

// 文件名 GreetingClient.java

import java.net.*;
import java.io.*;

public class GreetingClient
{
    public static void main(String [] args)
    {
        String serverName = "120.76.142.240";  //IP地址
        int port = Integer.parseInt("9999");  //端口号
        try
        {
            System.out.println("连接到主机：" + serverName + " ，端口号：" + port); //显示连接目标
            Socket client = new Socket(serverName, port);  //初始化socket对象，建立socket连接
            System.out.println("已连接到远程服务器：" + client.getRemoteSocketAddress()); //显示连接目标

            //所有的输入输出流对象操作都是为了接收和发送数据，不用管为什么，这么用就对了
            OutputStream outToServer = client.getOutputStream();  //发送给服务器的数据的变量
            DataOutputStream out = new DataOutputStream(outToServer);  //初始化一个输出流对象，把OutputStream变成DataOutputStream类型
            out.writeUTF("Hello from " + client.getLocalSocketAddress());  //发送数据给服务器

            InputStream inFromServer = client.getInputStream();   //取出从服务器端发回的数据
            DataInputStream in = new DataInputStream(inFromServer);  //从服务器端发回的数据；初始化一个输入流对象，也是为了转换类型
            System.out.println("服务器响应： " + in.readUTF());  //显示出服务器发来的字符串

            client.close();//注意每次连接的完成后要关闭socket连接
        }catch(IOException e)//抛出错误
        {
            e.printStackTrace();
        }
    }
}
