package com.gugu.upload.config;

import com.gugu.upload.config.filter.AuthFilter;
import com.gugu.upload.config.filter.RecordFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Filter config.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Configuration
public class FilterConfig {

    /**
     * Auth filter filter registration bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> authFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        authFilterFilterRegistrationBean.setFilter(new AuthFilter());
        authFilterFilterRegistrationBean.addUrlPatterns("/*");
        return authFilterFilterRegistrationBean;
    }

    /**
     * Record filter filter registration bean filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<RecordFilter> recordFilterFilterRegistrationBean(){
        FilterRegistrationBean<RecordFilter> authFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        authFilterFilterRegistrationBean.setFilter(new RecordFilter());
        authFilterFilterRegistrationBean.addUrlPatterns("/*");
        return authFilterFilterRegistrationBean;
    }
}
