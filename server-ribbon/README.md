

## 进入熔断器页面
http://localhost:8764/hystrix

## 进入熔断器仪表盘
http://localhost:8764/hystrix.stream


## 注意启动类中的这段代码,加上这代码才显示仪表盘

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }