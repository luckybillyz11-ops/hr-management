package org.billyz.hrmanagement.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.billyz.hrmanagement.annotation.LogRecord;
import org.billyz.hrmanagement.dao.EmployeeDao;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.service.EmpService;
import org.billyz.hrmanagement.vo.EmployeeByDeptVO;
import org.billyz.hrmanagement.vo.EmployeeDetailVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmpService {
    @LogRecord("按员工编号或姓名查询员工信息")
    @Override
    public List<Employee> queryByIdOrName(Integer empno, String ename) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(empno != null, Employee::getEmpno, empno)
                .like(ename != null && !ename.isBlank(), Employee::getEname, ename);

        return this.list(wrapper);
    }
    @LogRecord("更新员工信息")
    @Override
    public Integer updateEmp(Employee employee) {
        return this.baseMapper.updateById(employee);
    }
    @LogRecord("删除员工")
    @Override
    public int deleteEmp(Integer empno) {
        boolean result = this.removeById(empno);
        return result ? 1 : 0;
    }
    @LogRecord("添加员工")
    @Override
    public int insertEmp(Employee employee){
        if (employee.getStatus() == null) {
            employee.setStatus("待入职");
        }
        employee.setDeptno(0);
        return this.baseMapper.insert(employee);
    }
    
    @LogRecord("查询员工特定字段")
    @Override
    public IPage<EmployeeDetailVO> queryEmployeeDetailsPage(Integer current, Integer size) {
        Page<EmployeeDetailVO> page = new Page<>(current, size);
        // 现在 MyBatis 会自动查询下级员工，无需手动填充
        return this.baseMapper.queryEmployeeDetailsPage(page);
    }

    
    @LogRecord("按部门查询员工信息")
    @Override
    public List<EmployeeByDeptVO> queryEmployeesByDeptno(Integer deptno) {
        return this.baseMapper.queryEmployeesByDeptno(deptno);
    }
    
}
