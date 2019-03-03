package hello;

import com.spring.bean.user1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class demo {
    public static void main (String[] arg){
        //创建容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        //向容器获取user对象
        user1 u = (user1) ac.getBean("user1");
        //打印出user对象
        System.out.println(u);

    }
}
