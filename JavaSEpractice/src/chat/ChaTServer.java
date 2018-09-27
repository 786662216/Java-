package chat;
/*
 * 聊天室服务端
 *
 * */
import java.io.*;
import java.net.*;
import java.util.*;

public class ChaTServer {

    //初始化变量
    boolean started = false;//连接状态变量
    ServerSocket ss = null;

    List<Client> clients = new ArrayList<Client>();

    public static void main(String[] args){
        new ChaTServer().start();
    }

    public void start (){
        try {
            ss = new ServerSocket(9999);
            //建立连接成功
            started = true;
            //建立失败的原因
        } catch (BindException e) {
            System.out.println("端口被占用！");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            while(started){
                //如果接收到了，则更改布尔对象的值
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("a client connected!");
                new Thread(c).start();
                clients.add(c);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class Client implements Runnable {
        private Socket s;//每个用户一个
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnected = false; //判断是否建立了连接

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //给每个客户端发送他人消息
        public void send(String str){
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                clients.remove(this);
                System.out.println("有人退出了，");
            }
        }

        public void run() {
            Client c = null;
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for(int i =0;i<clients.size();i++){
                        c = clients.get(i);
                        c.send(str);
                    }
                }
            } catch (SocketException e){
                clients.remove(this);
                System.out.println("a client quit");
            }
            catch (EOFException e) {
                System.out.println("connect closed!");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dis != null) dis.close();
                    if (s != null) s.close();
                    if (dos != null) dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}


