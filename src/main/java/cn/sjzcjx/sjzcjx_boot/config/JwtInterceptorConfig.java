package cn.sjzcjx.sjzcjx_boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 22:33
 **/

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Resource
    JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/file/upload", "/file/delete")
                .addPathPatterns("/resource/*")
                .addPathPatterns("/sort/*")
                .addPathPatterns("/url/*")
                .addPathPatterns("/user/*");
    }
}
