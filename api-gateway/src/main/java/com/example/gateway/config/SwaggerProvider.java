package com.example.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 原理是 请求指定服务的下的json格式的swagger数据,然后在gateway模块重新渲染
 */
@Component
@Primary //有多个满足条件的依赖时,优先注入本依赖
@Slf4j
@EnableConfigurationProperties(SwaggerModuleNameProperty.class)
public class SwaggerProvider implements SwaggerResourcesProvider {

    //swagger json版
    public static final String API_URI = "/v3/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    private final SwaggerModuleNameProperty swaggerGatewayProperty;

    public SwaggerProvider(RouteLocator routeLocator, GatewayProperties gatewayProperties, SwaggerModuleNameProperty property) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
        this.swaggerGatewayProperty = property;
    }

    /**
     * 根据配置文件构造url,上层模块去这个url请求json格式的swagger数据
     * 说明:routeLocator获取的是处于同一注册中心的服务的路由,
     * gatewayProperties从配置文件中读取,无需去重
     *
     * @return
     */
    @Override
    public List<SwaggerResource> get() {
        //SwaggerResource：处理的是UI页面中顶部的选择框以及拉取到每个微服务上swagger接口文档的json数据。
        List<SwaggerResource> resources = new ArrayList<>();

        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        // 结合配置的route-路径(Path)，和route过滤，只获取在枚举中说明的route节点
        gatewayProperties.getRoutes().stream()
                //过滤无效配置文件
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                //一个id一个routeDefinition,,每个routeDefinition下面可以有多个断言
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        // 目前只处理Path断言  Header或其他路由需要另行扩展
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> {
                                    String routeId = routeDefinition.getId();
                                    String moduleName = swaggerGatewayProperty.getModuleNameById(routeId);
                                    if (StringUtils.hasLength(moduleName)) {
                                        resources.add(swaggerResource(moduleName, predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI)));
                                    }
                                }
                        ));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }

}