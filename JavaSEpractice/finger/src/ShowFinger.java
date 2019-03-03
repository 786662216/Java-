import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

public class ShowFinger extends Thread {

    private ServerSocket serverSocket;

    public static final String URL = "jdbc:mysql://localhost:3306/finger";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    public ShowFinger(int port) throws IOException {
        serverSocket = new ServerSocket(port);//绑定的端口
        serverSocket.setSoTimeout(1000000);
    }


    public void run() {
        while (true)//一直监听
        {
            try {
                System.out.println("等待远程连接，端口号为：" + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();//如果有连接，建立连接
                Thread.sleep(3000);
                System.out.println("远程客户端已连接：" + server.getRemoteSocketAddress());//连接成功的地址

                DataInputStream in = new DataInputStream(server.getInputStream());//读取服务器接收到的数据
                System.out.println(in.readUTF());//显示数据

//------------------------------------------------------------------------------------------------------------------------
                Thread.sleep(3000);
                String FileName = SaveFinger(server);//接收图片
                Thread.sleep(3000);
                String[] fingerinfo = info(FileName);//识别图片信息
                Thread.sleep(3000);
                try {
                    db(fingerinfo);//将指纹信息存入数据库中
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Thread.sleep(3000);

//------------------------------------------------------------------------------------------------------------------------


                DataOutputStream out = new DataOutputStream(server.getOutputStream());//服务器发给客户端的数据的变量
                out.writeUTF("谢谢连接我，我是：" + server.getLocalSocketAddress() + "\nGoodbye!");//发送服务器自己的地址
                out.writeUTF("指纹图片：" + FileName + "上传成功");


                server.close();
            } catch (SocketTimeoutException s)//时间到了抛出错误
            {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e)//抛出错误
            {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //接收图片
    String SaveFinger(Socket server) throws IOException, InterruptedException {
        System.out.println("准备接受指纹图片...");
        Thread.sleep(3000);

        String FileName = "./server/" + System.currentTimeMillis() + ".bmp";//图片名字
        InputStream in = server.getInputStream();//输入流
        FileOutputStream f = new FileOutputStream(FileName);

        byte[] data = new byte[1024];
        int len = 0;
        while(  (len = in.read(data)) != -1){
            f.write(data,0,len);
        }
        f.close();
        System.out.println("接收成功！");
        return FileName;
    }

    //储存图片信息到数据库中
    void db(String[] fingerinfo) throws Exception{
        System.out.println("正在储存信息到数据库中......");
        System.out.println("jdbc:mysql://localhost:3306/finger");
        Thread.sleep(3000);

        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        String sql = "INSERT into fingerinfo (height, width, infotime, infomation) VALUE (" + "'" + fingerinfo[0] + "'" + ","+ "'" + fingerinfo[1] + "'" + "," + "'" +fingerinfo[2] + "'" + ","+ "'" +fingerinfo[3] + "'" + ")";
        stmt.execute(sql);
        Thread.sleep(3000);
        System.out.println("储存成功！请到数据库中查看");
    }

    String fingerprint(){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < 88; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //识别指纹信息并打印
    String[] info(String filename){

        String[] fingerinfo = new String[4];//所有指纹数据

        try {
            BufferedImage img = ImageIO.read(new File(filename));
            fingerinfo[0] = String.valueOf(img.getWidth());//长
            fingerinfo[1] = String.valueOf(img.getHeight());//宽
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date date = new Date();//时间
        fingerinfo[2] = date.toString();

        fingerinfo[3] = fingerprint();//指纹模板字符串

        System.out.println("长：" + fingerinfo[0]);
        System.out.println("宽：" + fingerinfo[1]);
        System.out.println("扫描时间：" + fingerinfo[2]);
        System.out.println("指纹模板字符串：" + fingerinfo[3]);

        return fingerinfo;
    }


    public static void main(String[] args) {
        int port = Integer.parseInt("9999");
        try {
            Thread t = new ShowFinger(port);
            t.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
