
## 使用熔断器（hystrix）和熔断器监控（hystrix-dashboard）需要在启动类中增加以下代码
 1.   //加上此代码才能访问 熔断器仪表盘
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
    
  2. yml 文件中增加配置
      management:
        endpoints:
          web:
            exposure:
              include: "*"
            cors:
              allowed-origins: "*"
              allowed-methods: "*"  
               
  3. 接口一定要配置熔断时默认调用的方法，例如 com.fx.eurekaclient.EurekaClientApplication.fallbackMethod
     @HystrixCommand(fallbackMethod ="fallbackMethod")
              