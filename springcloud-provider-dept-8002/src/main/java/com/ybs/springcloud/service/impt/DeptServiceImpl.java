package com.ybs.springcloud.service.impt;

import com.ybs.springcloud.dao.DeptDao;
import com.ybs.springcloud.pojo.Dept;
import com.ybs.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DeptServiceImpl
 *
 * @author Paulson
 * @date 2020/3/5 23:14
 */

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    public boolean addDept(Dept dept) {
        return deptDao.addDept(dept);
    }

    public Dept queryById(Long id) {
        return deptDao.queryById(id);
    }

    public List<Dept> queryAll() {
        return deptDao.queryAll();
    }
}
