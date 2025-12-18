package org.billyz.hrmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.billyz.hrmanagement.entity.Employee;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
    //cast强转status
    @Insert("""
            INSERT INTO emp (ename, status, job, mgr, hiredate, sal, comm, deptno)
            VALUES (#{ename}, CAST(#{status} AS emp_status), #{job}, #{mgr}, #{hiredate}, #{sal}, #{comm}, #{deptno})
            """)
    int insertEmp(Employee employee);

    @Select("SELECT * FROM emp WHERE ename = #{ename}")
    Employee selectEmpByName(String empname);

    @Update("""
            UPDATE emp
            SET ename    = COALESCE(#{ename}, ename),
                status   = COALESCE(CAST(#{status} AS emp_status), status),
                job      = COALESCE(#{job}, job),
                mgr      = COALESCE(#{mgr}, mgr),
                hiredate = COALESCE(#{hiredate}, hiredate),
                sal      = COALESCE(#{sal}, sal),
                comm     = COALESCE(#{comm}, comm),
                deptno   = COALESCE(#{deptno}, deptno)
            WHERE empno = #{empno}
            """)
    int updateEmp(Employee employee);
}
