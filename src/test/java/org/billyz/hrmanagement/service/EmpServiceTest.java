package org.billyz.hrmanagement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.vo.EmployeeByDeptVO;
import org.billyz.hrmanagement.vo.EmployeeDetailVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class EmpServiceTest {
    
    @Resource
    private EmpService empService;
    
    /**
     * 测试按员工编号查询
     */
    @Test
    public void testQueryById() {
        // 查询存在的员工（假设数据库中有编号为7369的员工）
        List<Employee> employees = empService.queryByIdOrName(7839, null);
        Assertions.assertNotNull(employees);
        Assertions.assertFalse(employees.isEmpty());
        
        // 验证查询结果
        Employee employee = employees.get(0);
        Assertions.assertEquals(7839, employee.getEmpno());
        Assertions.assertNotNull(employee.getEname());
    }
    
    /**
     * 测试按员工姓名模糊查询
     */
    @Test
    public void testQueryByName() {
        // 按姓名模糊查询
        List<Employee> employees = empService.queryByIdOrName(null, "张");
        Assertions.assertNotNull(employees);
        // 验证所有结果都包含"张"
        employees.forEach(emp -> {
            Assertions.assertTrue(emp.getEname().contains("张"));
        });
    }
    
    /**
     * 测试按编号和姓名同时查询
     */
    @Test
    public void testQueryByIdAndName() {
        List<Employee> employees = empService.queryByIdOrName(7839, "任正非");
        Assertions.assertNotNull(employees);
        if (!employees.isEmpty()) {
            Employee employee = employees.get(0);
            Assertions.assertEquals(7839, employee.getEmpno());
            Assertions.assertTrue(employee.getEname().contains("任正非"));
        }
    }
    
    /**
     * 测试查询所有员工（参数都为null）
     */
    @Test
    public void testQueryAll() {
        List<Employee> employees = empService.queryByIdOrName(null, null);
        Assertions.assertNotNull(employees);
        // 至少应该有一些员工数据
        Assertions.assertFalse(employees.isEmpty());
    }
    
    /**
     * 测试更新员工信息
     */
    @Test
    public void testUpdateEmp() {
        // 先查询一个员工
        List<Employee> employees = empService.queryByIdOrName(7839, null);
        if (employees.isEmpty()) {
            System.out.println("测试数据不存在，跳过更新测试");
            return;
        }
        
        Employee employee = employees.get(0);
        String originalName = employee.getEname();
        String newName = originalName + "_测试";
        
        // 更新员工姓名
        employee.setEname(newName);
        Integer result = empService.updateEmp(employee);
        
        // 验证更新结果
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result > 0);
        
        // 验证更新后的数据
        List<Employee> updatedEmployees = empService.queryByIdOrName(employee.getEmpno(), null);
        Assertions.assertFalse(updatedEmployees.isEmpty());
        Assertions.assertEquals(newName, updatedEmployees.get(0).getEname());
        
        // 恢复原始数据（测试回滚会自动处理）
    }
    
    /**
     * 测试分页查询员工详细信息
     */
    @Test
    public void testQueryEmployeeDetailsPage() {
        // 查询第一页，每页10条
        IPage<EmployeeDetailVO> page = empService.queryEmployeeDetailsPage(1, 10);
        
        Assertions.assertNotNull(page);
        Assertions.assertTrue(page.getTotal() > 0);
        Assertions.assertFalse(page.getRecords().isEmpty());
        Assertions.assertTrue(page.getRecords().size() <= 10);
        
        // 验证每条记录的字段
        page.getRecords().forEach(emp -> {
            Assertions.assertNotNull(emp.getEmpno());
            Assertions.assertNotNull(emp.getEname());
            // subordinates 可能为空，但不应该为null
            Assertions.assertNotNull(emp.getSubordinates());
        });
    }
    
    
    /**
     * 测试查询不存在的员工
     */
    @Test
    public void testQueryNonExistentEmployee() {
        List<Employee> employees = empService.queryByIdOrName(99999, null);
        Assertions.assertNotNull(employees);
        Assertions.assertTrue(employees.isEmpty());
    }
    
    /**
     * 测试按部门查询员工信息
     */
    @Test
    public void testQueryEmployeesByDeptno() {
        List<EmployeeByDeptVO> employees = empService.queryEmployeesByDeptno(10);
        
        Assertions.assertNotNull(employees);
        if (!employees.isEmpty()) {
            EmployeeByDeptVO emp = employees.get(0);
            Assertions.assertNotNull(emp.getEmpno());
            Assertions.assertNotNull(emp.getEname());
            Assertions.assertNotNull(emp.getJob());
            Assertions.assertNotNull(emp.getHiredate());
        }
    }
    
    /**
     * 测试按部门查询 - 不存在的部门应返回空列表
     */
    @Test
    public void testQueryEmployeesByNonExistentDeptno() {
        List<EmployeeByDeptVO> employees = empService.queryEmployeesByDeptno(999);
        Assertions.assertNotNull(employees);
        Assertions.assertTrue(employees.isEmpty());
    }
}
