package com.wanfangdata.cpc.module.admin.controller.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.config.ApplicationInitialize;
import com.wanfangdata.cpc.module.admin.model.db.DbKeyword;
import com.wanfangdata.cpc.module.admin.service.db.DbColumnService;
import com.wanfangdata.cpc.module.admin.service.db.DbKeywordService;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnTreeVo;
import com.wanfangdata.cpc.module.admin.vo.db.KeywordConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.controller.db
 * @ClassName: KeywordController
 * @Description: 后台关键词管理
 * @Author: rongrong
 * @CreateDate: 2020/8/3
 * @Version: 1.0
 */
@Controller
@RequestMapping("keyword")
@AllArgsConstructor
public class KeywordController {
    private final ApplicationInitialize applicationInitialize;
    private final DbKeywordService dbKeywordService;
    private final DbColumnService dbColumnService;

    @PostMapping("keywordList")
    @ResponseBody
    public PageResultVo loadKeyword(KeywordConditionVo  conditionVo, Integer pageNumber, Integer pageSize) {
        IPage<DbKeyword> page = new Pagination<>(pageNumber, pageSize);
        IPage<DbKeyword> keywordList = dbKeywordService.findByCondition(page, conditionVo);
        return ResultUtil.table(keywordList.getRecords(), keywordList.getTotal());
    }
    @GetMapping("/add")
    public String addArticle(Model model) {
        model.addAttribute("libraries", applicationInitialize.getLibraries());
        model.addAttribute("dbNames", applicationInitialize.getDbNames());
        return CoreConst.ADMIN_PREFIX + "keyword/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(DbKeyword dbKeyword) {
        Date date = new Date();
        dbKeyword.setCreateTime(date);
        dbKeyword.setUpdateTime(date);
        int i = dbKeywordService.insertKeyword(dbKeyword);
        if (i==1) {
            return ResultUtil.success("新增关键词成功");
        } else {
            return ResultUtil.error("新增关键词失败");
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        DbKeyword dbKeyword = dbKeywordService.getById(id);
        ColumnTreeVo vo=dbColumnService.selectTree(Integer.parseInt(dbKeyword.getColumnId()));
        model.addAttribute("dbKeyword", dbKeyword);
        model.addAttribute("vo", vo);
        return CoreConst.ADMIN_PREFIX + "keyword/form";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(DbKeyword dbKeyword) {
        dbKeyword.setUpdateTime(new Date());
        boolean i = dbKeywordService.updateById(dbKeyword);
        if (i) {
            return ResultUtil.success("编辑关键词成功");
        } else {
            return ResultUtil.error("编辑关键词失败");
        }
    }
    @PostMapping("/moveUp")
    @ResponseBody
    public ResponseVo moveUp(Integer currentId,Integer lastId){
        DbKeyword upKeyword = dbKeywordService.getById(currentId);//当前
        DbKeyword lastKeyword= dbKeywordService.getById(lastId);//上一个
        if(null==lastKeyword){
            return ResultUtil.success("最上面的不能上移");
        }
        //交换orderNum值
        Integer nowNum = upKeyword.getOrderNum();
        upKeyword.setOrderNum(lastKeyword.getOrderNum());
        upKeyword.setUpdateTime(new Date());
        dbKeywordService.updateById(upKeyword);
        lastKeyword.setOrderNum(nowNum);
        lastKeyword.setUpdateTime(new Date());
        dbKeywordService.updateById(lastKeyword);
        return ResultUtil.success("上移成功");
    }
    @PostMapping("/moveDown")
    @ResponseBody
    public ResponseVo moveDown(Integer currentId,Integer nextId){
        DbKeyword downKeyword = dbKeywordService.getById(currentId);//当前
        DbKeyword nextKeyword = dbKeywordService.getById(nextId);//下一个的信息
        if(null==nextKeyword){
            return ResultUtil.success("最下面的不能下移");
        }
        //交换orderNum值
        Integer nowNum = downKeyword.getOrderNum();
        downKeyword.setOrderNum(nextKeyword.getOrderNum());
        downKeyword.setUpdateTime(new Date());
        dbKeywordService.updateById(downKeyword);
        nextKeyword.setOrderNum(nowNum);
        nextKeyword.setUpdateTime(new Date());
        dbKeywordService.updateById(nextKeyword);
        return ResultUtil.success("下移成功");
    }
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
        int i = dbKeywordService.deleteBatch(new Integer[]{id});
        if (i > 0) {
            return ResultUtil.success("删除关键词成功");
        } else {
            return ResultUtil.error("删除关键词失败");
        }
    }
}
