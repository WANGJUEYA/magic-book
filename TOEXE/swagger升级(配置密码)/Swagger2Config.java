package com.system.core.config;

import com.zh.base.constant.CommonConstant;
import com.zh.core.config.SystemConfig;
import com.zh.core.security.sysclient.SysClientConfigUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger-ui rest接口文档 配置
 *
 * @author YoungTH Zeus
 * @date 2020/4/20
 */
@Configuration
@EnableOpenApi
@ConditionalOnProperty(prefix = SystemConfig.PREFIX, name = "swagger-open", havingValue = "true")
public class Swagger2Config implements WebMvcConfigurer {
    /**
     *
     */
    @Bean
    public Docket createRestApi(SystemConfig systemConfig) {
        //添加head参数配置start
        List<RequestParameter> pars = new ArrayList<>();
        pars.add(new RequestParameterBuilder().in(ParameterType.HEADER).required(false)
                .name(CommonConstant.ACCESS_TOKEN).description(CommonConstant.ACCESS_TOKEN).build());
        pars.add(new RequestParameterBuilder().in(ParameterType.HEADER).required(false)
                .name(SysClientConfigUtils.CLIENT_ID).description(SysClientConfigUtils.CLIENT_ID).build());
        pars.add(new RequestParameterBuilder().in(ParameterType.HEADER).required(false)
                .name(SysClientConfigUtils.CLIENT_SECRET).description(SysClientConfigUtils.CLIENT_SECRET).build());

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(systemConfig))
                .select()
                //此包路径下的类，才生成接口文档
//                .apis(RequestHandlerSelectors.basePackage("com.zh"))
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(pars);
    }

    /**
     * 文档显示信息
     */
    private ApiInfo apiInfo(SystemConfig systemConfig) {
        return new ApiInfoBuilder()
                //大标题
                .title("zh-boot 接口平台")
                //描述
                .description("zh-boot 接口平台")
                //版本号
                .version(systemConfig.getVersion())
                .build();
    }

}
