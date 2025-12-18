package org.billyz.hrmanagement.service;

import org.billyz.hrmanagement.entity.Employee;

import java.util.List;

public interface EmpService {

    List<Employee> getAllEmps();

    Employee getEmpById(Integer empno);

    Employee addEmp(Employee employee);

    Employee getEmpByName(String ename);

    int deleteEmpById(Integer empno);

    int updateEmp(Employee employee);
}
