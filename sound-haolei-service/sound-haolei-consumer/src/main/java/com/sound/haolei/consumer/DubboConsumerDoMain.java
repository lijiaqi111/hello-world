package com.sound.haolei.consumer;

import com.sound.haolei.consumer.interceptor.CommonIntercepter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = "com.sound.haolei.consumer.controller")
public class DubboConsumerDoMain {
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerDoMain.class,args);
    }

    @Configuration
    static class WebMvcConfigurer extends WebMvcConfigurerAdapter {
        @Override
        public void addInterceptors(InterceptorRegistry registry){
            registry.addInterceptor(new CommonIntercepter())    //指定拦截器类
                    .addPathPatterns("/oldrecycle/**");        //指定该类拦截的url
            super.addInterceptors(registry);
        }
    }
}
