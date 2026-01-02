package org.billyz.hrmanagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.billyz.hrmanagement.entity.Department;

public interface DeptService extends IService<Department> {

    /**
     * 删除部门
     * @param deptno 部门编号
     * @return 删除结果消息
     */
    String deleteDepartment(Integer deptno);
}
