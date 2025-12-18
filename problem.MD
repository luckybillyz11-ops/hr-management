# HR管理系统开发问题总结

## 一、Spring Boot 和 MyBatis-Plus 版本兼容问题

### 问题
- Spring Boot 3.5.7 与 MyBatis-Plus 3.5.7 存在依赖冲突
- `mybatis-spring` 版本不兼容

### 解决方案
在 `pom.xml` 中：
1. 排除 MyBatis-Plus 自带的 `mybatis-spring`
2. 手动引入兼容版本 `mybatis-spring 3.0.3`

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.7</version>
    <exclusions>
        <exclusion>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>3.0.3</version>
</dependency>
```

---

## 二、PostgreSQL 枚举类型问题

### 问题
- 数据库字段 `status` 类型为 `emp_status`(PostgreSQL 枚举)
- Java 实体类使用 `String`
- 插入时报错：**字段类型为 `emp_status`，但表达式类型为 `character varying`**

### 解决方案
在 SQL 中使用 `CAST` 强制类型转换：

```java
@Insert("""
    INSERT INTO emp (ename, status, job, mgr, hiredate, sal, comm, deptno)
    VALUES (#{ename}, CAST(#{status} AS emp_status), #{job}, #{mgr}, #{hiredate}, #{sal}, #{comm}, #{deptno})
    """)
int insertEmp(Employee employee);
```

---

## 三、更新员工基本信息导致其他字段变成 `null` 问题

### 问题
- 一开始使用“全量覆盖”的 UPDATE 语句，为:
  ```java
  @Update("""
          UPDATE emp
          SET ename    = #{ename},
              status   = CAST(#{status} AS emp_status),
              job      = #{job},
              mgr      = #{mgr},
              hiredate = #{hiredate},
              sal      = #{sal},
              comm     = #{comm},
              deptno   = #{deptno}
          WHERE empno = #{empno}
          """)
  int updateEmp(Employee employee);
  ```
- 当调用 `PUT /updateEmpBasic` 时，如果只传了部分字段（例如只传 `empno` 和 `ename`），其它字段在 `Employee` 对象中是 `null`，会被直接更新成 `null`，导致数据库原有数据丢失。

### 解决方案
- 使用 PostgreSQL 的 `COALESCE` 函数，只更新非 `null` 字段，未传的字段保持原值不变：
  ```java
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
  ```
