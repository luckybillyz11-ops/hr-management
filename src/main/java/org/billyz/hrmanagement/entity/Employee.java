package org.billyz.hrmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("emp")
public class Employee {
    @TableId(value = "empno", type = IdType.AUTO)
    private Integer empno;

    private String ename;

    private String status;

    private String job;

    private Integer mgr;

    private LocalDateTime hiredate;

    private BigDecimal sal;

    private BigDecimal comm;

    private Integer deptno;
}
