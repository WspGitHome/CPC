package com.wanfangdata.cpc.module.admin.controller;

import com.baidu.ueditor.ActionEnter;
import com.wanfangdata.cpc.common.config.properties.FileUploadProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;

/**
 * create by lorne on 2017/12/24
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController {

    private Logger logger = LoggerFactory.getLogger(UeditorController.class);

    @Autowired
    private FileUploadProperties fileUploadProperties;

    private String rootPath;

    public UeditorController() {
        String path  = UeditorController.class.getClassLoader().getResource("config.json").getPath();
        logger.info("path->"+path);
        File file =  new File(path);
        if(file.getParentFile().isDirectory()) {
            rootPath = new File(path).getParent()+"/";
        }else{
            rootPath = new File(path).getParentFile().getParent()+"/";
            rootPath = rootPath.replace("file:","");
        }
    }


    @RequestMapping("/exec")
    public void exec(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        response.setHeader("Content-Type" , "text/html");
        out.write( new ActionEnter( request, fileUploadProperties.getUploadFolder(),fileUploadProperties.getAccessPathPattern()).exec());
    }

}
