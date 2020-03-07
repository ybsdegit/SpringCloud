package com.ybs.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ybs.springcloud.pojo.Dept;
import com.ybs.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeptControlller
 * 提供 restful 服务
 * @author Paulson
 * @date 2020/3/5 23:15
 */

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    // 获取一些配置信息
    @Autowired
    private DiscoveryClient client;

    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixGet")
    public Dept get(@PathVariable("id") Long id){
        Dept dept = deptService.queryById(id);
        if (dept == null){
            throw new RuntimeException("id => " + id + ", 不存在该用户，或者信息无法找到");
        }
        return dept;
    }

    // 备选方案
    public Dept hystrixGet(@PathVariable("id") Long id){
        return new Dept()
                .setDeptno(id)
                .setDname("id => " + id + "没有对应的信息, null => @hystrix")
                .setDb_source("no this database in mysql");
    }

}
