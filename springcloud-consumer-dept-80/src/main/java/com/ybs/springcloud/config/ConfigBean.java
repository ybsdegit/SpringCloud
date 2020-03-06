package com.ybs.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
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
    // IRule
    // RandomRule 随机
    // RoundRobinRule 轮询
    // AvailabilutyFilteringRule 过滤掉跳闸，访问故障的服务，对剩下的服务进行轮询
    // RetryRule 先按照轮询获取服务，如果获取服务失败，则会在指定的时间内进行重试
    //
    @Bean
    @LoadBalanced  // Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    // 随机访问
    /*@Bean
    public IRule myRule(){
        return new RandomRule();
    }
    */

}
