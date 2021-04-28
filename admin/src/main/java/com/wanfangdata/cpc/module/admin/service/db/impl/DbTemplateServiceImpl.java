package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.common.config.properties.FileUploadProperties;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.module.admin.mapper.db.DbFTemplateInfoMapper;
import com.wanfangdata.cpc.module.admin.mapper.db.DbLibraryMapper;
import com.wanfangdata.cpc.module.admin.mapper.db.DbTemplateMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplate;
import com.wanfangdata.cpc.module.admin.service.db.DbTemplateService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.db.impl
 * @Classname DbTemplateServiceImpl
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/09 14:01
 * @Version 1.0
 */
@Service
@AllArgsConstructor
public class DbTemplateServiceImpl extends ServiceImpl<DbTemplateMapper, DbTemplate> implements DbTemplateService {

    private final static String TEMPLATE_PATH="template";
    private final static String DEPLOY_PATH="front";

    private final FileUploadProperties fileUploadProperties;
    private final DbTemplateMapper dbFTemplateMapper;
    private final DbFTemplateInfoMapper dbFTemplateInfoMapper;
    private final DbLibraryMapper dbLibraryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)//checked异常也回滚 操作数据库和文件删改必须保持结果一致
    public boolean removedById(Serializable id) throws Exception {
        DbTemplate dbTemplate = dbFTemplateMapper.selectById(id);
        return (dbFTemplateMapper.deleteById(id) == 1 ? true : false) && removeDbPath(dbTemplate.getDbId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toSaveOrUpdate(DbTemplate entity) throws Exception {
        boolean operateDone = false;
        Integer id = entity.getId();
        Integer templateId = entity.getTemplateId();
        if (id != null) {
            //修改
            operateDone = dbFTemplateMapper.updateById(entity) == 1;
        } else {
            //添加
            operateDone = dbFTemplateMapper.insert(entity) == 1;
        }

        if (templateId != null) {
            String deployFolder = fileUploadProperties.getDeployFolder();
            String source = deployFolder + File.separator + TEMPLATE_PATH + File.separator + dbFTemplateInfoMapper.selectById(entity.getTemplateId()).getTemplateUrl();
            String target = deployFolder + File.separator + DEPLOY_PATH + File.separator + dbLibraryMapper.selectById(entity.getDbId()).getAliasPath();
            if (entity.getId() != null) {
                //更新时
                //已选数据库不能改变
                DbTemplate dbTemplate = dbFTemplateMapper.selectById(entity.getId());
                if (entity.getTemplateId() != null) {
                    if (!entity.getTemplateId().equals(dbTemplate.getTemplateId())) {
                        try { //模板改变，变化删除旧的拷贝目录，重新复制，否则不变
                            FileUtils.deleteDirectory(new File(target));
                            FileUtils.copyDirectory(new File(source), new File(target));
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new IOException("toSaveOrUpdate-update-文件位置异常！");
                        }
                    }
                }
            } else {
                try {
                    FileUtils.copyDirectory(new File(source), new File(target));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IOException("toSaveOrUpdate-add-文件位置异常！");
                }
            }
        }
        return operateDone;
    }

    @Override
    public IPage<DbTemplate> findByCondition(@Param("dbTemplate") DbTemplate dbTemplate, Integer pageNum, Integer pageSize) {
        IPage<DbTemplate> page = new Pagination<>(pageNum, pageSize);
        return dbFTemplateMapper.selectPage(page, dbTemplate);
    }

    @Override
    public List<Integer> getDBIdsInTemplate() {
        return dbFTemplateMapper.selectDbIds();
    }

    @Override
    public boolean removeDbPath(Integer dbId) throws Exception {
        String deployFolder = fileUploadProperties.getDeployFolder();
        String target = deployFolder + File.separator + (dbLibraryMapper.selectById(dbId) != null ? dbLibraryMapper.selectById(dbId).getAliasPath() : String.valueOf(System.currentTimeMillis()));
        try {
            FileUtils.deleteDirectory(new File(target));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("removeDbPath-移除文件目录出错！");
        }
        return true;
    }

    @Override
    public DbTemplate getById(Serializable id) {
        return dbFTemplateMapper.selectById(id);
    }

}
