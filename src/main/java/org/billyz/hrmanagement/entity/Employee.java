package org.billyz.hrmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("emp")
public class Employee {
    @TableId(value = "empno", type = IdType.AUTO)
    //员工编号
    private Integer empno;
    //员工姓名
    private String ename;
    //员工状态
    private String status;
    //员工职位
    private String job;
    //员工上级
    private Integer mgr;
    //入职日期
    private LocalDateTime hiredate;
    //员工薪资
    private BigDecimal sal;
    //员工佣金
    private BigDecimal comm;
    //员工部门
    private Integer deptno;
    
    //软删除标记
    @TableLogic(value = "false", delval = "true")
    private Boolean isDeleted;
}
