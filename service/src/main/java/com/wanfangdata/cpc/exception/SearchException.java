package com.wanfangdata.cpc.exception;

import lombok.Data;

/**
 *
 *@author FLY
 *@date 2019年7月19日
 *
 */
@Data
public class SearchException extends Exception{

    private String msg;
    private String collection;
    private Exception exception;


    public SearchException(String msg, String collection){
        this.msg=msg;
        this.collection=collection;
    }
    public SearchException(String msg, String collection,Exception exception){
        this.msg=msg;
        this.collection=collection;
        this.exception=exception;

    }
}