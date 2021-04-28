package com.wanfangdata.cpc.web;

import com.wanfangdata.cpc.entity.Column;
import com.wanfangdata.cpc.entity.Article;
import com.wanfangdata.cpc.entity.Domain;
import com.wanfangdata.cpc.entity.Keyword;
import com.wanfangdata.cpc.model.PageResultModel;
import com.wanfangdata.cpc.model.ResponseModel;
import com.wanfangdata.cpc.service.DataService;
import com.wanfangdata.cpc.utils.ResultUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 数据查询接口
 *@author: FLY
 *@create: 2020-08-13 15:55
 */
@Api(value = "/api", description = "栏目数据接口")
@RestController
@AllArgsConstructor
public class DataController {

    private static Logger logger = LoggerFactory.getLogger(DataController.class);

    Domain domain;
    DataService dataService;

    @ApiOperation(value = "获取栏目导航",
            notes = "获取栏目导航,根据站点别名路径，获取栏目一级导航目录，目前无需翻页处理", httpMethod = "GET")
    @ApiImplicitParam(name = "alias", value = "项目访问别名路径", required = true, dataType = "String",paramType = "query")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/navigation", method = RequestMethod.GET)
    public ResponseModel navigation( @RequestParam String alias) {
        try {
            List<Column>  list=dataService.columnsByAlias(alias);
            Map<Integer,List<Column>> map=new HashMap<>();
            list.stream().forEach(column -> {
                if(map.containsKey(column.getPropertyId())){
                    map.get(column.getPropertyId()).add(column);
                }else{
                    List<Column>  columnList=new ArrayList<Column>();
                    columnList.add(column);
                    map.put(column.getPropertyId(),columnList);
                }
            });
            return ResultUtil.success("查询成功",map);
        }catch (Exception e){
            logger.error("查询失败 alias:{} msg:{}",alias,e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }


    @ApiOperation(value = "获取栏目树",
            notes = "获取栏目树,根据站点别名路径，获取栏目树，目前无需翻页处理", httpMethod = "GET")
    @ApiImplicitParam(name = "alias", value = "项目访问别名路径", required = true, dataType = "String",paramType = "query")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/columnTree", method = RequestMethod.GET)
    public ResponseModel columnTree( @RequestParam String alias) {
        try {
            Integer library=dataService.getLibrary(alias);
            Column column=dataService.columnTree(library);
            return ResultUtil.success("查询成功",column );
        }catch (Exception e){
            logger.error("查询失败 alias:{} msg:{}",alias,e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }


    @ApiOperation(value = "获取子栏目列表",
            notes = "获取子栏目列表，根据父栏目ID,获取子栏目列表，不得为0，目前无需翻页处理", httpMethod = "GET")
    @ApiImplicitParam(name = "columnId", value = "栏目ID", required = true, dataType = "String",paramType = "query")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/columns", method = RequestMethod.GET)
    public ResponseModel columns(@RequestParam Integer columnId) {
        try {
            if(columnId==0){
               return ResultUtil.error("查询失败,栏目ID为0，请通过导航接口获取一级栏目");
            }
            List<Column> list=dataService.columnsByPid(columnId);
            return ResultUtil.success("查询成功",list);
        }catch (Exception e){
            logger.error("查询失败 msg:{}",e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }

    @ApiOperation(value = "获取关键字列表",
            notes = "获取关键字列表，目前无需翻页处理", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "栏目ID", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码，初始值应为1", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "翻页数量", required = true, dataType = "String",paramType = "query")
    })
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/keywords", method = RequestMethod.GET)
    public ResponseModel keywords(@RequestParam Integer columnId,@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
        try {
            PageResultModel list=dataService.keywords(columnId,pageNo,pageSize);
            return ResultUtil.success("查询成功",list);
        }catch (Exception e){
            logger.error("查询失败 msg:{}",e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }


    @ApiOperation(value = "获取文章列表",
            notes = "获取文章列表，根据栏目ID,获取文章列表", httpMethod = "GET")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "栏目ID", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码，初始值应为1", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "翻页数量", required = true, dataType = "String",paramType = "query")
    })
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public ResponseModel articles(@RequestParam Integer columnId,@RequestParam Integer pageNo,@RequestParam Integer pageSize) {
        try {
            PageResultModel list=dataService.articles(columnId,pageNo,pageSize);
            return ResultUtil.success("查询成功",list);
        }catch (Exception e){
            logger.error("查询失败 msg:{}",e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }

    @ApiOperation(value = "获取文章",
            notes = "获取文章，根据文章ID,获取文章详情", httpMethod = "GET")
    @ApiImplicitParam(name = "articleId", value = "文章ID", required = true, dataType = "String",paramType = "query")
    @ApiResponses({@ApiResponse(code = 400, message = "请求参数有误")})
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public ResponseModel article(@RequestParam Integer articleId) {
        try {
            Article article =dataService.article(articleId);
            return ResultUtil.success("查询成功", article);
        }catch (Exception e){
            logger.error("查询失败 msg:{}",e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }
}