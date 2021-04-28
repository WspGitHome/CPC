package com.wanfangdata.grpc.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Packagename com.wanfangdata.grpc.server.config
 * @Classname Config
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/09/03 10:37
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "map")
@Component
public class Config {
    public Map<String, String> dbMap;
    public Map<String, String> coreMap;
    public Map<String, String> columMap;
    public Map<String, String> dialectMap;

    public Map<String, String> getDialectMap() {
        return dialectMap;
    }

    public void setDialectMap(Map<String, String> dialectMap) {
        this.dialectMap = dialectMap;
    }

    public Map<String, String> getDbMap() {
        return dbMap;
    }

    public void setDbMap(Map<String, String> dbMap) {
        this.dbMap = dbMap;
    }

    public Map<String, String> getCoreMap() {
        return coreMap;
    }

    public void setCoreMap(Map<String, String> coreMap) {
        this.coreMap = coreMap;
    }

    public Map<String, String> getColumMap() {
        return columMap;
    }

    public void setColumMap(Map<String, String> columMap) {
        this.columMap = columMap;
    }
}
