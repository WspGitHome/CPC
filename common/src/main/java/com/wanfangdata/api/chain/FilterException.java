package com.wanfangdata.api.chain;

/**
 *
 * @Author FLY
 * @Date 2019-11-1
 *
 * */
public class FilterException extends Exception{
    String feild;

    Enum type;

    public FilterException( String feild, Enum type) {
        this.feild = feild;
        this.type = type;
    }

    public FilterException(String message, Throwable cause, String feild, Enum type) {
        super(message, cause);
        this.feild = feild;
        this.type = type;
    }

    public enum Type{
        NULL,EMPTY;
    }
}
