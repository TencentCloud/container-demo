package demo.tcloud.triblewood.qcbm.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("demo.tcloud.triblewood.qcbm"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("Book Management System REST APIs")
                .description("图书管理系统 API 文档，本页面采用 swagger 自动生成，可以查看图书服务对外提供的所有 API 及其对应的方法和参数说明，同时提供了在线测试功能。关于 swagger 的详细功能，请访问其官网：https://swagger.io")
                .termsOfServiceUrl("https://cloud.tencent.com")
                .contact(new Contact("森林木", "https://cloud.tencent.com", "yuanlinbao@tencent.com"))
                .build();
    }
}
