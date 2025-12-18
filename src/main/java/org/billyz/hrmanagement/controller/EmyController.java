package org.billyz.hrmanagement.controller;


import jakarta.annotation.Resource;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.service.EmpService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmyController {

    @Resource
    private EmpService empService;

    @GetMapping("/getEmpById/{empno}")
    public Employee getEmployee(@PathVariable Integer empno) {
        return empService.getEmpById(empno);
    }
    @GetMapping("/selectAllEmp")
    public List<Employee> selectAll() {
        return empService.getAllEmps();
    }
    @GetMapping("/getEmpByName")
    public Employee getEmpByName(@RequestParam String ename) {
        return empService.getEmpByName(ename);
    }

    @PostMapping("/addEmp")
    public Employee addEmp(@RequestBody Employee employee) {
        return empService.addEmp(employee);
    }
    @DeleteMapping("/deleteEmpById/{empno}")
    public Map<String, Object> deleteEmpById(@PathVariable Integer empno) {
        int result = empService.deleteEmpById(empno);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "删除成功");
            response.put("deletedCount", result);
        } else {
            response.put("success", false);
            response.put("message", "删除失败，员工不存在");
            response.put("deletedCount", 0);
        }
        return response;
    }
//    @PutMapping("/updateEmpDept/{empno}/{deptno}")
//    public Map<String, Object> updateEmpDept(@PathVariable Integer empno, @PathVariable Integer deptno) {
//        int result = empService.updateEmp(empno, deptno);
//        Map<String, Object> response = new HashMap<>();
//        if (result > 0) {
//            response.put("success", true);
//            response.put("message", "更新成功");
//            response.put("updatedCount", result);
//        } else {
//            response.put("success", false);
//            response.put("message", "更新失败，员工不存在");
//            response.put("updatedCount", 0);
//        }
//        return response;
//    }

    @PutMapping("/updateEmp")
    public Map<String, Object> updateEmpBasic(@RequestBody Employee employee) {
        int result = empService.updateEmp(employee);
        Map<String, Object> response = new HashMap<>();
        if (result > 0) {
            response.put("success", true);
            response.put("message", "更新成功");
            response.put("updatedCount", result);
        } else {
            response.put("success", false);
            response.put("message", "更新失败，员工不存在");
            response.put("updatedCount", 0);
        }
        return response;
    }
    
}
