package com.wanfangdata.cpc.web;


import com.wanfangdata.cpc.model.ResponseModel;
import com.wanfangdata.cpc.model.SearchResultViewModel;
import com.wanfangdata.cpc.model.SearchViewModel;
import com.wanfangdata.cpc.search.utils.ItemTree;
import com.wanfangdata.cpc.service.CatalogueService;
import com.wanfangdata.cpc.service.SearchService;
import com.wanfangdata.cpc.utils.*;
import com.wanfangdata.oss.model.CatalogueNode;
import com.wanfangdata.oss.model.VolumeNode;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author FLY
 * @create 2018-01-05 8:23
 **/
@Api(value = "/api", description = "详情接口")
@RestController
@AllArgsConstructor
public class DetailController {
    private static Logger logger = LoggerFactory.getLogger(DetailController.class);

    private final static String FZCPCLocalChronicleItem="FZCPCLocalChronicleItem";

    SearchService searchService;
    CatalogueService catalogueService;

    @ApiOperation(value = "详情信息", notes = "书籍/条目详情" ,httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源ID", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "collection", value = "资源类型", required = true, dataType = "String",paramType = "query",allowableValues = Constant.PROPERTY)
    })
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public ResponseModel detail( @RequestParam String id,  @RequestParam String collection){
        try {
            Map<String,String> result=searchService.searchOne(id,collection,null);
            return ResultUtil.success("获取详情信息成功",result);
        }catch (Exception e){
            logger.error("获取详情信息失败 id:{} property：{} msg:{}",id,collection,e.getMessage());
            return ResultUtil.error("获取详情信息失败");
        }
    }

    @ApiOperation(value = "获取整书目录", notes = "新方志目录" ,httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "整书ID", required = true, dataType = "String",paramType = "query")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/contents",method = RequestMethod.GET)
    public ResponseModel contents(@RequestParam String id){
        try {
            List<CatalogueNode> catalogueNodeList=catalogueService.catalogue(id);
            return ResultUtil.success("新方志目录查询成功",catalogueNodeList);
        }catch (Exception e){
            logger.error("新方志目录查询失败 id:{} msg:{}",id,e.getMessage());
            return ResultUtil.error("新方志目录查询失败");
        }

    }


    @ApiOperation(value = "获取条目目录树", notes = "新方志条目目录" ,httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "条目ID", required = true, dataType = "String",paramType = "query")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/contentTree",method = RequestMethod.GET)
    public ResponseModel contentTree(@RequestParam String id){
        try {
            Map<String,String> result=searchService.searchOne(id,FZCPCLocalChronicleItem,null);
            SearchViewModel model=new SearchViewModel();
            model.setQuery("ParentID:"+id);
            model.setCollection(FZCPCLocalChronicleItem);
            SearchResultViewModel searchResult=searchService.search(model);
            TreeNode treeNode=ItemTree.getNavigateTree(result,searchResult.getResult());
            return ResultUtil.success("新方志方志条目目录查询成功",treeNode);
        }catch (Exception e){
            logger.error("新方志方志条目目录查询失败 id:{} msg:{}",id,e.getMessage());
            return ResultUtil.error("新方志方志条目目录查询失败");
        }
    }


    /**
     *@ApiOperation(value = "获取分卷目录", notes = "旧方志目录" ,httpMethod = "GET")
     *@ApiImplicitParam(name = "id", value = "整书ID", required = true, dataType = "String",paramType = "query")
     *@ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
     *@RequestMapping(value = "/volumes",method = RequestMethod.GET)
     *
     *  public ResponseModel  volumes(@RequestParam String id){
     *  try {
     *  List<VolumeNode> catalogueNodeList=catalogueService.volumes(id);
     *  return ResultUtil.success("旧方志目录查询成功",catalogueNodeList);
     *  }catch (Exception e){
     *  logger.error("旧方志目录查询失败 id:{} msg:{}",id,e.getMessage());
     *  return ResultUtil.error("旧方志目录查询失败");
     *  }
     * *  }
     * */


}
