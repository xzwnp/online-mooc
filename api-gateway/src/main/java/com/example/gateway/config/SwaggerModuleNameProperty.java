package com.example.gateway.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.example.gateway.entity
 *
 * @author xiaozhiwei
 * 2022/12/8
 * 14:20
 * 对配置文件进行增强,现在可以指定swagger的模块名
 */
@ConfigurationProperties("spring.cloud.gateway")
@Slf4j
public class SwaggerModuleNameProperty implements InitializingBean {


    private List<GatewayRouter> routes = new ArrayList<>();


    private static final Map<String, String> MODULE_ID_NAME_MAP = new HashMap<>();

    /**
     * @param id 配置文件中router的Id
     * @return 配置文件router
     * 可以通过routerId获取模块名
     */
    public String getModuleNameById(String id) {
        return MODULE_ID_NAME_MAP.get(id);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        StringBuilder routerInfo = new StringBuilder();
        routerInfo.append("发现以下路由信息:");
        routes.forEach(route -> {
            MODULE_ID_NAME_MAP.put(route.getId(), route.getSwaggerName());
            routerInfo.append("[").append(route.getId()).append("-").append(route.getSwaggerName()).append("]").append(",");
        });
        log.info(routerInfo.toString());
    }

    public void setRoutes(List<GatewayRouter> routes) {
        this.routes = routes;
    }

    @Data
    static class GatewayRouter {
        private String id;
        private String swaggerName;
    }
}
