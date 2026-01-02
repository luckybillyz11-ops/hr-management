package org.billyz.hrmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.billyz.hrmanagement.common.ApiResponse;
import org.billyz.hrmanagement.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class EmpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 测试查询员工列表 - GET /employees
     */
    @Test
    public void testQueryEmployees() throws Exception {
        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    /**
     * 测试按员工编号查询 - GET /employees?empno=7839
     */
    @Test
    public void testQueryEmployeeById() throws Exception {
        mockMvc.perform(get("/employees")
                        .param("empno", "7839")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].empno").value(7839));
    }

    /**
     * 测试按员工姓名查询 - GET /employees?ename=张
     */
    @Test
    public void testQueryEmployeeByName() throws Exception {
        mockMvc.perform(get("/employees")
                        .param("ename", "张")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    /**
     * 测试更新员工信息 - PUT /employees
     */
    @Test
    public void testUpdateEmployee() throws Exception {
        // 先查询一个员工
        String response = mockMvc.perform(get("/employees")
                        .param("empno", "7839")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApiResponse<List<Employee>> apiResponse = objectMapper.readValue(
                response,
                objectMapper.getTypeFactory().constructParametricType(
                        ApiResponse.class,
                        objectMapper.getTypeFactory().constructCollectionType(
                                java.util.List.class, Employee.class)));

        if (apiResponse.getData() != null && !apiResponse.getData().isEmpty()) {
            Employee employee = apiResponse.getData().get(0);
            String originalName = employee.getEname();
            employee.setEname(originalName + "_测试更新");

            String requestJson = objectMapper.writeValueAsString(employee);

            mockMvc.perform(put("/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true));
        }
    }

    /**
     * 测试更新不存在的员工 - PUT /employees
     */
    @Test
    public void testUpdateNonExistentEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmpno(99999);
        employee.setEname("不存在的员工");

        String requestJson = objectMapper.writeValueAsString(employee);

        mockMvc.perform(put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("更新失败，员工不存在"));
    }

    /**
     * 测试分页查询员工详细信息 - GET /employees/details
     */
    @Test
    public void testQueryEmployeeDetailsPage() throws Exception {
        mockMvc.perform(get("/employees/details")
                        .param("current", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.current").value(1))
                .andExpect(jsonPath("$.data.size").value(10))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    /**
     * 测试按部门查询员工信息
     */
    @Test
    public void testQueryEmployeesByDeptno() throws Exception {
        mockMvc.perform(get("/employees/by-dept")
                        .param("deptno", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].empno").exists())
                .andExpect(jsonPath("$.data[0].ename").exists())
                .andExpect(jsonPath("$.data[0].job").exists());
    }

    /**
     * 测试按部门查询 - 不存在的部门应返回空数组
     */
    @Test
    public void testQueryEmployeesByNonExistentDeptno() throws Exception {
        mockMvc.perform(get("/employees/by-dept")
                        .param("deptno", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}

