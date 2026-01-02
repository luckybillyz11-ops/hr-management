package org.billyz.hrmanagement.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 员工详细信息视图对象
 */
@Data
public class EmployeeDetailVO {

    /**
     * 员工编号
     */
    private Integer empno;

    /**
     * 员工姓名
     */
    private String ename;

    /**
     * 部门名称
     */
    private String dname;

    /**
     * 总收入（薪资 + 佣金）
     */
    private BigDecimal totalIncome;

    /**
     * 薪资等级
     */
    private Integer salaryGrade;

    /**
     * 下级员工姓名列表
     */
    private List<String> subordinates;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
