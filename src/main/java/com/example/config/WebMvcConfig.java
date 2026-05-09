package com.example.config;

import com.example.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC の設定を行うクラス.
 *
 * @author shunsei
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /** ログインチェックの Interceptor */
    private final LoginCheckInterceptor authInterceptor;

    /**
     * コンストラクタ.
     *
     * @param authInterceptor LoginCheckInterceptor
     */
    public WebMvcConfig(LoginCheckInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    /**
     * インターセプターを登録する.
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/employees")
                .addPathPatterns("/employees/detail");
    }
}