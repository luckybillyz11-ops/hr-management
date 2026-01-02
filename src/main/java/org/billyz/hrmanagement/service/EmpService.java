package org.billyz.hrmanagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.vo.EmployeeByDeptVO;
import org.billyz.hrmanagement.vo.EmployeeDetailVO;

import java.util.List;

public interface EmpService extends IService<Employee> {

    List<Employee> queryByIdOrName(Integer empno, String ename);

    Integer updateEmp(Employee employee);
    
    int deleteEmp(Integer empno);

    int insertEmp(Employee employee);
    
    /**
     * 分页查询员工详细信息
     * @param current 当前页码
     * @param size 每页大小
     * @return 员工详细信息分页数据
     */
    IPage<EmployeeDetailVO> queryEmployeeDetailsPage(Integer current, Integer size);
    
    /**
     * 按部门查询员工信息
     * @param deptno 部门编号
     * @return 员工信息列表（包含：员工名称、职位、雇佣时间、联系方式、上级员工姓名）
     */
    List<EmployeeByDeptVO> queryEmployeesByDeptno(Integer deptno);

    
}
