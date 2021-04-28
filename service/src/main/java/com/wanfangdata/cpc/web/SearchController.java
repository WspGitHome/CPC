package com.wanfangdata.cpc.web;


import com.wanfangdata.cpc.model.*;
import com.wanfangdata.cpc.service.SearchService;
import com.wanfangdata.cpc.utils.ResultUtil;
import com.wanfangdata.cpc.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FLY
 * @create
 **/
@Api(value = "/api", description = "检索服务")
@RestController
@AllArgsConstructor
public class SearchController {
    private static Logger logger = LoggerFactory.getLogger(SearchController.class);

    SearchService searchService;

    @ApiOperation(value = "普通检索", notes = "普通检索" )
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseModel search(SearchViewModel model){
        try {
            SearchResultViewModel result=searchService.search(model);
            return ResultUtil.success("查询成功",result);
        }catch (Exception e){
            logger.error("查询失败 model:{} msg:{}",model.toString(),e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }

    @ApiOperation(value = "首页列表检索", notes = "首页列表检索,查询随机页码数据" )
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/searchCache",method = RequestMethod.GET)
    public ResponseModel searchCache(SearchViewModel model){
        try {
            String key=String.join("#", StringUtil.listToString(model.getFq(),"-"),model.getQuery().trim()+model.getCollection().trim());
            SearchResultViewModel result=searchService.searchCache(model,key);
            return ResultUtil.success("查询成功",result);
        }catch (Exception e){
            logger.error("查询失败 model:{} msg:{}",model.toString(),e.getMessage());
            return ResultUtil.error("查询失败");
        }
    }

    @ApiOperation(value = "聚类检索", notes = "聚类检索" )
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/facet",method = RequestMethod.GET)
    public ResponseModel facet(FacetViewModel model){
        try {
            FacetResultViewModel result= searchService.facet(model);
            return ResultUtil.success("聚类查询成功",result);
        }catch (Exception e){
            logger.error("聚类查询失败 model:{} msg:{}",model.toString(),e.getMessage());
            return ResultUtil.error("聚类查询失败");
        }
    }

    @ApiOperation(value = "二维聚类检索", notes = "二维聚类检索" )
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数有误") })
    @RequestMapping(value = "/facetPivot",method = RequestMethod.GET)
    public ResponseModel facetPivot(FacetPivotViewModel model){
        try {
            FacetResultViewModel result= searchService.facetPivot(model);
            return ResultUtil.success("聚类查询成功",result);
        }catch (Exception e){
            logger.error("聚类查询失败 model:{} msg:{}",model.toString(),e.getMessage());
            return ResultUtil.error("聚类查询失败");
        }
    }

}
