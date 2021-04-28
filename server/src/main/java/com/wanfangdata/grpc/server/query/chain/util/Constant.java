package com.wanfangdata.grpc.server.query.chain.util;

public class Constant {
	/**
	 * 检索每页数量
	 * */
	public static Integer pageSize=1;

	/**
	 * solr默认ID
	 * */
	public static String  ID ="Id";
	/**
	 * solr字段CategoryCode_ForSearch
	 * */
	public static String  CategoryCode_ForSearch ="CategoryCode_ForSearch";
	public static String  CategoryCode_ForSearchSUBFIX ="CategoryCode_ForSearch:";
	public static String  CategoryCode ="CategoryCode";
	public static String  CategoryCodeSUBFIX ="CategoryCode:";
	/**
	 *
	 * */
	public static String  FZNEW ="FZ_New";
	public static String  FZOLD ="FZ_Old";
	public static String  ITEM ="ITEM";
	public static String  BOOK ="BOOK";
	/**
	 * solr字段映射
	 * */
	public static String  DBID ="DBID";
	public static String  Page ="Page";
	public static String  PageCount ="PageCount";
	public static String  Title ="Title";
	public static String  BookID ="BookID";
	public static String  BookTitle ="BookTitle";
	public static String  Province ="Province";
	public static String  City ="City";
	public static String  County ="County";
	public static String  AlbumCategory ="AlbumCategory";
	public static String  Editor ="Editor";
	public static String  Editorial ="Editorial";
	public static String  Edition ="Edition";
	public static String  Date ="Date";
	public static String  Dynasty ="Dynasty";
	public static String  VolumeSort ="VolumeSort";
	public static String  NO ="No";
	public static String  RealLevel ="RealLevel";
	public static String  Content ="Content";
	public static String  ParentID ="ParentID";
	public static String  Volume ="Volume";
	public static String  ColumnId ="ColumnId";

	/**
	 * 资源类型
	 */
	public static final String PROPERTY = "FZCPCLocalChronicle,FZCPCLocalChronicleItem,FZCPCPeriodical,FZCPCPaper,FZCPCConference";
	public static final String ALIAS = "wwz,hswh,whz,jyz,rwz,xqz,fyz,mswh";
	public static final String DEFAULT_QUREY = "DBID:FZ_New";
	public static final String separator="%";
	public static final String separator_column="/";
	public static final String separator_asterisk="*";
	public static final String separator_wei="#";
	public static final String ColumnName ="ColumnName";
	public static final String LibraryId ="LibraryId";
	public static final String HL ="_HL";

}
