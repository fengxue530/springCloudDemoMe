package com.fx.configclient.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: mabaofeng
 * @date: 2019/8/14 13:50
 * @description:
 */
@RestController
@RefreshScope
public class TestController {


    @Value("${username}")
    private String userName;

    @Value("${password}")
    private String password;


    @RequestMapping("/test")
    public Object test() {
        Map<String, Object> model = new HashMap<>();
        model.put("getMode", "@Value");
        Map<String, String> remoteCgfMap = new HashMap<>();
        remoteCgfMap.put("clientUserName", userName);
        remoteCgfMap.put("clientPassword", password);
        model.put("remoteConfig", remoteCgfMap);
        return model;
    }
}
