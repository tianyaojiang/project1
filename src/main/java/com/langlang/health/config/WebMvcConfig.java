package com.langlang.health.config;

import com.langlang.health.common.interceptor.UserActionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by tyj on 2019/01/09.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public UserActionInterceptor userActionInterceptor(){
        return new UserActionInterceptor();
    }
    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 路径根据后期项目的扩展，进行调整
        registry.addInterceptor(userActionInterceptor())
                .addPathPatterns("/user/**", "/auth/**")
                .excludePathPatterns("/user/sendMsg", "/user/login");

    }
    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
