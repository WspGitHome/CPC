package com.wanfangdata.cpc.module.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.AdminApplication;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplate;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplateInfo;
import com.wanfangdata.cpc.module.admin.service.db.DbTemplateInfoService;
import com.wanfangdata.cpc.module.admin.service.db.DbTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.service
 * @Classname DbTemplateTest
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/07 10:14
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class DbTemplateTest {
    @Autowired
    private DbTemplateInfoService dbTemplateInfoService;
    @Autowired
    private DbTemplateService dbTemplateService;

    @Test
    public void getDbTemplateInfo() {
        List<DbTemplateInfo> list = dbTemplateInfoService.list();
        System.out.println(list.size());
    }

    @Test
    public void getDbTemplateByPage() {
        IPage<DbTemplate> data = dbTemplateService.findByCondition(new DbTemplate(), 0, 10);
        List<DbTemplate> templates = data.getRecords();
        long size = data.getTotal();
        System.out.println(templates.size());
        System.out.println(size);
    }

    @Test
    public void deleteDbTemplate() {
        boolean s = dbTemplateService.removeById(12);
        System.out.println(s);
        IPage<DbTemplate> data = dbTemplateService.findByCondition(new DbTemplate(), 0, 10);
        List<DbTemplate> templates = data.getRecords();
        long size = data.getTotal();
        System.out.println(size);
    }

    @Test
    public void updateDbTemplate() {
        DbTemplate dbTemplate = new DbTemplate();
        dbTemplate.setId(6);
        dbTemplate.setTemplateId(1222);
        dbTemplate.setThematicClassification("update1");
        dbTemplate.setSmallAlbumLogo("update1");
        dbTemplate.setAreaClassification("update1");
        dbTemplate.setDbId(99);
        dbTemplate.setAreaClassificationPicture("update1");
        dbTemplate.setBigAlbumLogo("update1");
        dbTemplate.setButtonColor("update1");
        dbTemplate.setClassificationPicture("update1");
        dbTemplate.setClickColor("update1");
        dbTemplate.setSecondLevelPicture("update1");
        dbTemplate.setHomepagePicture("update1");
        dbTemplate.setUpdateTime(new Date());
        System.out.println(dbTemplate.getId());
        System.out.println(dbTemplate.getAreaClassification());
        System.out.println(dbTemplate.getAreaClassificationPicture());
        System.out.println(dbTemplate.getBigAlbumLogo());
        System.out.println(dbTemplate.getButtonColor());
        System.out.println(dbTemplate.getClassificationPicture());
        System.out.println(dbTemplate.getDbId());
        System.out.println(dbTemplate.getHomepagePicture());
        System.out.println(dbTemplate.getSecondLevelPicture());
        System.out.println(dbTemplate.getSmallAlbumLogo());
        System.out.println(dbTemplate.getTemplateId());
        System.out.println(dbTemplate.getThematicClassification());
        boolean i = dbTemplateService.saveOrUpdate(dbTemplate);
        System.out.println(i);
    }


}
