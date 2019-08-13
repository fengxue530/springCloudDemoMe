package com.fx.serverribbon.web;

import com.fx.serverribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: mabaofeng
 * @date: 2019/8/12 16:38
 * @description:
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;
    @RequestMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return helloService.hiService(name);
    }
}
