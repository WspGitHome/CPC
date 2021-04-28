package com.wanfangdata.oss.model;
/**
 *@program: Fulltext
 *@description: 旧方志目录
 *@author: FLY
 *@create: 2020-05-13 10:16
 */

public class VolumeNode {

    private String id;
    private String title;
    private String no;
    private String volumeSort;
    private String pageCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getVolumeSort() {
        return volumeSort;
    }

    public void setVolumeSort(String volumeSort) {
        this.volumeSort = volumeSort;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}