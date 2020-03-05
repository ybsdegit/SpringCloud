package com.ybs.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Dept
 *
 * @author Paulson
 * @date 2020/3/5 22:37
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)  //链式写法
public class Dept implements Serializable {

    private Long deptno; // 主键
    private String dname;

    // 这个数据是存在哪个数据库的字段~ 微服务，一个服务对应一个数据库
    // 用一个信息可能存在不同的数据库
    private String db_source;

    public Dept(String dname) {
        this.dname = dname;
    }

    /*
    链式写法：
        Dept dept = new Dept()
        dept.setDeptNo(11).setDname("test")
     */
}
