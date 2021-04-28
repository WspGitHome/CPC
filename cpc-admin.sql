-- --------------------------------------------------------
-- 主机:                           10.10.184.172
-- 服务器版本:                        5.7.30 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 cpc-admin 的数据库结构
CREATE DATABASE IF NOT EXISTS `cpc-admin` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cpc-admin`;

-- 导出  表 cpc-admin.biz_article 结构
CREATE TABLE IF NOT EXISTS `biz_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '文章封面图片',
  `qrcode_path` varchar(255) DEFAULT NULL COMMENT '文章专属二维码地址',
  `is_markdown` tinyint(1) unsigned DEFAULT '1',
  `content` longtext COMMENT '文章内容',
  `content_md` longtext COMMENT 'markdown版的文章内容',
  `top` tinyint(1) unsigned DEFAULT '0' COMMENT '是否置顶',
  `category_id` int(11) unsigned DEFAULT NULL COMMENT '类型',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '状态',
  `recommended` tinyint(1) unsigned DEFAULT '0' COMMENT '是否推荐',
  `slider` tinyint(1) unsigned DEFAULT '0' COMMENT '是否轮播',
  `slider_img` varchar(255) DEFAULT NULL COMMENT '轮播图地址',
  `original` tinyint(1) unsigned DEFAULT '1' COMMENT '是否原创',
  `description` varchar(300) DEFAULT NULL COMMENT '文章简介，最多200字',
  `keywords` varchar(200) DEFAULT NULL COMMENT '文章关键字，优化搜索',
  `comment` tinyint(1) unsigned DEFAULT '1' COMMENT '是否开启评论',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_article_look 结构
CREATE TABLE IF NOT EXISTS `biz_article_look` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(20) unsigned NOT NULL COMMENT '文章ID',
  `user_id` varchar(20) DEFAULT NULL COMMENT '已登录用户ID',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户IP',
  `look_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_article_tags 结构
CREATE TABLE IF NOT EXISTS `biz_article_tags` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) unsigned NOT NULL COMMENT '标签表主键',
  `article_id` int(11) unsigned NOT NULL COMMENT '文章ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_category 结构
CREATE TABLE IF NOT EXISTS `biz_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(11) unsigned DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '文章类型名',
  `description` varchar(200) DEFAULT NULL COMMENT '类型介绍',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `status` tinyint(1) unsigned DEFAULT '1' COMMENT '是否可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_comment 结构
CREATE TABLE IF NOT EXISTS `biz_comment` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `sid` int(20) DEFAULT NULL COMMENT '被评论的文章或者页面的ID(-1:留言板)',
  `user_id` varchar(20) DEFAULT NULL COMMENT '评论人的ID',
  `pid` int(20) unsigned DEFAULT NULL COMMENT '父级评论的id',
  `qq` varchar(13) DEFAULT NULL COMMENT '评论人的QQ（未登录用户）',
  `nickname` varchar(13) DEFAULT NULL COMMENT '评论人的昵称（未登录用户）',
  `avatar` varchar(255) DEFAULT NULL COMMENT '评论人的头像地址',
  `email` varchar(100) DEFAULT NULL COMMENT '评论人的邮箱地址（未登录用户）',
  `url` varchar(200) DEFAULT NULL COMMENT '评论人的网站地址（未登录用户）',
  `status` tinyint(1) DEFAULT '0' COMMENT '评论的状态',
  `ip` varchar(64) DEFAULT NULL COMMENT '评论时的ip',
  `lng` varchar(50) DEFAULT NULL COMMENT '经度',
  `lat` varchar(50) DEFAULT NULL COMMENT '纬度',
  `address` varchar(100) DEFAULT NULL COMMENT '评论时的地址',
  `os` varchar(64) DEFAULT NULL COMMENT '评论时的系统类型',
  `os_short_name` varchar(10) DEFAULT NULL COMMENT '评论时的系统的简称',
  `browser` varchar(64) DEFAULT NULL COMMENT '评论时的浏览器类型',
  `browser_short_name` varchar(10) DEFAULT NULL COMMENT '评论时的浏览器的简称',
  `content` varchar(2000) DEFAULT NULL COMMENT '评论的内容',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注（审核不通过时添加）',
  `support` int(10) unsigned DEFAULT '0' COMMENT '支持（赞）',
  `oppose` int(10) unsigned DEFAULT '0' COMMENT '反对（踩）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_link 结构
