package com.wanfangdata.cpc.module.admin.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.util
 * @ClassName: ClassUtil
 * @Description: 类的操作的工具类
 * @Author: rongrong
 * @CreateDate: 2020/8/24
 * @Version: 1.0
 */
public class ClassUtil {
    private ClassUtil() {
        throw new Error("工具类不允许实例化！");
    }


    /***
     * @description 获取类属性
     * @param targetObj
     * @return java.lang.reflect.Field[]
     * @authors rongrong
     * @date 2020/8/24
     * @modified by
     * @version 1.0
     */
    public static Field[] getClassAttribute(Object targetObj){

        Class<?> objectClass = targetObj.getClass();
        return objectClass.getDeclaredFields();

    }
    /***
     * @description 获取对象的所有get或set方法
     * @param targetObj 要获取属性的类
     * @param methodKeyword get或者set关键字
     * @return java.util.List<java.lang.reflect.Method>
     * @authors rongrong
     * @date 2020/8/24
     * @modified by
     * @version 1.0
     */
    public static List<Method> getMethod(Object targetObj,String methodKeyword){
        List<Method> methodList = new ArrayList<>();

        Class<?> objectClass = targetObj.getClass();

        Field[] field = objectClass.getDeclaredFields();
        for (int i = 0;i<field.length;i++){
            //获取属性名并组装方法名
            String fieldName = field[i].getName();
            String getMethodName = methodKeyword
                    + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);

            try {
                Method method = objectClass.getMethod(getMethodName,new Class[]{});
                methodList.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return methodList;
    }

    /***
     * @description 获取对象的所有get方法
     * @param targetObj 要获取属性的类
     * @return java.util.List<java.lang.reflect.Method>
     * @authors rongrong
     * @date 2020/8/24
     * @modified by
     * @version 1.0
     */
    public static List<Method> getMethodGet(Object targetObj){
        return getMethod(targetObj,"get");
    }

    /***
     * @description 获取对象的所有set方法
     * @param targetObj 要获取属性的类
     * @return java.util.List<java.lang.reflect.Method>
     * @authors rongrong
     * @date 2020/8/24
     * @modified by
     * @version 1.0
     */
    public static List<Method> getMethodSet(Object targetObj){
        return getMethod(targetObj,"set");
    }
}
