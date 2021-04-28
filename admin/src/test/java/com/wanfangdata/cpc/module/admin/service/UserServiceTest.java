package com.wanfangdata.cpc.module.admin.service;

import com.wanfangdata.cpc.AdminApplication;
import com.wanfangdata.cpc.SpringbootApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: linzhaoguan
 * @date: 2019/9/11 1:56 下午
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class UserServiceTest extends SpringbootApplicationTests {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void selectByUsername() {
        System.out.println(userService.selectByUsername("admin"));
        System.out.println(userService.getById("a"));
    }

    @Test
    public void register() {
    }

    @Test
    public void updateLastLoginTime() {
    }

    @Test
    public void selectUsers() {
    }

    @Test
    public void selectByUserId() {
    }

    @Test
    public void updateByUserId() {
    }

    @Test
    public void updateStatusBatch() {
    }

    @Test
    public void addAssignRole() {
    }

    @Test
    public void updateUserByPrimaryKey() {
    }

    @Test
    public void selectOnlineUsers() {
    }

    @Test
    public void kickout() {
    }
}
