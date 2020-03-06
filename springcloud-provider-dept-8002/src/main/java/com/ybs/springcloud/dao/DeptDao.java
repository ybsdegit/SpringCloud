package com.ybs.springcloud.dao;

import com.ybs.springcloud.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DeptDao
 *
 * @author Paulson
 * @date 2020/3/5 23:04
 */

@Mapper
@Repository
public interface DeptDao {

    public boolean addDept(Dept dept);

    public Dept queryById(Long id);

    public List<Dept> queryAll();
}
