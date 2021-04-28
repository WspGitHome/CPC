package com.wanfangdata.cpc.module.admin.service;

import com.wanfangdata.cpc.SpringbootApplicationTests;
import com.wanfangdata.cpc.module.admin.mapper.db.DbPropertyMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: linzhaoguan
 * @date: 2019/9/11 1:56 下午
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest extends SpringbootApplicationTests {

    @Autowired
    private DbPropertyMapper dbPropertyMapper;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void selectSchma() {
        List<Map<String ,Object>> list=dbPropertyMapper.selectSchama("localChronicle");
        System.out.println(list.toString());
    }
    @Test
    public void selectByPage() {
        Date date=new Date();
        date.setYear(10);
        List<Map<String ,Object>> list=dbPropertyMapper.findByPage("localChronicle",1,2,1);
        System.out.println(list.toString());
    }

    @Test
    public void selectCount() {
        Integer list=dbPropertyMapper.selectCount("localChronicle",1);
        System.out.println(list.toString());
    }

}
