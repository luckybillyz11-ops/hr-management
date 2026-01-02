package org.billyz.hrmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.billyz.hrmanagement.entity.Employee;
import org.billyz.hrmanagement.vo.EmployeeByDeptVO;
import org.billyz.hrmanagement.vo.EmployeeDetailVO;

import java.util.List;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {

    /**
     * 分页查询员工详细信息
     * 包含：员工姓名、部门名称、总收入、薪资等级、联系方式
     * 使用 MyBatis Plus 的 Page 分页，SQL 定义在 EmployeeDao.xml 中
     */
    IPage<EmployeeDetailVO> queryEmployeeDetailsPage(Page<EmployeeDetailVO> page);

    /**
     * 按部门查询员工信息
     * 包含：员工名称、职位、雇佣时间、联系方式、上级员工姓名
     * SQL 定义在 EmployeeDao.xml 中
     * @param deptno 部门编号
     * @return 员工信息列表
     */
    List<EmployeeByDeptVO> queryEmployeesByDeptno(@Param("deptno") Integer deptno);

}