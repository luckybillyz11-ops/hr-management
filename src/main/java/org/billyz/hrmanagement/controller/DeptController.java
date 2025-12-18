package org.billyz.hrmanagement.controller;

import jakarta.annotation.Resource;
import org.billyz.hrmanagement.dao.DepartmentDao;
import org.billyz.hrmanagement.entity.Department;
import org.billyz.hrmanagement.service.DeptService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class DeptController
{
    @Resource
    private DeptService deptService;

    @GetMapping("/getDeptById/{deptno}")
    public Department getDept(@PathVariable Integer deptno) {
        return deptService.getDeptById(deptno);
    }
//    @GetMapping("/getDeptByName/{dname}")
//    public Department getDeptByName(@PathVariable String dname) {
//        return deptService.getDeptByName(dname);
//    }
    @GetMapping("selectAllDept")
    public List<Department> selectAllDept() {
        return deptService.getAllDepts();
    }
    @PostMapping("/addDept")
    public Department addDept(@RequestBody Department dept) {
        return deptService.addDept(dept);
    }
    @DeleteMapping("/deleteDeptById/{deptno}")
    public Map<String, Object> deleteDeptById(@PathVariable Integer deptno) {
        int result = deptService.deleteDeptById(deptno);
        Map<String,Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "删除成功");
            response.put("deletedCount", result);
        } else {
            response.put("success", false);
            response.put("message", "删除失败，部门不存在");
            response.put("deletedCount", 0);
        }
        return response;
    }
    @GetMapping("/getDeptByName")
    public Department getDeptByNameByParam(@RequestParam String dname) {
        return deptService.getDeptByName(dname);
    }
}
