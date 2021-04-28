package com.wanfangdata.cpc.module.admin.controller.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.config.ApplicationInitialize;
import com.wanfangdata.cpc.module.admin.model.db.DbContent;
import com.wanfangdata.cpc.module.admin.service.db.DbColumnService;
import com.wanfangdata.cpc.module.admin.service.db.DbContentService;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnTreeVo;
import com.wanfangdata.cpc.module.admin.vo.db.ContentConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;




/**
 * 后台文章管理
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Controller
@RequestMapping("content")
@AllArgsConstructor
public class ContentController {

    private final DbContentService dbContentService;
    private final DbColumnService dbColumnService;
    private final ApplicationInitialize applicationInitialize;

    @PostMapping("list")
    @ResponseBody
    public PageResultVo loadArticle(ContentConditionVo conditionVo, Integer pageNumber, Integer pageSize) {
       IPage<DbContent> page = new Pagination<>(pageNumber, pageSize);
      IPage<DbContent> contentList = dbContentService.findByCondition(page, conditionVo);
       return ResultUtil.table(contentList.getRecords(), contentList.getTotal());

    }

    @GetMapping("/add")
    public String addArticle(Model model) {
        model.addAttribute("libraries", applicationInitialize.getLibraries());
        return CoreConst.ADMIN_PREFIX + "content/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(DbContent dbContent,String editorValue) {
        Date date = new Date();
        dbContent.setContentDetails(editorValue);
        dbContent.setCreateTime(date);
        dbContent.setUpdateTime(date);
        boolean i = dbContentService.save(dbContent);
        if (i) {
            return ResultUtil.success("新增文章成功");
        } else {
            return ResultUtil.error("新增文章失败");
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        DbContent dbContent = dbContentService.getById(id);
        ColumnTreeVo vo=dbColumnService.selectTree(Integer.parseInt(dbContent.getColumnId()));
        model.addAttribute("dbContent", dbContent);
        model.addAttribute("vo", vo);
        return CoreConst.ADMIN_PREFIX + "content/form";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(DbContent dbContent,String editorValue) {
        dbContent.setUpdateTime(new Date());
        dbContent.setContentDetails(editorValue);
        boolean i = dbContentService.updateById(dbContent);
        if (i) {
            return ResultUtil.success("编辑文章成功");
        } else {
            return ResultUtil.error("编辑文章失败");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
        int i = dbContentService.deleteBatch(new Integer[]{id});
        if (i > 0) {
            return ResultUtil.success("删除文章成功");
        } else {
            return ResultUtil.error("删除文章失败");
        }
    }

}
