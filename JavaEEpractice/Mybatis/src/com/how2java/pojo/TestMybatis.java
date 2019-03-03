package com.how2java.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.how2java.pojo.Category;

public class TestMybatis {

    public static void main(String[] args) throws IOException {
        //加载核心配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //创建session
        SqlSession session = sqlSessionFactory.openSession();
        //通过session的selectList方法，调用sql语句listCategory。
        //listCategory这个就是在配置文件Category.xml中那条sql语句设置的id。
        //执行增删改查
//      1、创建映射对象
        Category index = new Category();
//      2、这种方式设置具体值
        index.setId(3);
        index.setName("习近平");
//      3、不同的sql语句都有相应的方法来执行，第一个参数是xml文件中的sql语句的id 
        session.insert("CategoryInsert",index);
    }
}