CREATE TABLE IF NOT EXISTS `biz_link` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '链接名',
  `url` varchar(200) NOT NULL COMMENT '链接地址',
  `description` varchar(255) DEFAULT NULL COMMENT '链接介绍',
  `img` varchar(255) DEFAULT NULL COMMENT '友链图片地址',
  `email` varchar(100) DEFAULT NULL COMMENT '友链站长邮箱',
  `qq` varchar(50) DEFAULT NULL COMMENT '友链站长qq',
  `status` int(1) unsigned DEFAULT NULL COMMENT '状态',
  `origin` int(1) DEFAULT NULL COMMENT '1-管理员添加 2-自助申请',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_love 结构
CREATE TABLE IF NOT EXISTS `biz_love` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `biz_id` int(11) unsigned NOT NULL COMMENT '业务ID',
  `biz_type` tinyint(1) DEFAULT NULL COMMENT '业务类型：1.文章，2.评论',
  `user_id` varchar(20) DEFAULT NULL COMMENT '已登录用户ID',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户IP',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_site_info 结构
CREATE TABLE IF NOT EXISTS `biz_site_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site_name` varchar(255) DEFAULT NULL,
  `site_desc` varchar(255) DEFAULT NULL,
  `site_pic` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_tags 结构
CREATE TABLE IF NOT EXISTS `biz_tags` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '书签名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.biz_theme 结构
CREATE TABLE IF NOT EXISTS `biz_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `name` varchar(50) DEFAULT NULL COMMENT '主题名（路径前缀）',
  `description` varchar(255) DEFAULT NULL COMMENT '主题描述',
  `img` varchar(255) DEFAULT NULL COMMENT '主题预览图url',
  `status` tinyint(1) DEFAULT NULL COMMENT '0-未启用 1-启用',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_column 结构
CREATE TABLE IF NOT EXISTS `db_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `library_id` int(11) DEFAULT NULL COMMENT '所属数据库id',
  `property_id` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '栏目名称',
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '类型介绍',
  `picture1` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '栏目背景图',
  `picture2` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '栏目封面',
  `add_user` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_user` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=682 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_column_copy 结构
CREATE TABLE IF NOT EXISTS `db_column_copy` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(11) unsigned DEFAULT NULL,
  `library_id` int(11) DEFAULT NULL COMMENT '所属数据库id',
  `property_id` int(11) DEFAULT NULL,
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `name` varchar(50) DEFAULT NULL COMMENT '栏目名称',
  `description` varchar(200) DEFAULT NULL COMMENT '类型介绍',
  `picture1` varchar(255) DEFAULT NULL COMMENT '栏目背景图',
  `picture2` varchar(255) DEFAULT NULL COMMENT '栏目封面',
  `add_user` varchar(255) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=617 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_column_copy1 结构
CREATE TABLE IF NOT EXISTS `db_column_copy1` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(11) unsigned DEFAULT NULL,
  `library_id` int(11) DEFAULT NULL COMMENT '所属数据库id',
  `property_id` int(11) DEFAULT NULL,
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `name` varchar(50) DEFAULT NULL COMMENT '栏目名称',
  `description` varchar(200) DEFAULT NULL COMMENT '类型介绍',
  `picture1` varchar(255) DEFAULT NULL COMMENT '栏目背景图',
  `picture2` varchar(255) DEFAULT NULL COMMENT '栏目封面',
  `add_user` varchar(255) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=654 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_config 结构
CREATE TABLE IF NOT EXISTS `db_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_content 结构
CREATE TABLE IF NOT EXISTS `db_content` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `db_type` int(255) DEFAULT NULL COMMENT '数据库类型 1专辑库2 专题库',
  `library_id` int(255) DEFAULT NULL COMMENT '数据库名称',
  `column_id` int(255) DEFAULT NULL COMMENT '栏目名称',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `source` varchar(255) DEFAULT NULL COMMENT '来源',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片',
  `introduction` text COMMENT '内容简介',
  `content_details` text COMMENT '内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_frontmodule 结构
