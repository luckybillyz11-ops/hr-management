package org.billyz.hrmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dept")
public class Department {
    @TableId(value = "deptno", type = IdType.AUTO)
    private Integer deptno;

    private String dname;

    private String loc;
}
