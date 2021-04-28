package com.wanfangdata.cpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringbootApplicationTests {

    @Test
    public void contextLoads() {
        String regEx="[\\s~·`!！@#$%^……&*()\\-——\\-_=+\\[\\]{}\\|\\;:‘'“”\"，,<。.>、/?]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("aaaa#aaa");
        System.out.println(m.replaceAll(""));
    }

}
