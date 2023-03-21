### 带有实时弹幕推送的在线课程学习平台（https://github.com/xzwnp/online-mooc） 
> 说明: 这是本项目的后端部分,用户前端见(https://github.com/xzwnp/online-mooc-web),后台管理系统见(https://github.com/xzwnp/online-mooc-admin)
#### 项目介绍

本系统采用微服务架构设计，包括课程详情、视频点播、课程秒杀、用户、订单、统计分析等模块。并在传统慕平台的基础上，引入了弹幕系统创造良好的课程讨论环境。本人主要完成架构设计、弹幕模块和课程秒杀模块的开发等工作。

#### 主要技术

SpringBoot，SpringCloud，MybatisPlus，Shiro，MySQL，Redis，RabbitMQ，Docker等

#### 项目亮点

* 采用微服务架构开发，各模块之间耦合度低，涉及到的微服务理念包括服务发现、配置中心、RPC、微服务网关、服务熔断、降级、限流等。
* 使用RabbitMQ实现弹幕异步存储，并解决分布式环境下WebSocket的实时弹幕推送问题。
* 使用Redisson分布式锁解决秒杀课程重复上架的幂等性问题。
* 使用Redis防止秒杀课程超卖、重复购买；秒杀成功利用消息队列异步保存到数据库。
* 敏感操作需权限校验，并异步生成操作日志投递到ELK。
* 使用Docker-Compose实现项目的容器化部署。
