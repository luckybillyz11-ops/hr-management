package org.billyz.hrmanagement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 员工联系信息表
 */
@Data
@TableName("employee_profile")
public class EmployeeProfile {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    private Integer employeeId;  // 对应 emp.empno
    
    private String phone;
    
    private String email;
    
    // 紧急联系人信息（JSONB 类型，存储为 List<Map>）
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String, Object>> emergencyContacts;
    
    @TableLogic(value = "false", delval = "true")
    private Boolean isDeleted;
}










