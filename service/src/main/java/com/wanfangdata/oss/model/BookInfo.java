package com.wanfangdata.oss.model;/**
 * @author FLY
 * @date 2019年7月19日
 */

import com.wanfangdata.cpc.utils.Constant;
import com.wanfangdata.cpc.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *@program: Fulltext
 *@description: 方志志书元信息
 *@author: FLY
 *@create: 2020-05-12 12:28
 */
public class BookInfo implements Serializable {

    private String id;
    //存因不存果，只把计算元数据存储
    private String location;
    private int locationTotalPage=0;
    private String location01;
    private int location01TotalPage=0;
    private String location02;
    private int location02TotalPage=0;

    //获取时设置不序列化
    //有0102的时候01总页数也就是02的偏移量
    private transient int offset=0;
    //该总页码需加上offset
    private transient int totalPage=0;

    /**
     *
     * 根据显示的书籍页数，获取真实存储位置和页码
     * */
    public Position getPositionn(String page){
        //页码校验 1<page<totalPage
        int p=Integer.parseInt(page);
        if(p<1)p=1;
        if(p>totalPage)p=totalPage;

        //只有02，该情况最多
        if(StringUtils.isNotEmpty(location02)&& StringUtils.isEmpty(location01)){
            return new Position(location02,String.valueOf(p));
        }
        //只有0102,涉及页码切换
        if(StringUtils.isNotEmpty(location02)&& StringUtils.isNotEmpty(location01)){
            if(p<=getOffset()){
                return new Position(location01,String.valueOf(p));
            }
            return new Position(location02,String.valueOf(p-getOffset()));
        }
        //只有0 此情况最少
        if(StringUtils.isNotEmpty(location)){
            return new Position(location,String.valueOf(p));
        }
        //只有01 此情况最少
        if(StringUtils.isNotEmpty(location01)){
            return new Position(location01,String.valueOf(p));
        }
        //默认返回0
        return new Position(id,String.valueOf(p));
    }
    /**
     * 获取条目前端偏移初始地址,只有01 02都存在该方法才有意义
     * */
    public int getInitPage(Map<String, String> document){
        //条目第一页，由于BeginPara 可能为空
        String[] pages=document.get(Constant.Page).split("-");
        String initPage=document.get(Constant.BeginPara);
        if(pages.length==2){
            initPage=pages[0];
        }
        String location=document.get(Constant.Location);
        if(location.equals(location02)&&location01!=null){
            return Integer.parseInt(initPage)+getOffset();
        }
        return Integer.parseInt(initPage);
    }

    /**
     * 获取条目前端偏移结束地址,只有01 02都存在该方法才有意义
     * */
    public int getFinishPage(Map<String, String> document){
        //条目第一页，由于BeginPara 可能为空
        String[] pages=document.get(Constant.Page).split("-");
        String finishPage=document.get(Constant.EndPara);
        if(pages.length==2){
            finishPage=pages[1];
        }
        String location=document.get(Constant.Location);
        if(location.equals(location02)&&location01!=null){
            return Integer.parseInt(finishPage)+offset;
        }
        return Integer.parseInt(finishPage);
    }

    //获取总页码
    public int getTotalPage(){
        if(totalPage!=0){
            return totalPage;
        }
        this.totalPage=locationTotalPage+location01TotalPage+location02TotalPage;
        return totalPage;
    }

    //只有01 02同时存在才有偏移
    public int getOffset(){
        if(location01!=null&location02!=null){
            this.offset=location01TotalPage;
            return offset;
        }
        return offset;
    }

    //初始化书籍信息
    public void setBookInfo(String location,String endPage){
        int end=Integer.parseInt(endPage);
        //有01就有偏移量
        if(location.lastIndexOf("_01")!=-1){
            this.location01=location;
            if(end>location01TotalPage){
                this.location01TotalPage=end;
            }
            return;
        }
        if(location.lastIndexOf("_02")!=-1){
            this.location02=location;
            if(end>location02TotalPage){
                this.location02TotalPage=end;
            }
            return;
        }
        this.location=location;
        if(end>locationTotalPage){
            this.locationTotalPage=end;
        }
        return;
    }


    public void getItemInfo(List<CatalogueNode> catalogues, String page){
        Position position=getPositionn(page);
        catalogues.stream().forEach(catalogueNode -> {
            String location= StringUtil.nvl(position.getLocation());
            int realPage=Integer.parseInt(position.getRealPage());
            int startPage=Integer.parseInt(catalogueNode.getStartPara());
            int finishPage=Integer.parseInt(catalogueNode.getFinishPara());
            if(location.equals(catalogueNode.getLocation())&&realPage>=startPage&&realPage<=finishPage){

            }
        });
    }

    public class Position{
        String location;
        String realPage;
        public Position(String location,String realPage){this.location=location;this.realPage=realPage;}

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getRealPage() {
            return realPage;
        }

        public void setRealPage(String realPage) {
            this.realPage = realPage;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocationTotalPage() {
        return locationTotalPage;
    }

    public void setLocationTotalPage(int locationTotalPage) {
        this.locationTotalPage = locationTotalPage;
    }

    public String getLocation01() {
        return location01;
    }

    public void setLocation01(String location01) {
        this.location01 = location01;
    }

    public int getLocation01TotalPage() {
        return location01TotalPage;
    }

    public void setLocation01TotalPage(int location01TotalPage) {
        this.location01TotalPage = location01TotalPage;
    }

    public String getLocation02() {
        return location02;
    }

    public void setLocation02(String location02) {
        this.location02 = location02;
    }

    public int getLocation02TotalPage() {
        return location02TotalPage;
    }

    public void setLocation02TotalPage(int location02TotalPage) {
        this.location02TotalPage = location02TotalPage;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
