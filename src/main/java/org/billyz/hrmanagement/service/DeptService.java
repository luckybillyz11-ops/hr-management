package org.billyz.hrmanagement.service;

import org.billyz.hrmanagement.entity.Department;

import java.util.List;

public interface DeptService {

    List<Department> getAllDepts();

    Department getDeptById(Integer deptno);

    Department getDeptByName(String dname);

    int deleteDeptById(Integer deptno);

    Department addDept(Department dept);

}
