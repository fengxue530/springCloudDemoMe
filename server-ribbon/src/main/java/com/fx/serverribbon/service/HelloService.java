package com.fx.serverribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @auther: mabaofeng
 * @date: 2019/8/12 16:38
 * @description:
 */
@Service
public class HelloService {

    @Autowired
    private RestTemplate restTemplate;

    //该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法
    @HystrixCommand(fallbackMethod ="fallbackMethod")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    public String fallbackMethod(String name){
        return "hi,"+name+",sorry,error!";
    }
}