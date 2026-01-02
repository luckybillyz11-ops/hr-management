package org.billyz.hrmanagement.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 按部门查询员工信息视图对象
 */
@Data
public class EmployeeByDeptVO {

    /**
     * 员工编号
     */
    private Integer empno;

    /**
     * 员工姓名
     */
    private String ename;

    /**
     * 职位
     */
    private String job;

    /**
     * 雇佣时间
     */
    private LocalDateTime hiredate;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上级员工姓名
     */
    private String managerName;
}

