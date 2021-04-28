package com.wanfangdata.cpc.common.util;

import lombok.experimental.UtilityClass;

/**
 * 常量工具类
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@UtilityClass
public class CoreConst {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 500;
    public static final Integer STATUS_VALID = 1;
    public static final Integer STATUS_INVALID = 0;
    public static final Integer PAGE_SIZE = 10;
    public static final Integer TOP_MENU_ID = 0;
    public static final String TOP_MENU_NAME = "顶层菜单";
    public static final String SHIRO_REDIS_SESSION_PREFIX = "cpc_admin:session:";
    public static final String SHIRO_REDIS_CACHE_NAME = "shiro_cpc_admin";

    public static final String ADMIN_PREFIX = "admin/";

    /**
     * 类型 1：前端模板路径  2：文章资源路径  3：栏目资源路径  4：资源导入路径 5.其他
     */
    public static final int UPLOAD_TYPE_FORMWORK = 1;
    public static final int UPLOAD_TYPE_CONTENT = 2;
    public static final int UPLOAD_TYPE_COLUMN = 3;
    public static final int UPLOAD_TYPE_PROPERTY = 4;
    public static final int UPLOAD_TYPE_OTHER = 5;

    //前端模板路径
    public final static String FORMWORK = "formwork";
    //文章资源路径
    public final static String CONTENT = "content";
    //栏目资源路径
    public final static String COLUMN = "column";
    //资源导入路径
    public final static String PROPERTY = "property";
    //其他
    public final static String OTHER = "other";
}
