package com.fx.scfgatewayfirstsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 1. http://localhost:8080/get 走第一种断言
 * //TODO
 * 2. curl --dump-header - --header 'Host: www.hystrix.com' http://localhost:8080/delay/3    这种写法没看懂
 */
@SpringBootApplication
@RestController
public class ScFGatewayFirstSightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScFGatewayFirstSightApplication.class, args);
    }

    /**
     *   -> p   代表断言，相当于判断条件
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f ->
                                // 添加了自定义头
                                f.addRequestHeader("Hello", "World")
                        )
                        .uri("http://httpbin.org:80"))
                .route(p ->p
                        //此处判断域名包含下面的字符串
                        .host("*.hystrix.com")
                        .filters(
                                f->f
                            .hystrix(config ->
                                    config.setName("myTest")
                                            // 阻断后默认的方法
                                            .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }


    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        Map<String,Object> map = new HashMap<>(16);
        map.put("Lina","我没看懂");
        map.put("xiaoxiao","我也没看懂啊！");
        return Mono.just(map.toString());
    }

}
