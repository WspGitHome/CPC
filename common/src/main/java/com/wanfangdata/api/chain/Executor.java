package com.wanfangdata.api.chain;


/**
 *
 * @Author FLY
 * @Date 2019-11-1
 *
 * */
public interface Executor<T,R> {
    /**
     * 执行器
     * */
    public void  execut(String core,T request, R response);

}
