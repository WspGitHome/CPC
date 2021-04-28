package com.wanfangdata.cpc.module.admin.controller.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicleItem;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.service.db.DbPropertyModifyService;
import com.wanfangdata.cpc.module.admin.util.ExcelExport;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyModifyConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.controller.db
 * @ClassName: PropertyModifyController
 * @Description: 资源更新记录
 * @Author: rongrong
 * @CreateDate: 2020/8/20
 * @Version: 1.0
 */
@Controller
@RequestMapping("propertyModify")
@AllArgsConstructor
public class PropertyModifyController {
    private final DbPropertyModifyService propertyModifyService;
    @PostMapping("/list")
    @ResponseBody
    public PageResultVo proModifyList(PropertyModifyConditionVo conditionVo, Integer pageNumber, Integer pageSize){
        IPage<DbPropertyList> page = new Pagination<>(pageNumber, pageSize);
        IPage<DbPropertyList> proList = propertyModifyService.findByCondition(page, conditionVo);
        return ResultUtil.table(proList.getRecords(), proList.getTotal());
    }

    @RequestMapping("export")
    @ResponseBody
    public void uploadData(String proType, String startDate, String endDate, HttpServletResponse response) {
        List<String> columnNameList = propertyModifyService.findColumnNames(proType);
        if(proType.equals("localChronicle")){
            List<DbLocalChronicle> localChronicleList= propertyModifyService.findLocalChronicleData(startDate, endDate);
            ExcelExport.export(response,localChronicleList,columnNameList, proType);
        }else{
            List<DbLocalChronicleItem> itemData = propertyModifyService.findItemData(startDate, endDate);
            ExcelExport.export(response,itemData,columnNameList, proType);
        }


    }
}
