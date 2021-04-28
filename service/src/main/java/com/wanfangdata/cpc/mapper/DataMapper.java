package com.wanfangdata.cpc.mapper;

import com.wanfangdata.cpc.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author FLY
 * @date 2019年7月19日
 */
@Mapper
public interface DataMapper {

    @Select("select t.*,l.name db_name  from db_template t,db_library l  where t.db_id=l.id and l.alias_path=#{alias}")
    Template getTemplate(@Param("alias")String alias);
    /**
     *
     * 根据别名获取Id
     * */
    @Select("select id from db_library l where l.alias_path=#{alias}")
    Integer getLibraryId(@Param("alias")String alias);
    /**
     * 获取栏目导航
     * */
    @Select("SELECT  l.id as library_id,l.name as library_name,c.id as id,c.pid as pid,c.name as name,c.property_id as property_id,p.property_solr_alias as property_solr_alias,c.sort,c.description as description from db_library l LEFT JOIN db_column c on c.library_id=l.id LEFT JOIN db_property p on p.id=c.property_id where c.pid=0 and l.alias_path=#{alias}  ORDER BY c.sort")
    List<Column> columnsByAlias(@Param("alias")String alias);

    /**
     * 获取数据库下所有栏目
     * */
    @Select("SELECT  c.id as id,c.pid as pid,c.name as name,c.property_id as property_id,c.description as description,p.property_solr_alias as property_solr_alias,c.picture1,c.picture2,c.sort,library_id,l.name library_name  from db_column c left join db_library l on l.id=c.library_id  LEFT JOIN db_property p on p.id=c.property_id where c.library_id=#{libraryId}  ORDER BY c.sort")
    List<Column> columnTreeByLibraryId(@Param("libraryId")Integer libraryId);

    /**
     * 获取栏目导航
     * */
    @Select("select c.id as id,c.pid as pid,c.name as name,c.property_id as property_id,c.sort,c.description as description,p.property_solr_alias as property_solr_alias from db_column c  LEFT JOIN db_property p on p.id=c.property_id  where c.pid=#{pid}  ORDER BY c.sort")
    List<Column> columnsByPid(@Param("pid")Integer pid);
    /**
     * 获取所有资源类型，及其对应的solr库
     * */
    @Select("select t.id,t.property_name,t.property_table_name,t.property_solr_alias from db_property t")
    List<Property> property();

    /**
     * 获取所有资源类型，及其对应的solr库
     * */
    @Select("select k.id,k.order_num,k.keywords,k.keyword_name,c.name as column_name from db_keyword k  left join db_column c on c.id=k.column_id  where k.column_id=#{columnId}  order by order_num  limit #{start},#{size}")
    List<Keyword> keywords(@Param("columnId")Integer columnId, @Param("start")Integer start, @Param("size")Integer size);
    /**
     *获取关键词总数
     * */
    @Select("select count(id) from db_keyword k  where k.column_id=#{columnId}")
    Integer keywordsCount(@Param("columnId")Integer columnId);
    /**
     *获取内容列表
     * */
    @Select("select c.id,c.title,c.author,c.source,c.url,c.introduction,c.picture,c.content_details,c.update_time from db_content c where c.column_id=#{columnId}  order by update_time limit #{start},#{size} ")
    List<Article> articles(@Param("columnId")Integer columnId, @Param("start")Integer start, @Param("size")Integer size);
    /**
     *获取内容总数
     * */
    @Select("select count(id) from db_content c where c.column_id=#{columnId} ")
    Integer articlesCount(@Param("columnId")Integer columnId);

    /**
     *获取内容
     * */
    @Select("select c.id,c.title,c.author,c.source,c.url,c.introduction,c.content_details,c.update_time from db_content c where c.id=#{contentId}")
    Article article(@Param("contentId")Integer contentId);

    @Select("select t.id,t.name,t.alias_path from db_library t")
    List<Library> library();
}
