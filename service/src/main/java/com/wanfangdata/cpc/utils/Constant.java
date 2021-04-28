package com.wanfangdata.cpc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constant {


	public static final Integer SUCCESS_CODE = 200;
	public static final Integer FAIL_CODE = 500;
	/**
	 * 资源类型
	 */
	public static final String PROPERTY = "FZCPCLocalChronicle,FZCPCLocalChronicleItem,FZCPCLocalChroniclePaper";
	public static final String ALIAS = "wwz,hswh,whz,jyz,rwz,xqz,fyz,mswh,jfjx,mzz";
	public static final String DEFAULT_QUREY = "-Id:1";
	public static final String separator="%";
	public static final String separator_column="/";
	public static final String ColumnId ="ColumnId";
	public static final String ColumnName ="ColumnName";
	public static final String LibraryId ="LibraryId";
	public static final String HL ="_HL";

	/**
	 * 所有域名
	 */
	public static final String DOMAIN_ALL = "wanfangdata.com.cn";
	/**
	 * 微信公众号Cookie中token的key
	 */
	public static final String LOGIN_TOKEN_COOKIE_KEY = "CASTGC";

	public final static int PAGE_SIZE=20;
	public final static int ROWS=10000;
	public static String  HighlightingPre ="<em style='color:#ef7d24;'>";
	public static String  HighlightingPost ="</em>";
	public static String  LocalChronicle ="LocalChronicle";
	public static String  LocalChronicleItemFulltext ="LocalChronicleItemFulltext";
	public static Integer USER_TYPE=0;//个人用户

	public static String  FZNEW ="FZ_New";
	public static String  FZOLD ="FZ_Old";
	public static String  ITEM ="ITEM";
	public static String  BOOK ="BOOK";
	public static String  READ ="READ";
	public static String  DOWNLOAD ="DOWNLOAD";

	/**
	 * solr字段映射
	 * */
	public static String  ID ="Id";
	public static String  DBID ="DBID";
	public static String  Page ="Page";
	public static String  PageCount ="PageCount";
	public static String  Title ="Title";
	public static String  NewTitle ="NewTitle";
	public static String  Content ="Article";
	public static String  BookId ="BookId";
	public static String  BookID ="BookID";
	public static String  BookTitle ="BookTitle";
	public static String  Introduction ="Introduction";
	public static String  Province ="Province";
	public static String  City ="City";
	public static String  County ="County";
	public static String  AlbumCategory ="AlbumCategory";
	public static String  Editor ="Editor";
	public static String  EditorA ="EditorA";
	public static String  EditorB ="EditorB";
	public static String  EditorC ="EditorC";
	public static String  Editorial ="Editorial";
	public static String  Edition ="Edition";
	public static String  Date ="Date";
	public static String  Dynasty ="Dynasty";
	public static String  VolumeSort ="VolumeSort";
	public static String  NO ="No";
	public static String  RealLevel ="RealLevel";
	public static String  ParentID ="ParentID";
	public static String  Volume ="Volume";
	public static String  BeginPara ="BeginPara";
	public static String  EndPara ="EndPara";
	public static String  Location ="Location";
	public static String  CategoryCode_ForSearch ="CategoryCode_ForSearch";
	public static String  CategoryCode_ForSearchSUBFIX ="CategoryCode_ForSearch:";
	public static String  CategoryCode ="CategoryCode";
	public static String  CategoryCodeSUBFIX ="CategoryCode:";
	public static String  ItemInfo ="ItemInfo";
	public static String  ChapterInfo ="ChapterInfo";
	public static String  SectionInfo ="SectionInfo";
	public static String  SheetInfo ="SheetInfo";
	public static String OnlineDate="OnlineDate";
	public static String FulltextOnlineDate="FulltextOnlineDate";
	public static String PublishDate="PublishDate";
	public static String MeetingDate="MeetingDate";

	private static Set<String> dateType=new HashSet<>();

	static {
		dateType.add(FulltextOnlineDate);
		dateType.add(OnlineDate);
		dateType.add(PublishDate);
		dateType.add(FulltextOnlineDate);
		dateType.add(MeetingDate);
		dateType.add(Date);
	}

	public static boolean isDateType(String date){
		return dateType.contains(date);
	}

	public  static List<String> catalogueReturnedField=new ArrayList<>();
	static {
		catalogueReturnedField.add(ID);
		catalogueReturnedField.add(Title);
		catalogueReturnedField.add(BookID);
		catalogueReturnedField.add(Page);
		catalogueReturnedField.add(PageCount);
		catalogueReturnedField.add(BeginPara);
		catalogueReturnedField.add(EndPara);
		catalogueReturnedField.add(NO);
		catalogueReturnedField.add(Location);
		catalogueReturnedField.add(ParentID);

	}
	public  static List<String> itemReturnedField=new ArrayList<>();
	static {
		itemReturnedField.add(ID);
		itemReturnedField.add(Title);
		itemReturnedField.add(BookID);
		itemReturnedField.add(BookTitle);
		itemReturnedField.add(Province);
		itemReturnedField.add(City);
		itemReturnedField.add(County);
		itemReturnedField.add(DBID);
		itemReturnedField.add(PageCount);
		itemReturnedField.add(Edition);
		itemReturnedField.add(Editor);
		itemReturnedField.add(Editorial);
		itemReturnedField.add(Date);
		itemReturnedField.add(Dynasty);
		itemReturnedField.add(CategoryCode_ForSearch);
	}
	public  static List<String> bookReturnedField=new ArrayList<>();
	static {
		bookReturnedField.add(ID);
		bookReturnedField.add(BookTitle);
		bookReturnedField.add(Province);
		bookReturnedField.add(City);
		bookReturnedField.add(County);
		bookReturnedField.add(DBID);
		bookReturnedField.add(PageCount);
		bookReturnedField.add(Edition);
		bookReturnedField.add(Editor);
		bookReturnedField.add(Editorial);
		bookReturnedField.add(Date);
		bookReturnedField.add(Dynasty);
		bookReturnedField.add(AlbumCategory);
	}
	public  static List<String> volumeReturnedField=new ArrayList<>();
	static {
		volumeReturnedField.add(ID);
		volumeReturnedField.add(Title);
		volumeReturnedField.add(NO);
		volumeReturnedField.add(VolumeSort);
		volumeReturnedField.add(PageCount);
	}

}
