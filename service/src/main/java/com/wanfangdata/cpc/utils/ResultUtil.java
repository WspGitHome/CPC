package com.wanfangdata.cpc.utils;

import com.wanfangdata.cpc.model.ResponseModel;
import lombok.experimental.UtilityClass;

/**
 * 返回结果封装对象
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@UtilityClass
public class ResultUtil {

    public static ResponseModel success() {
        return vo(Constant.SUCCESS_CODE, null, null);
    }

    public static ResponseModel success(String msg) {
        return vo(Constant.SUCCESS_CODE, msg, null);
    }

    public static ResponseModel success(String msg, Object data) {
        return vo(Constant.SUCCESS_CODE, msg, data);
    }

    public static ResponseModel error() {
        return vo(Constant.FAIL_CODE, null, null);
    }

    public static ResponseModel error(String msg) {
        return vo(Constant.FAIL_CODE, msg, null);
    }

    public static ResponseModel error(String msg, Object data) {
        return vo(Constant.FAIL_CODE, msg, data);
    }


    public static ResponseModel vo(Integer status, String message, Object data) {
        return new ResponseModel<>(status, message, data);
    }


}