CREATE TABLE IF NOT EXISTS `db_frontmodule` (
  `id` int(11) NOT NULL,
  `code` varchar(10) NOT NULL DEFAULT '' COMMENT '页面位置对应码',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '页面位置名称(原型为基础)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_keyword 结构
CREATE TABLE IF NOT EXISTS `db_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `db_type` int(11) DEFAULT NULL COMMENT '数据库类型id  1专辑库 2 专题库',
  `library_id` int(11) DEFAULT NULL COMMENT '数据库id',
  `column_id` int(11) DEFAULT NULL COMMENT '栏目id',
  `keywords` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '关键词',
  `keyword_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '关键词名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_library 结构
CREATE TABLE IF NOT EXISTS `db_library` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库Id',
  `type` tinyint(11) DEFAULT NULL COMMENT '数据库类别',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `en_name` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `alias_path` varchar(255) DEFAULT NULL COMMENT '访问路径',
  `introduction` longtext COMMENT '简介',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_libtype 结构
CREATE TABLE IF NOT EXISTS `db_libtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库类型id',
  `name` varchar(255) DEFAULT NULL COMMENT '数据库类型名称',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_property 结构
CREATE TABLE IF NOT EXISTS `db_property` (
  `id` int(30) NOT NULL DEFAULT '-1',
  `property_name` varchar(100) DEFAULT NULL,
  `property_table_name` varchar(50) DEFAULT NULL,
  `property_solr_alias` varchar(255) DEFAULT NULL COMMENT 'solr库别名',
  `add_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_create` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_property_import 结构
CREATE TABLE IF NOT EXISTS `db_property_import` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL COMMENT '上传文件名',
  `file_name_real` varchar(255) DEFAULT NULL COMMENT '在服务器文件名',
  `file_path` varchar(255) DEFAULT NULL COMMENT '文件相对路径',
  `status` varchar(1) DEFAULT NULL COMMENT '状态:已上传已标引已上线',
  `library_id` int(11) DEFAULT NULL COMMENT '所属数据库',
  `property_id` int(11) DEFAULT NULL COMMENT '资源类型',
  `update_time` datetime DEFAULT NULL COMMENT '导入历史',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_property_info 结构
CREATE TABLE IF NOT EXISTS `db_property_info` (
  `ID` int(11) NOT NULL DEFAULT '-1',
  `property_id` int(11) DEFAULT NULL,
  `property_china_column` varchar(60) DEFAULT NULL,
  `property_column` varchar(60) DEFAULT NULL,
  `property_length` varchar(14) DEFAULT NULL,
  `property_type` varchar(20) DEFAULT NULL,
  `is_null` varchar(1) DEFAULT NULL COMMENT '0=不能为空 1=能为空',
  `property_sort` int(11) DEFAULT NULL,
  `break` varchar(500) DEFAULT NULL,
  `add_user` varchar(30) DEFAULT NULL,
  `add_date` datetime DEFAULT NULL,
  `upd_user` varchar(30) DEFAULT NULL,
  `upd_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源详情表';

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_property_modify 结构
CREATE TABLE IF NOT EXISTS `db_property_modify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `property_type` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '资源类型',
  `property_id` varchar(11) DEFAULT NULL COMMENT '修改的资源id',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_template 结构
CREATE TABLE IF NOT EXISTS `db_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `db_id` int(11) DEFAULT NULL COMMENT '数据库id',
  `template_id` int(11) DEFAULT NULL COMMENT '模板id',
  `big_album_logo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '专辑大logo',
  `small_album_logo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '专辑小logo',
  `homepage_picture` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '首页图片',
  `second_level_picture` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '二级页面图片',
  `thematic_classification` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '专题分类',
  `classification_picture` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '专题分类图片',
  `area_classification` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '地区分类',
  `area_classification_picture` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '地区分类图片',
  `resource_type_book_read` varchar(255) DEFAULT NULL COMMENT '资源类型',
  `resource_type_item_read` varchar(255) DEFAULT NULL,
  `resource_type_item_download` varchar(255) DEFAULT NULL,
  `button_color` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '按钮颜色',
  `click_color` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '点击颜色',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.db_template_info 结构
CREATE TABLE IF NOT EXISTS `db_template_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `template_name` varchar(50) NOT NULL COMMENT '模板名称',
  `template_url` varchar(50) NOT NULL COMMENT '模板相对地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `big_album_logo` varchar(255) DEFAULT NULL COMMENT '专辑大logo',
  `small_album_logo` varchar(255) DEFAULT NULL COMMENT '专辑小logo',
  `homepage_picture` varchar(255) DEFAULT NULL COMMENT '首页图片',
  `second_level_picture` varchar(255) DEFAULT NULL COMMENT '二级页面图片',
  `classification_picture` varchar(255) DEFAULT NULL COMMENT '专题分类图片',
  `area_classification_picture` varchar(255) DEFAULT NULL COMMENT '地区分类图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.localchronicle 结构
CREATE TABLE IF NOT EXISTS `localchronicle` (
  `Id` varchar(100) NOT NULL COMMENT 'id',
  `NewTitle` varchar(255) DEFAULT NULL,
  `BookTitle` longtext COMMENT '志书题名',
  `Introduction` longtext COMMENT '简介',
  `Editorial` longtext COMMENT '编纂单位',
  `Editor` longtext COMMENT '编纂人员',
  `Publisher` longtext COMMENT '出版单位',
  `Date` varchar(30) DEFAULT NULL,
  `Year` varchar(12) DEFAULT NULL,
  `ISBN` varchar(150) DEFAULT NULL,
  `TimeLimit` varchar(150) DEFAULT NULL,
  `IsNational` varchar(150) DEFAULT NULL,
  `Province` longtext COMMENT '省',
  `City` varchar(150) DEFAULT NULL COMMENT '市',
  `County` varchar(150) DEFAULT NULL COMMENT '县',
  `RegionCode` varchar(150) DEFAULT NULL,
  `RegionCode_ForSearch` longtext,
  `CategoryCode` varchar(150) DEFAULT NULL,
  `CategoryCode_ForSearch` longtext,
  `CategoryLevel` varchar(150) DEFAULT NULL,
  `RegionLevel` varchar(150) DEFAULT NULL,
  `AlbumCategory` longtext,
  `Keywords` longtext COMMENT '关键词',
  `Volume` longtext,
  `Dynasty` longtext,
  `ReignTitle` longtext,
  `ReignYear` longtext,
  `Edition` longtext,
  `PageCount` varchar(11) DEFAULT NULL,
  `Years_ForSearch` longtext,
  `DBID` varchar(30) DEFAULT NULL,
  `OnlineDate` varchar(30) DEFAULT NULL,
  `BoughtUserId` varchar(255) DEFAULT NULL,
  `LibraryId` varchar(255) DEFAULT NULL COMMENT '数据库id',
  `ColumnId` varchar(255) DEFAULT NULL COMMENT '栏目号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `BatchId` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.localChronicle 结构
CREATE TABLE IF NOT EXISTS `localChronicle` (
  `Id` varchar(100) NOT NULL COMMENT 'id',
  `NewTitle` varchar(255) DEFAULT NULL,
  `BookTitle` longtext COMMENT '志书题名',
  `Introduction` longtext COMMENT '简介',
  `Editorial` longtext COMMENT '编纂单位',
  `Editor` longtext COMMENT '编纂人员',
  `Publisher` longtext COMMENT '出版单位',
  `Date` varchar(30) DEFAULT NULL,
  `Year` varchar(12) DEFAULT NULL,
  `ISBN` varchar(150) DEFAULT NULL,
  `TimeLimit` varchar(150) DEFAULT NULL,
  `IsNational` varchar(150) DEFAULT NULL,
  `Province` longtext COMMENT '省',
  `City` varchar(150) DEFAULT NULL COMMENT '市',
  `County` varchar(150) DEFAULT NULL COMMENT '县',
  `RegionCode` varchar(150) DEFAULT NULL,
  `RegionCode_ForSearch` longtext,
  `CategoryCode` varchar(150) DEFAULT NULL,
  `CategoryCode_ForSearch` longtext,
  `CategoryLevel` varchar(150) DEFAULT NULL,
  `RegionLevel` varchar(150) DEFAULT NULL,
  `AlbumCategory` longtext,
  `Keywords` longtext COMMENT '关键词',
  `Volume` longtext,
  `Dynasty` longtext,
  `ReignTitle` longtext,
  `ReignYear` longtext,
  `Edition` longtext,
  `PageCount` varchar(11) DEFAULT NULL,
  `Years_ForSearch` longtext,
  `DBID` varchar(30) DEFAULT NULL,
  `OnlineDate` varchar(30) DEFAULT NULL,
  `BoughtUserId` varchar(255) DEFAULT NULL,
  `LibraryId` varchar(255) DEFAULT NULL COMMENT '数据库id',
  `ColumnId` varchar(255) DEFAULT NULL COMMENT '栏目号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `BatchId` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.localchronicleitem 结构
CREATE TABLE IF NOT EXISTS `localchronicleitem` (
  `Id` varchar(255) NOT NULL COMMENT 'id',
  `NewTitle` longtext COMMENT '规范题目',
  `Title` longtext COMMENT '题目',
  `ParentID` varchar(255) DEFAULT NULL,
  `LibraryId` longtext COMMENT '数据库id',
  `ColumnId` longtext COMMENT '栏目编号',
  `Keywords` longtext COMMENT '关键词',
  `RealLevel` varchar(255) DEFAULT NULL,
  `Sheet` longtext,
  `SheetInfo` varchar(255) DEFAULT NULL,
  `Chapter` longtext,
  `ChapterInfo` varchar(255) DEFAULT NULL,
  `Section` longtext,
  `SectionInfo` varchar(255) DEFAULT NULL,
  `Item` longtext,
  `ItemInfo` varchar(255) DEFAULT NULL,
  `SubItem` longtext,
  `SubItemInfo` varchar(255) DEFAULT NULL,
  `Type` longtext,
  `No` longtext,
  `ItemLevel` longtext,
  `ItemLevelCode` varchar(255) DEFAULT NULL,
  `Page` varchar(255) DEFAULT NULL,
  `PageCount` varchar(255) DEFAULT NULL,
  `Content` longtext COMMENT '内容简介',
  `Location` varchar(255) DEFAULT NULL,
  `BeginPara` varchar(255) DEFAULT NULL,
  `EndPara` varchar(255) DEFAULT NULL,
  `BookID` varchar(255) DEFAULT NULL,
  `BookTitle` longtext COMMENT '方志中文名',
  `Editorial` longtext COMMENT '编纂单位',
  `Editor` longtext COMMENT '编纂人员',
  `Publisher` longtext COMMENT '出版单位',
  `Date` datetime DEFAULT NULL,
  `Year` varchar(11) DEFAULT NULL,
  `ISBN` varchar(255) DEFAULT NULL,
  `TimeLimit` varchar(255) DEFAULT NULL,
  `TimeLimitBegin` varchar(255) DEFAULT NULL,
  `TimeLimitEnd` varchar(255) DEFAULT NULL,
  `IsNational` varchar(255) DEFAULT NULL,
  `Province` varchar(255) DEFAULT NULL COMMENT '省',
  `City` varchar(255) DEFAULT NULL COMMENT '市',
  `County` varchar(255) DEFAULT NULL COMMENT '县',
  `RegionCode` varchar(255) DEFAULT NULL,
  `OriginalCategoryCode` varchar(255) DEFAULT NULL,
  `CategoryCode` varchar(255) DEFAULT NULL,
  `RegionCode_ForSearch` varchar(255) DEFAULT NULL,
  `CategoryCode_ForSearch` varchar(255) DEFAULT NULL,
  `DBID` varchar(255) DEFAULT NULL,
  `Dynasty` varchar(255) DEFAULT NULL,
  `ReignTitle` longtext,
  `ReignYear` varchar(255) DEFAULT NULL,
  `Volume` longtext,
  `VolumeSort` varchar(255) DEFAULT NULL,
  `FilePath` varchar(255) DEFAULT NULL,
  `Years_ForSearch` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `BatchId` int(11) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `BatchId` (`BatchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.localChronicleItem 结构
CREATE TABLE IF NOT EXISTS `localChronicleItem` (
  `Id` varchar(255) NOT NULL COMMENT 'id',
  `NewTitle` longtext COMMENT '规范题目',
  `Title` longtext COMMENT '题目',
  `ParentID` varchar(255) DEFAULT NULL,
  `LibraryId` longtext COMMENT '数据库id',
  `ColumnId` longtext COMMENT '栏目编号',
  `Keywords` longtext COMMENT '关键词',
  `RealLevel` varchar(255) DEFAULT NULL,
  `Sheet` longtext,
  `SheetInfo` varchar(255) DEFAULT NULL,
  `Chapter` longtext,
  `ChapterInfo` varchar(255) DEFAULT NULL,
  `Section` longtext,
  `SectionInfo` varchar(255) DEFAULT NULL,
  `Item` longtext,
  `ItemInfo` varchar(255) DEFAULT NULL,
  `SubItem` longtext,
  `SubItemInfo` varchar(255) DEFAULT NULL,
  `Type` longtext,
  `No` longtext,
  `ItemLevel` longtext,
  `ItemLevelCode` varchar(255) DEFAULT NULL,
  `Page` varchar(255) DEFAULT NULL,
  `PageCount` varchar(255) DEFAULT NULL,
  `Content` longtext COMMENT '内容简介',
  `Location` varchar(255) DEFAULT NULL,
  `BeginPara` varchar(255) DEFAULT NULL,
  `EndPara` varchar(255) DEFAULT NULL,
  `BookID` varchar(255) DEFAULT NULL,
  `BookTitle` longtext COMMENT '方志中文名',
  `Editorial` longtext COMMENT '编纂单位',
  `Editor` longtext COMMENT '编纂人员',
  `Publisher` longtext COMMENT '出版单位',
  `Date` datetime DEFAULT NULL,
  `Year` varchar(11) DEFAULT NULL,
  `ISBN` varchar(255) DEFAULT NULL,
  `TimeLimit` varchar(255) DEFAULT NULL,
  `TimeLimitBegin` varchar(255) DEFAULT NULL,
  `TimeLimitEnd` varchar(255) DEFAULT NULL,
  `IsNational` varchar(255) DEFAULT NULL,
  `Province` varchar(255) DEFAULT NULL COMMENT '省',
  `City` varchar(255) DEFAULT NULL COMMENT '市',
  `County` varchar(255) DEFAULT NULL COMMENT '县',
  `RegionCode` varchar(255) DEFAULT NULL,
  `OriginalCategoryCode` varchar(255) DEFAULT NULL,
  `CategoryCode` varchar(255) DEFAULT NULL,
  `RegionCode_ForSearch` varchar(255) DEFAULT NULL,
  `CategoryCode_ForSearch` varchar(255) DEFAULT NULL,
  `DBID` varchar(255) DEFAULT NULL,
  `Dynasty` varchar(255) DEFAULT NULL,
  `ReignTitle` longtext,
  `ReignYear` varchar(255) DEFAULT NULL,
  `Volume` longtext,
  `VolumeSort` varchar(255) DEFAULT NULL,
  `FilePath` varchar(255) DEFAULT NULL,
  `Years_ForSearch` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `BatchId` int(11) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `BatchId` (`BatchId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.localchroniclepaper 结构
CREATE TABLE IF NOT EXISTS `localchroniclepaper` (
  `Id` varchar(120) DEFAULT NULL,
  `Title` longtext,
  `Creator` varchar(120) DEFAULT NULL,
  `Abstract` longtext,
  `LibraryId` varchar(200) DEFAULT NULL,
  `ColumnId` varchar(500) DEFAULT NULL,
  `Keywords` longtext,
  `OriginalOrganization` longtext,
  `AuthorOrg` longtext,
  `PublishDate` varchar(120) DEFAULT NULL,
  `PageNo` longtext,
  `FulltextOnlineDate` longtext,
  `PublishYear` longtext NOT NULL,
  `ClassCode` longtext,
  `ServiceMode` longtext,
  `HasFulltext` longtext,
  `Page` longtext,
  `Issue` longtext,
  `Volum` longtext,
  `CorePeriodical` longtext,
  `ISSN` longtext,
  `MeetingArea` longtext,
  `MeetingDate` datetime DEFAULT NULL,
  `MeetingYear` longtext,
  `Sponsor` longtext,
  `Theme` longtext,
  `TitleKeyword` longtext,
  `Degree` longtext,
  `Major` longtext,
  `MajorCode` longtext,
  `Tutor` longtext,
  `PeriodicalId` longtext,
  `PeriodicalTitle` longtext,
  `MeetingId` longtext,
  `MeetingTitle` longtext,
  `ResourceType` longtext,
  `BatchId` int(11) DEFAULT NULL,
  `NewTitle` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.localChroniclePaper 结构
CREATE TABLE IF NOT EXISTS `localChroniclePaper` (
  `Id` varchar(120) DEFAULT NULL,
  `Title` longtext,
  `Creator` varchar(120) DEFAULT NULL,
  `Abstract` longtext,
  `LibraryId` varchar(200) DEFAULT NULL,
  `ColumnId` varchar(500) DEFAULT NULL,
  `Keywords` longtext,
  `OriginalOrganization` longtext,
  `AuthorOrg` longtext,
  `PublishDate` varchar(120) DEFAULT NULL,
  `PageNo` longtext,
  `FulltextOnlineDate` longtext,
  `PublishYear` longtext NOT NULL,
  `ClassCode` longtext,
  `ServiceMode` longtext,
  `HasFulltext` longtext,
  `Page` longtext,
  `Issue` longtext,
  `Volum` longtext,
  `CorePeriodical` longtext,
  `ISSN` longtext,
  `MeetingArea` longtext,
  `MeetingDate` datetime DEFAULT NULL,
  `MeetingYear` longtext,
  `Sponsor` longtext,
  `Theme` longtext,
  `TitleKeyword` longtext,
  `Degree` longtext,
  `Major` longtext,
  `MajorCode` longtext,
  `Tutor` longtext,
  `PeriodicalId` longtext,
  `PeriodicalTitle` longtext,
  `MeetingId` longtext,
  `MeetingTitle` longtext,
  `ResourceType` longtext,
  `BatchId` int(11) DEFAULT NULL,
  `NewTitle` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.permission 结构
CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_id` varchar(32) NOT NULL COMMENT '权限id',
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `url` varchar(255) DEFAULT NULL COMMENT '权限访问路径',
  `perms` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级权限id',
  `type` int(1) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `order_num` int(3) DEFAULT '0' COMMENT '排序',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` int(1) NOT NULL COMMENT '状态：1有效；2删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `status` int(1) NOT NULL COMMENT '状态：1有效；2删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.role_copy1 结构
CREATE TABLE IF NOT EXISTS `role_copy1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `status` int(1) NOT NULL COMMENT '状态：1有效；2删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.role_copy2 结构
CREATE TABLE IF NOT EXISTS `role_copy2` (
  `id` int(11) NOT NULL DEFAULT '0',
  `role_id1` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.role_permission 结构
CREATE TABLE IF NOT EXISTS `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  `permission_id` varchar(20) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4043 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.sys_config 结构
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `sys_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`sys_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(20) NOT NULL COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL,
  `salt` varchar(128) DEFAULT NULL COMMENT '加密盐值',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sex` int(255) DEFAULT NULL COMMENT '年龄：1男2女',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `img` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `status` int(1) NOT NULL COMMENT '用户状态：1有效; 2删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  UNIQUE KEY `user_user_id_uindex` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

-- 导出  表 cpc-admin.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL COMMENT '用户id',
  `role_id` varchar(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
