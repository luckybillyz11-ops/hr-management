package org.billyz.hrmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.billyz.hrmanagement.entity.Department;
@Mapper
public interface DepartmentDao extends BaseMapper<Department>
{
    @Select("SELECT * FROM dept WHERE dname = #{dname}")
    Department selectDeptByName(String dname);
}
