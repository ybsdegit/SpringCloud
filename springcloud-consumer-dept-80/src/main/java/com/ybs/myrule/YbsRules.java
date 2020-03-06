package com.ybs.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * YbsRules
 *
 * @author Paulson
 * @date 2020/3/7 1:58
 */

@Configuration

public class YbsRules {

    @Bean
    public IRule myRule(){
        return new YbsRandomRule();  // 默认是轮询，切换为自定义的
    }



}
