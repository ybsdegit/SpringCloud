package com.ybs.springcloud.controller;

import com.ybs.springcloud.pojo.Dept;
import com.ybs.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/dept/add")
    public boolean addDept(Dept dept){
        return deptService.addDept(dept);
    }

    @GetMapping("/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return deptService.queryById(id);
    }

    @GetMapping("/dept/list")
    public List<Dept> getAll(){
        return deptService.queryAll();
    }

    // 注册进来的微服务，获取一些消息

    @GetMapping("/dept/discovery")
    public Object Discovery(){
        // 获取微服务列表清单
        List<String> services = client.getServices();
        System.out.println("discover: " + services);

        // 得到一个具体的微服务信息,通过具体的微服务id
        List<ServiceInstance> instances = client.getInstances("SPRINGCLOUD-PROVIDER-DEPT");
        for (ServiceInstance instance : instances) {
            System.out.println(
                    instance.getHost() + "\t" +
                    instance.getUri() + "\t" +
                    instance.getPort() + "\t" +
                    instance.getServiceId()
                    );
        }
        return this.client;
    }

}
