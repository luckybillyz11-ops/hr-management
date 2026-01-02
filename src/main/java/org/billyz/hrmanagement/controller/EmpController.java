package org.billyz.hrmanagement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.billyz.hrmanagement.common.ApiResponse;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.service.EmpService;
import org.billyz.hrmanagement.vo.EmployeeByDeptVO;
import org.billyz.hrmanagement.vo.EmployeeDetailVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/employees")
@RestController
public class EmpController {

    @Resource
    private EmpService empService;

    @PostMapping
    public ApiResponse<Void> addEmp(@RequestBody Employee employee) {
        int result = empService.insertEmp(employee);
        if (result > 0) {
            return ApiResponse.success(null);
        } else {
            return ApiResponse.fail("添加失败");
        }
    }

    @GetMapping
    public ApiResponse<List<Employee>> queryByIdOrName
            (@RequestParam(required = false)Integer empno,
             @RequestParam(required = false)String ename) {
        return ApiResponse.success(empService.queryByIdOrName(empno, ename));
    }
    @PutMapping
    public ApiResponse<Void> updateEmp(@RequestBody Employee employee) {
        int result = empService.updateEmp(employee);
        if (result > 0) {
            return ApiResponse.success(null);
        } else {
            return ApiResponse.fail("更新失败，员工不存在");
        }
    }
//    @DeleteMapping
//    public ApiResponse<Void> deleteEmp(@RequestParam Integer empno) {
//        int result = empService.deleteEmp(empno);
//        if (result > 0) {
//            return ApiResponse.success(null);
//        } else {
//            return ApiResponse.fail("删除失败，员工不存在");
//        }
//    }
    
    /**
     * 分页查询员工详细信息
     * @param current 当前页码，默认1
     * @param size 每页大小，默认10
     * @return 员工详细信息分页数据
     */
    @GetMapping("/details")
    public ApiResponse<IPage<EmployeeDetailVO>> queryEmployeeDetailsPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(empService.queryEmployeeDetailsPage(current, size));
    }
    
    /**
     * 按部门查询员工信息
     * @param deptno 部门编号
     * @return 员工信息列表（包含：员工名称、职位、雇佣时间、联系方式、上级员工姓名）
     */
    @GetMapping("/by-dept")
    public ApiResponse<List<EmployeeByDeptVO>> queryEmployeesByDeptno(
            @RequestParam Integer deptno) {
        return ApiResponse.success(empService.queryEmployeesByDeptno(deptno));
    }
    @DeleteMapping
    public ApiResponse<Void> deleteEmployeesByIds(@RequestParam Integer empno) {
        int result = empService.deleteEmp(empno);
        if (result > 0) {
            return ApiResponse.success(null);
            } else {
            return ApiResponse.fail("删除失败，员工不存在");
            }
        }

}
