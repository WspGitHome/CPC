package com.wanfangdata.cpc.module.admin.controller.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.config.ApplicationInitialize;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.service.db.DbLibService;
import com.wanfangdata.cpc.module.admin.service.db.DbTemplateService;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import com.wanfangdata.cpc.module.admin.vo.db.DblibraryConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("library")
@AllArgsConstructor
public class DbLibController {

    private final DbTemplateService dbTemplateService;
    private final DbLibService dbLibService;
    private final ApplicationInitialize applicationInitialize;

    @PostMapping("listType")
    @ResponseBody
    public List<DbLibtype> loadLibraryType() {
        List<DbLibtype> list = applicationInitialize.getLibraries();
        return list;
    }

    @PostMapping("list")
    @ResponseBody
    public PageResultVo loadLibrary(DblibraryConditionVo dblibraryConditionVo, Integer pageNumber, Integer pageSize) {
        IPage<DbLibrary> page = new Pagination<>(pageNumber, pageSize);
        IPage<DbLibrary> list = dbLibService.findByCondition(page, dblibraryConditionVo);
        return ResultUtil.table(list.getRecords(), list.getTotal());
    }

    @PostMapping("listAll")
    @ResponseBody
    public List<DbLibrary> listAll(Integer type, Integer templateFlag) {
        List<DbLibrary> list;
        list = dbLibService.findAll(type);
        return list;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("libraries", applicationInitialize.getLibraries());
        return CoreConst.ADMIN_PREFIX + "library/form";
    }


    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(DbLibrary dbLibrary) {
        Date date = new Date();
        dbLibrary.setCreateTime(date);
        dbLibrary.setUpdateTime(date);
        boolean i = dbLibService.save(dbLibrary);
        if (i) {
            return ResultUtil.success("新增数据库成功");
        } else {
            return ResultUtil.error("新增数据库失败");
        }
    }


    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        model.addAttribute("libraries", applicationInitialize.getLibraries());
        DbLibrary dbLibrary = dbLibService.getById(id);
        model.addAttribute("dbLibrary", dbLibrary);
        return CoreConst.ADMIN_PREFIX + "library/form";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(DbLibrary dbLibrary) {
        dbLibrary.setUpdateTime(new Date());
        boolean i = dbLibService.updateById(dbLibrary);
        if (i) {
            return ResultUtil.success("编辑数据库成功");
        } else {
            return ResultUtil.error("编辑数据库失败");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
        try {
            if (dbLibService.deleteBatch(id) > 0) {
                return ResultUtil.success("删除数据库成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error("删除数据库失败");
    }

    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo batchDelete(@RequestParam(value = "ids[]") Integer[] ids) {
        for (Integer id : ids) {
            try {
                dbLibService.deleteBatch(id);

            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.error("删除数据库失败");
            }
        }
        return ResultUtil.success("删除数据库成功");
    }


    @PostMapping("/getDbAlias")
    @ResponseBody
    public ResponseVo getDbAlias(String alias) {
        DblibraryConditionVo dblibrary = new DblibraryConditionVo();
        dblibrary.setAliasPath(alias);
        IPage<DbLibrary> page = new Pagination<>(0, 1);
        IPage<DbLibrary> list = dbLibService.findByCondition(page, dblibrary);
        if (list.getTotal() > 0) {
            return ResultUtil.error("数据库别名已存在！");
        }
        return ResultUtil.success("");
    }
}
