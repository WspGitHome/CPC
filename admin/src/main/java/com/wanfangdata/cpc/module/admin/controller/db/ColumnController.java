package com.wanfangdata.cpc.module.admin.controller.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.config.ApplicationInitialize;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.service.db.*;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnConditionVo;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnTreeVo;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyConditionVo;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台类目管理
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Controller
@RequestMapping("column")
@AllArgsConstructor
public class ColumnController {

    private final DbColumnService dbColumnService;
    private final DbContentService dbContentService;
    private final DbKeywordService dbKeywordService;
    private final DbLibService dbLibService;
    private final PropertyService propertyService;
    private final ApplicationInitialize applicationInitialize;

    @PostMapping("list")
    @ResponseBody
    public PageResultVo loadColumn(ColumnConditionVo columnConditionVo, Integer pageNumber, Integer pageSize) {
        if (columnConditionVo.getPid() == null) {
            columnConditionVo.setPid(0);
        }
        IPage<DbColumn> page = new Pagination<>(pageNumber, pageSize);
        List<DbColumn> list = dbColumnService.findByCondition(page, columnConditionVo);
        return ResultUtil.table(list, page.getTotal());
    }

    @PostMapping("tree")
    @ResponseBody
    public List<ColumnConditionVo> tree(Integer library) {
        List<DbColumn> list = dbColumnService.selecColumneAll(library);
        List<ColumnConditionVo> result = new ArrayList<>();
        list.stream().forEach(dbColumn -> {
            ColumnConditionVo vo = new ColumnConditionVo();
            vo.setId(dbColumn.getId());
            vo.setPid(dbColumn.getPid());
            vo.setName(dbColumn.getName());
            result.add(vo);
        });
        return result;
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<DbLibtype> libraries = dbLibService.selecLibtypes();
        model.addAttribute("properties", applicationInitialize.getDbProperties());
        model.addAttribute("libraries", libraries);
        return CoreConst.ADMIN_PREFIX + "column/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(DbColumn dbColumn) {
        Date date = new Date();
        dbColumn.setCreateTime(date);
        dbColumn.setUpdateTime(date);
        int i = dbColumnService.insertColumn(dbColumn);
        if (i == 1) {
            return ResultUtil.success("新增栏目成功");
        } else {
            return ResultUtil.error("新增栏目失败");
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        ColumnTreeVo vo = dbColumnService.selectTree(id);
        model.addAttribute("vo", vo);
        model.addAttribute("properties", applicationInitialize.getDbProperties());
        return CoreConst.ADMIN_PREFIX + "column/form";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(DbColumn dbColumn) {
        dbColumn.setUpdateTime(new Date());
        boolean i = dbColumnService.updateById(dbColumn);
        if (i) {
            return ResultUtil.success("编辑栏目成功");
        } else {
            return ResultUtil.error("编辑栏目失败");
        }
    }

    @PostMapping("/moveUp")
    @ResponseBody
    public ResponseVo moveUp(Integer currentId, Integer lastId) {
        DbColumn column = dbColumnService.getById(currentId);//当前
        DbColumn upColumn = dbColumnService.getById(lastId);//上一个
        //交换sort值
        Integer nowSort = column.getSort();
        column.setSort(upColumn.getSort());
        column.setUpdateTime(new Date());
        dbColumnService.updateById(column);
        upColumn.setSort(nowSort);
        upColumn.setUpdateTime(new Date());
        dbColumnService.updateById(upColumn);
        return ResultUtil.success("上移成功");
    }

    @PostMapping("/moveDown")
    @ResponseBody
    public ResponseVo moveDown(Integer currentId, Integer nextId) {
        DbColumn nowColumn = dbColumnService.getById(currentId);//当前
        DbColumn downColumn = dbColumnService.getById(nextId);//下一个
        //交换sort值
        Integer nowSort = nowColumn.getSort();
        nowColumn.setSort(downColumn.getSort());
        nowColumn.setUpdateTime(new Date());
        dbColumnService.updateById(nowColumn);
        downColumn.setSort(nowSort);
        downColumn.setUpdateTime(new Date());
        dbColumnService.updateById(downColumn);
        return ResultUtil.success("下移成功");
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
        List<DbColumn> list = dbColumnService.selectByPid(id);
        if (CollectionUtils.isNotEmpty(list)) {
            System.out.println("该栏目下存在子栏目,请先删除子栏目");
            return ResultUtil.error("该栏目下存在子栏目,请先删除子栏目");
        }
        if (dbContentService.selectCountByColumnId(id) > 0) {
            System.out.println("该栏目下存在内容,请先删除该栏目下的内容");
            return ResultUtil.error("该栏目下存在内容,请先删除该栏目下的内容");
        }
        if (dbKeywordService.selectCountByColumnId(id) > 0) {
            System.out.println("该栏目下存在关键词,请先删除该栏目下的关键词");
            return ResultUtil.error("该栏目下存在关键词,请先删除该栏目下的关键词");
        }
        return ResultUtil.success();
    }

    @PostMapping("/deleteReal")
    @ResponseBody
    public ResponseVo realDelete(Integer id) {
        List<DbColumn> dbColumnList=dbColumnService.selectParents(id);
        StringBuilder colTree=new StringBuilder();
        if(dbColumnList.size()>0){
            colTree.append(dbColumnList.get(0).getLibraryId());
            for(DbColumn dbColumn:dbColumnList){
                if(dbColumn!=null){
                    colTree.append("/");
                    colTree.append(dbColumn.getId());
                }
            }
        }
        IPage<DbPropertyList> page = new Pagination<>(1, 1);
        PropertyConditionVo conditionVo=new PropertyConditionVo();
        conditionVo.setColumnId(colTree.toString());
        conditionVo.setProType("localChronicleItem");
        IPage<DbPropertyList> propertyList = propertyService.findByCondition(page, conditionVo);
        if (propertyList.getRecords().size()> 0) {
            return ResultUtil.error("栏目下含有资源，是否删除该栏目下资源");
        }
        if (dbColumnService.deleteColumn(id) == 1) {
            return ResultUtil.success("删除栏目成功");
        }
        return ResultUtil.error("删除栏目失败");
    }


    @PostMapping("/deleteResoueces")
    @ResponseBody
    public ResponseVo resourcesDelete(Integer id) {
        //TODO 删除资源下的有关内容
        boolean resourcesFlag = true;
        if (resourcesFlag && dbColumnService.deleteColumn(id) == 1) {
            return ResultUtil.success("删除栏目成功");
        }
        return ResultUtil.error("删除栏目失败");
    }

    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo deleteBatch(@RequestParam("ids[]") Integer[] ids) {
        int i = dbColumnService.deleteBatch(ids);
        if (i > 0) {
            return ResultUtil.success("删除栏目成功");
        } else {
            return ResultUtil.error("删除栏目失败");
        }
    }

}
