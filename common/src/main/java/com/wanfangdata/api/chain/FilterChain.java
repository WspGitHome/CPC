package com.wanfangdata.api.chain;


/**
 *
 * @Author FLY
 * @Date 2019-11-1
 *
 * */
public interface FilterChain<T,K> {
    /**
     * 过滤器
     * */
    public void doFilter(T in,K out) throws FilterException;

}
