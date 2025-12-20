package org.billyz.hrmanagement.controller;


import jakarta.annotation.Resource;
import org.billyz.hrmanagement.common.ApiResponse;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.service.EmpService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmyController {

    @Resource
    private EmpService empService;

    @GetMapping("/getEmpById/{empno}")
    public ApiResponse<Employee> getEmployee(@PathVariable Integer empno) {
        Employee emp = empService.getEmpById(empno);
        if (emp == null) {
            return ApiResponse.fail("员工不存在");

        }
        return ApiResponse.success(emp);
    }
    @GetMapping("/selectAllEmp")
    public ApiResponse<List<Employee>> selectAll() {
        List<Employee> emp = empService.getAllEmps();
        if (emp == null) {
            return ApiResponse.fail("查找失败");
        }
        return ApiResponse.success(emp);
    }
    @GetMapping("/getEmpByName")
    public ApiResponse<Employee> getEmpByName(@RequestParam String ename) {
        Employee emp = empService.getEmpByName(ename);
        if (emp == null) {
            return ApiResponse.fail("员工不存在");

        }
        return ApiResponse.success(emp);
    }

    @PostMapping("/addEmp")
    public Employee addEmp(@RequestBody Employee employee) {
        return empService.addEmp(employee);
    }
    @DeleteMapping("/deleteEmpById/{empno}")
    public ApiResponse<Void> deleteEmpById(@PathVariable Integer empno) {
        int result = empService.deleteEmpById(empno);
        if (result > 0) {
            return ApiResponse.success(null);  // 删除成功，没有数据返回
        } else {
            return ApiResponse.fail("删除失败，员工不存在");
        }
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
    public ApiResponse<Void> updateEmpBasic(@RequestBody Employee employee) {
        int result = empService.updateEmp(employee);
        if (result > 0) {
            return ApiResponse.success(null);
        } else {
            return ApiResponse.fail("更新失败，员工不存在");
        }
    }
    
}
