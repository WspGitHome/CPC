package com.wanfangdata.cpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: LocalChronicleGrpcSearch88882
 * @description:
 * @author: FLY
 * @create: 2020-10-10 13:58
 */
@Getter
@AllArgsConstructor
public enum PropertyStatus {

    /**
     * 新上传
     */
    INIT(0, "新上传"),
    /**
     * 已标引
     */
    INDEXED(1, "已标引"),
    /**
     * 已上线
     */
    ONLINE(2, "已上线");

    private final Integer code;
    private final String message;
}