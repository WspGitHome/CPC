package com.wanfangdata.grpc.server.query.chain.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author FLY
 * @date 2019-11-1
 *
 * */
public class Catalog<T> implements Serializable{
    private T item;
    private List<Catalog<T>> subItemList;

    public Catalog(T item, List<Catalog<T>> subItem) {
        this.item = item;
        this.subItemList = subItem;
    }
    public Catalog() {
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public List<Catalog<T>> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<Catalog<T>> subItemList) {
        this.subItemList = subItemList;
    }
}
