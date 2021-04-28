package com.wanfangdata.grpc.server.db.dbmapper;

import com.wanfangdata.grpc.server.db.dbentity.Column;
import com.wanfangdata.grpc.server.db.dbentity.Library;
import com.wanfangdata.grpc.server.db.dbentity.Property;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Packagename com.wanfangdata.grpc.server.db.dbmapper
 * @Classname DataMapper
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/09/02 16:11
 * @Version 1.0
 */
@Mapper
public interface DataMapper {

    /**
     * 根据别名获取Id
     */
    @Select("select id from db_library l where l.alias_path=#{alias}")
    Integer getLibraryId(@Param("alias") String alias);

    /**
     * 获取栏目导航
     */
    @Select("SELECT  l.id as library_id,l.name as library_name,c.id as id,c.pid as pid,c.name as name,c.property_id as property_id,c.sort,c.description as description from db_library l LEFT JOIN db_column c on c.library_id=l.id where c.pid=0 and l.alias_path=#{alias}  ORDER BY c.sort")
    List<Column> columnsByAlias(@Param("alias") String alias);

    /**
     * 获取数据库下所有栏目
     */
    @Select("SELECT  c.id as id,c.pid as pid,c.name as name,c.property_id as property_id,c.description as description,c.picture1,c.picture2,c.sort,library_id,l.name library_name  from db_column c left join db_library l on l.id=c.library_id where c.library_id=#{libraryId}  ORDER BY c.sort")
    List<Column> columnTreeByLibraryId(@Param("libraryId") Integer libraryId);

    /**
     * 获取栏目导航
     */
    @Select("select c.id as id,c.pid as pid,c.name as name,c.property_id as property_id,c.sort,c.description as description from db_column c  where c.pid=#{pid}  ORDER BY c.sort")
    List<Column> columnsByPid(@Param("pid") Integer pid);

    /**
     * 获取所有资源类型，及其对应的solr库
     */
    @Select("select t.id,t.property_name,t.property_table_name,t.property_solr_alias from db_property t")
    List<Property> property();


    @Select("select t.id,t.name,t.alias_path from db_library t")
    List<Library> library();
}
