package org.billyz.hrmanagement.service.impl;

import jakarta.annotation.Resource;
import org.billyz.hrmanagement.dao.EmployeeDao;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmpServiceImpl implements EmpService {
    private static final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);
    @Resource
    private EmployeeDao empDao;

    @Override
    public Employee getEmpById(Integer empno) {
        return empDao.selectById(empno);
    }
    @Override
    public List<Employee> getAllEmps() {
        return empDao.selectList(null);
    }
    @Override
    public Employee getEmpByName(String ename) {
        return empDao.selectEmpByName(ename);
    }

    @Override
    public int deleteEmpById(Integer empno) {
        log.info("删除员工【入参】:empno={}", empno);
        int rows = empDao.deleteById(empno);
        log.info("删除员工【结果】:empno={}, 影响行数={}", empno, rows);
        return rows;
    }
    @Override
    public Employee addEmp(Employee employee) {
        if (employee.getStatus() == null || employee.getStatus().trim().isBlank()) {
            employee.setStatus("待入职");
        }
        employee.setDeptno(0);

        log.info("新增员工【入参】:empno={}, ename={}, status={}, job={}, deptno={}",
                employee.getEmpno(), employee.getEname(), employee.getStatus(),
                employee.getJob(), employee.getDeptno());

        empDao.insertEmp(employee);

        log.info("新增员工【完成】:empno={}, ename={}, status={}, job={}, deptno={}",
                employee.getEmpno(), employee.getEname(), employee.getStatus(),
                employee.getJob(), employee.getDeptno());
        return employee;
    }

    @Override
    public int updateEmp(Employee employee) {
        log.info("更新员工信息:empno={}, ename={}, status={}, job={}, mgr={}, hiredate={}, sal={}, comm={}, deptno={}",
                employee.getEmpno(), employee.getEname(), employee.getStatus(),
                employee.getJob(), employee.getMgr(), employee.getHiredate(),
                employee.getSal(), employee.getComm(), employee.getDeptno());

        int response = empDao.updateEmp(employee);

        log.info("更新员工【结果】:empno={}, 影响行数={}", employee.getEmpno(), response);
        return response;
    }
}
