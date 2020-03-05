package com.ybs.springcloud.service;

import com.ybs.springcloud.pojo.Dept;

import java.util.List;

/**
 * DeptService
 *
 * @author Paulson
 * @date 2020/3/5 23:13
 */

public interface DeptService {

    public boolean addDept(Dept dept);

    public Dept queryById(Long id);

    public List<Dept> queryAll();
}
