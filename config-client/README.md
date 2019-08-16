

## 手动刷新更改后的配置,只能刷新一个 client的配置
## 自己测试,同时刷新了 8771 8772 2个client 实例
http://localhost:8771/actuator/refresh

## 使用 rabbitmq 刷新所有 client的配置， 项目如果在公网，可以配置 github的webhooks，实现修改配置自动推送消息到rabbitmq
http://localhost:8771/actuator/bus-refresh



## 手动刷新 /actuator/refresh  与 /actuator/bus-refresh 的区别

1. config-server 多实例的情况下
   有2个config-server,名字为 config-server1  config-server
   
   config-server1 对应 config-client-8773
   config-server 对应 config-client-8771 、config-client-8772
   
   发送post请求 http://localhost:8773/actuator/refresh, 则只能更新 config-client-8773 下的 client实例
   
   发送post请求 http://localhost:8773/actuator/bus-refresh, 则可以更新 所有 client实例
   
2. /actuator/bus-refresh 更加灵活   可以执行服务刷新  