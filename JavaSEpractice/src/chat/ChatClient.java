package chat;

/*
* 聊天室客户端
*
* */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class ChatClient extends Frame {

    //socket对象
    Socket s = null;
    //字符串对象
    DataOutputStream dos = null;
    DataInputStream dis = null;

    private boolean bConnect = false;

    //创建组件
    TextField tfTxt = new TextField();//输入框
    TextArea taContent = new TextArea();//对话框

    Thread tRecv = new Thread(new RecvThread());

    //主类，直接建立窗口即可
    public static void main(String[] args) {
        new ChatClient().launchFrame();//建立窗口
    }

    //窗口主题
    //各类元素的大小、位置、监听器等等
    public void launchFrame() {
        setLocation(400, 300);//在屏幕的哪个位置
        this.setSize(300, 300);//窗口尺寸

        //把相应的东西填进去
        add(tfTxt, BorderLayout.SOUTH);//输入框靠下
        add(taContent, BorderLayout.NORTH);//对话框靠上
        pack();

        //添加关闭功能：使用匿名类并重写windowClosing方法
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                System.exit(0);
                disconnect();
            }
        });

        //添加发送信息的监听器
        tfTxt.addActionListener(new TFListener());

        setVisible(true);//让窗体显示
        connect();

        tRecv.start();
    }

    //连接。
    //初始化链接对象和输出流
    public void connect() {
        try {
            s = new Socket("127.0.0.1", 9999);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            bConnect = true;
            System.out.println("connected!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //断开连接的一系列操作
    public void disconnect() {
        try {
            dos.close();
            dis.close();
            s.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }

    }

    //回车把信息从输入框发送到聊天框和服务器
    //继承ActionListener这个接口并实现其中相应的方法，响应回车事件
    private class TFListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //打印到聊天框
            String string = tfTxt.getText().trim();
            tfTxt.setText("");
            //发送聊天内容到服务器
            try {
                dos.writeUTF(string);
                dos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class RecvThread implements Runnable {

        public void run() {
            try {
                while (bConnect) {
                    String str = null;
                    str = dis.readUTF();
                    //System.out.println(str);
                    taContent.setText(taContent.getText() + str + '\n');
                }
            } catch (SocketException e) {
                System.out.println("退出了，88");
            }catch (EOFException e){
                System.out.println("退出了，88");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
