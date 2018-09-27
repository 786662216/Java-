package socket;

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
    private ServerSocket serverSocket;

    public GreetingServer(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);//绑定的端口
        serverSocket.setSoTimeout(1000000);
    }

    public void run()
    {
        while(true)//一直监听
        {
            try
            {
                System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();//如果有连接，建立连接
                System.out.println("远程客户端地址：" + server.getRemoteSocketAddress());//连接成功的地址

                DataInputStream in = new DataInputStream(server.getInputStream());//读取服务器接收到的数据
                System.out.println(in.readUTF());//显示数据

                DataOutputStream out = new DataOutputStream(server.getOutputStream());//服务器发给客户端的数据的变量
                out.writeUTF("谢谢连接我，我是：" + server.getLocalSocketAddress() + "\nGoodbye!");//发送服务器自己的地址

                server.close();
            }catch(SocketTimeoutException s)//时间到了抛出错误
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)//抛出错误
            {
                e.printStackTrace();
                break;
            }
        }
    }
    public static void main(String [] args)
    {
        int port = Integer.parseInt(args[0]);
        try
        {
            Thread t = new GreetingServer(port);
            t.run();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
