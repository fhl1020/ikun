package com.iweb.test;

import com.iweb.dao.StudentDAO;
import com.iweb.dao.impl.StudentDAOImpl;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 用于DAO接口测试
 *
 * @author FanHaoLong
 * @Test ：将指定方法标记为测试方法，可以不依赖于main方法直接运行
 * @Before ：在所有测试方法运行之前执行的代码，一般用于环境的初始化
 * @After ：在所有测试方法运行之后执行的代码，一般用于资源回收
 * @date 2024/7/23 上午11:02
 */
public class TestStudentDAO {
    private StudentDAO studentDAO;

    @Before
    public void init() {
        studentDAO = new StudentDAOImpl();
    }

    @Test
    public void testFindById() {
        // 断言类：验证结果和预期是否一致
        Assert.assertNotNull(studentDAO.findById(1));
        // 为null是抛出异常，测试不通过
//        Assert.assertNotNull(null);
    }

    @Test
    public void testConut() {
        Assert.assertEquals(9, (long) studentDAO.count());
    }

    @Test
    public void testFindAll() {
        Assert.assertNotNull(studentDAO.findAll());
    }

    @Test
    public void testFindByNameLike() {
        Assert.assertNotNull(studentDAO.findByNameLike("f"));
    }

    @Test
    public void testFindWithLimit() {
        Assert.assertNotNull(studentDAO.findWithLimit(1, 5));
    }

    @Test
    public void testFindByNameLikeWithLimit() {
        Assert.assertNotNull(studentDAO.findByNameLikeWithLimit("l", 1, 2));
    }
}
