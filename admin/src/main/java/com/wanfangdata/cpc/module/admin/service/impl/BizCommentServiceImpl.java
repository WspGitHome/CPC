package com.wanfangdata.cpc.module.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.module.admin.mapper.BizCommentMapper;
import com.wanfangdata.cpc.module.admin.model.BizComment;
import com.wanfangdata.cpc.module.admin.service.BizCommentService;
import com.wanfangdata.cpc.module.admin.vo.db.CommentConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class BizCommentServiceImpl extends ServiceImpl<BizCommentMapper, BizComment> implements BizCommentService {

    private final BizCommentMapper commentMapper;

    @Override
    public IPage<BizComment> selectComments(CommentConditionVo vo, Integer pageNumber, Integer pageSize) {
        IPage<BizComment> page = new Pagination<>(pageNumber, pageSize);
        page.setRecords(commentMapper.selectComments(page, vo));
        return page;
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return commentMapper.deleteBatch(ids);
    }
}
