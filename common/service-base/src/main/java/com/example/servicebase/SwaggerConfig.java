package com.example.servicebase;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean(value = "userApi")
	@Order(value = 1)
	public Docket groupRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(groupApiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.example"))
			.paths(PathSelectors.any())
			.build();
	}

	private ApiInfo groupApiInfo() {
		return new ApiInfoBuilder()
			.title("在线课程学习平台 接口文档")
			.description("<div style='font-size:14px;color:red;'>参照大多数在线教学网站或慕课网站，为某一公司设计一个方便、实用的在线课程学习平台。该平台主要用于管理和向用户发布公司所有版权的相应学习资料（包括视频、PDF版本讲义等）</div>")
			.termsOfServiceUrl("http://www.yidongzhenka.top/")
			.contact("xiao@ynu.icu")
			.version("1.0")
			.build();
	}


}
