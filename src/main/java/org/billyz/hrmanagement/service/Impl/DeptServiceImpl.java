package org.billyz.hrmanagement.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.billyz.hrmanagement.annotation.LogRecord;
import org.billyz.hrmanagement.dao.DepartmentDao;
import org.billyz.hrmanagement.dao.EmployeeDao;
import org.billyz.hrmanagement.entity.Department;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.service.DeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeptServiceImpl extends ServiceImpl<DepartmentDao, Department> implements DeptService {

    @Resource
    private EmployeeDao employeeDao;
    @LogRecord("删除部门")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteDepartment(Integer deptno) {
        // 1. 检查部门是否存在
        Department department = this.getById(deptno);
        if (department == null) {
            return "部门不存在";
        }

        // 2. 将该部门下的所有员工部门改为 0（待分配），保持原状态不变
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getDeptno, deptno);
        
        // 先查询员工数量（用于返回提示信息）
        Long employeeCount = employeeDao.selectCount(queryWrapper);
        
        // 批量更新：一次SQL更新所有员工的部门
        if (employeeCount > 0) {
            Employee updateEmployee = new Employee();
            updateEmployee.setDeptno(0);  // 只修改部门为待分配
            employeeDao.update(updateEmployee, queryWrapper);
        }

        // 3. 软删除部门（MyBatis-Plus 会自动将 is_deleted 设为 true）
        boolean removed = this.removeById(deptno);
        
        if (removed) {
            return employeeCount > 0 
                ? "删除成功，" + employeeCount + "名员工已转为待入职状态" 
                : "删除成功";
        }
        
        return "删除失败";
    }
}
