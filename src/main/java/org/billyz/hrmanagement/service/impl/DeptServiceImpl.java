package org.billyz.hrmanagement.service.impl;

import jakarta.annotation.Resource;
import org.billyz.hrmanagement.dao.DepartmentDao;
import org.billyz.hrmanagement.entity.Department;
import org.billyz.hrmanagement.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DepartmentDao departmentDao;

    @Override
    public Department getDeptById(Integer deptno) {
        return departmentDao.selectById(deptno);
    }
    @Override
    public List<Department> getAllDepts() {
        return departmentDao.selectList(null);
    }
    @Override
    public Department addDept(Department dept) {
        departmentDao.insert(dept);
        return dept;
    }
    @Override
    public Department getDeptByName(String dname) {
        return departmentDao.selectDeptByName(dname);
    }
    @Override
    public int deleteDeptById(Integer deptno){
        return departmentDao.deleteById(deptno);
    }
}
