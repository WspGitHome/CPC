package com.wanfangdata.cpc.service;

import com.wanfangdata.oss.model.*;
import java.util.List;

/**
 *@program: Fulltext
 *@description: 方志目录
 *@author: FLY
 *@create: 2020-05-06 14:52
 */
public interface CatalogueService{


    public List<CatalogueNode> catalogue(String id)  throws Exception ;

    public List<VolumeNode> volumes(String id)  throws Exception ;

}