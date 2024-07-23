package com.iweb.test1;

import com.iweb.dao.StudentDAO;
import com.iweb.dao.impl.StudentDAOImpl;
import com.iweb.pojo.Student;

import java.util.Date;

/**
 * JDBC：java database connection
 *
 * @author FanHaoLong
 * @date 2024/7/22 下午2:47
 */
public class Test {
    public static void main(String[] args) {
//        // 驱动加载
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println("驱动加载成功！");
//        // 获取jdbc连接
//        String url = "jdbc:mysql://localhost:3306/nuist?characterEncoding=utf8";
//        String username = "root";
//        String password = "a12345";
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("获取连接成功！");
//        try {
//            Statement s = connection.createStatement();
//            // 准备一个sql语句
//            String sql = "insert into student(name,gender,birthday,addr,qqnumber) values('albb','女','2003-03-23','南京',1312909178)";
//            // 运行sql语句
//            s.execute(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // 调用插入方法
//        Student s1 = new Student(0,"吴伟","男",new Date(),"南京",110);
//        StudentDAO studentDAO = new StudentDAOImpl();
//        studentDAO.insert(s1);

        // 调用修改方法
        Student s2 = new Student(6,"雪豹","男",new Date(),"理塘",119);
        StudentDAO studentDAO = new StudentDAOImpl();
        studentDAO.update(s2);
    }
    // 驱动加载、连接获取、创建编译语句对象、执行语句
    // 查询语句：驱动加载、连接获取、创建编译语句对象、执行语句、获取查询结果
}
