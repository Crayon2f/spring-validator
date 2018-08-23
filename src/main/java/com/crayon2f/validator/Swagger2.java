package com.crayon2f.validator;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by feiFan.gou on 2018/6/4 20:18.
 */
@Component
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {

        ApiInfo info = new ApiInfo(
                "spring-validator",
                "spring 参数校验, 分为单独参数方式,和Bean 方式",
                "1.0",
                "http://www.baidu.com",
                new Contact("Crayon2f", "15010780830", "feifan.gou@gmail.com"),
                "license",
                "https://github.com/Crayon2f",
                Lists.newArrayList());

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.crayon2f.validator.controller"))
                .paths(PathSelectors.any()).build()
                .apiInfo(info)
                .useDefaultResponseMessages(false);
    }
}
