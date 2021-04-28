package com.wanfangdata.cpc.module.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.module.admin.mapper.BizTagsMapper;
import com.wanfangdata.cpc.module.admin.model.BizTags;
import com.wanfangdata.cpc.module.admin.service.BizTagsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class BizTagsServiceImpl extends ServiceImpl<BizTagsMapper, BizTags> implements BizTagsService {

    private final BizTagsMapper bizTagsMapper;

    @Override
    public List<BizTags> selectTags(BizTags bizTags) {
        return bizTagsMapper.selectTags(null, bizTags);
    }

    @Override
    public IPage<BizTags> pageTags(BizTags bizTags, Integer pageNumber, Integer pageSize) {
        IPage<BizTags> page = new Pagination<>(pageNumber, pageSize);
        return page.setRecords(bizTagsMapper.selectTags(page, bizTags));
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return bizTagsMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
