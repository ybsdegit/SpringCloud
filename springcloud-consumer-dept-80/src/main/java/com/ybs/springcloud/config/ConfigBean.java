package com.ybs.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ConfigBean
 *
 * @author Paulson
 * @date 2020/3/5 23:32
 */

@Configuration
public class ConfigBean {

    // 配置负载均衡实现 RestTemplate
    @Bean
    @LoadBalanced  // Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}
