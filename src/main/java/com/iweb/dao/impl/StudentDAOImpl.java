package com.iweb.dao.impl;

import com.iweb.dao.StudentDAO;
import com.iweb.pojo.Student;
import com.iweb.test1.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author FanHaoLong
 * @date 2024/7/22 下午3:52
 */
public class StudentDAOImpl implements StudentDAO {

    @Override
    public void insert(Student student) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement()) {
            // Statement
            // 1. 传参麻烦
            // 2. 性能较差
            // 3. 存在sql注入攻击问题

            // PreparedStatement
            // 1. 先编译，后传参，效率更高
            // 2. 传参方便
            // 3. 有效防止sql注入攻击的问题
            String sql = "insert into student(name,gender,birthday,addr,qqnumber)" + "values('%s','%s','%s','%s','%d')";
            sql = String.format(sql, student.getName(), student.getGender(), sdf.format(student.getBirthday()), student.getAddr(), student.getQqnumber());
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        Connection c = null;
        String sql = "delete from student where id = ?";
        try {
            c = DBUtil.getConnection();
            // 关闭事务的自动提交
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            try {
                // 发现问题进行回档
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(Student student) {
        String sql = "update student set name=?,gender=?,birthday=?,addr=?,qqnumber=? where id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // 给sql语句进行参数传递
            ps.setString(1, student.getName());
            ps.setString(2, student.getGender());
            ps.setDate(3, new Date(student.getBirthday().getTime()));
            ps.setString(4, student.getAddr());
            ps.setLong(5, student.getQqnumber());
            ps.setInt(6, student.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer count() {
        String sql = "select count(*) from student";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // 存放查询结果
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // 每次从结果集读取一行数据
                // 根据读取的字段数据不同，使用不同的get方法
                // 方法参数有两种：
                // 一种是获取的字段在查询结果中出现的顺序
                // 另一种可以不写字段出现顺序，而写字段的名称
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Student findById(Integer id) {
        String sql = "select * from student where id = ?";
        Student student = null;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                student = new Student();
                student.setId(id);
                student.setName(rs.getString("name")); // 将对应字段的数据存放到对象中
                student.setGender(rs.getString("gender"));
                student.setBirthday(rs.getDate("birthday"));
                student.setAddr(rs.getString("addr"));
                student.setQqnumber(rs.getLong("qqnumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        // 调用分页查询设置范围为最大，则可表示findAll
        return findWithLimit(0, Integer.MAX_VALUE);
    }

    @Override
    public List<Student> findByNameLike(String name) {
        return findByNameLikeWithLimit(name, 1, Integer.MAX_VALUE);
    }

    @Override
    public List<Student> findByNameLikeWithLimit(String name, int start, int limit) {
        List<Student> stus = new ArrayList<>();
        String sql = "select * from student where name like concat('%',?,'%') limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, start);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getString("gender"));
                s.setBirthday(rs.getDate("birthday"));
                s.setAddr(rs.getString("addr"));
                s.setQqnumber(rs.getLong("qqnumber"));
                stus.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stus.size() == 0 ? null : stus;
    }

    @Override
    public List<Student> findWithLimit(int start, int limit) {
        List<Student> stus = new ArrayList<>();
        String sql = "select * from student limit ?,?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            // 遍历结果集
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getString("gender"));
                s.setBirthday(rs.getDate("birthday"));
                s.setAddr(rs.getString("addr"));
                s.setQqnumber(rs.getLong("qqnumber"));
                stus.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stus;
    }

}
