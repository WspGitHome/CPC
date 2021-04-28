package com.wanfangdata.api.chain;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;



/**
 *
 * @Author FLY
 * @Date 2019-11-1
 *
 * */
public class MockFilterChain<T,K> implements Filter<T,K>{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private  List<Filter> filters;

    private Iterator<Filter> iterator;

    @Override
    public void doFilter(T in,K out) throws FilterException{

        Iterator<Filter> iterator = filters.iterator();
         while(iterator.hasNext()){
             Filter next = iterator.next();
             next.doFilter(in,out);
             if(logger.isDebugEnabled()){
                 logger.debug(next.getClass().getName());
                 logger.debug("INPUT:"+ JSON.toJSONString(in));
                 logger.debug("OUTPUT:"+ JSON.toJSONString(in));
             }
         }
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
