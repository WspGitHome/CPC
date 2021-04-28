package com.wanfangdata.cpc.module.admin;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Packagename com.wanfangdata.cpc.module.admin
 * @Classname UtilsTest
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/10 15:23
 * @Version 1.0
 */
public class UtilsTest {



    public static void main(String[] args) {
        String fq=String.join(":","aaa","aaa1");
        String facets=String.join(":","bbb");
        String facetPivot=String.join(":","cccc");
        String sort=String.join(":","dddd")+String.join(":","eeee");
        String cacheKey=String.join("#",fq,facets,facetPivot,sort,"ffff");
        System.out.println(cacheKey);
    }
}
