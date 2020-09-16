package com.wy.configuration;

import com.wy.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new UserFilter());
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/orders/*");
        filterRegistrationBean.addUrlPatterns("/userInfo.html");
        return filterRegistrationBean;
    }
}
