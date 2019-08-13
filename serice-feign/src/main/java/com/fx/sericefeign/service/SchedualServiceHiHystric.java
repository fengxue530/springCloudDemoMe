package com.fx.sericefeign.service;

import org.springframework.stereotype.Component;

/**
 * @auther: mabaofeng
 * @date: 2019/8/13 14:39
 * @description: fegin 基于熔断机制，增加的默认异常信息，   每一个FeignClient 接口都对应一个 默认的返回方法
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry" + name;
    }
}
