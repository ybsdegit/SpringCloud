package com.ybs.springcloud.service.Impl;

import com.ybs.springcloud.pojo.Dept;
import com.ybs.springcloud.service.DeptClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DempClientFallbackFactory
 *
 * @author Paulson
 * @date 2020/3/7 23:18
 */
// 降级
@Component
public class DeptClientFallbackFactory implements FallbackFactory {

    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            public Dept queryById(Long id) {
                return new Dept()
                        .setDeptno(id)
                        .setDname("id=>"+id+"没有对应的信息。客户端提供了降级的信息，这个服务已经被关闭")
                        .setDb_source("没有数据");
            }

            public List<Dept> queryAll() {


                return null;
            }

            public boolean addDept(Dept dept) {
                return false;
            }
        };
    }
}
