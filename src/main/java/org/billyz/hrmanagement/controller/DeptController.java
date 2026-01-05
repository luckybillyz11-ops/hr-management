package org.billyz.hrmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.billyz.hrmanagement.annotation.LogRecord;
import org.billyz.hrmanagement.common.ApiResponse;
import org.billyz.hrmanagement.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/department")
@Tag(name = "部门管理")
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 删除部门
     * @param deptno 部门编号
     * @return 删除结果
     */
    @Operation(summary = "删除部门")
    @LogRecord("删除部门")
    @DeleteMapping("/{deptno}")
    public ApiResponse<String> deleteDepartment(@PathVariable Integer deptno) {
        String result = deptService.deleteDepartment(deptno);
        
        if (result.contains("成功")) {
            return ApiResponse.success(result);
        } else {
            return ApiResponse.fail(result);
        }
    }
}
