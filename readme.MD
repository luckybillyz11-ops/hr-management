## 人事管理系统（HR Management）

### 版本 v1.0（2025-12-17）

1. **实现新增员工功能**
   - 员工状态字段 `status` 在数据库中为 **PostgreSQL 枚举类型 `emp_status`**，不是 `varchar`。
   - 通过 **手写 SQL + `CAST` 强转**，解决 Java `String` 写入枚举字段的问题：  
     `CAST(#{status} AS emp_status)`。

2. **实现按员工编号查询**
   - 接口：根据 `empno` 查询单个员工信息。

---

### 版本 v2.0（2025-12-18）

1. **新增员工默认状态与部门策略**
   - 新增员工时，如果未传 `status`，默认设置为 **“待入职”/“待分配”**。
   - 新增时不直接分配真实业务部门，`deptno` 初始设置为 `0`，表示“待分配部门”。

2. **实现完整的员工增删改查**
   - **查询**:
     - 按 `empno` 查询单个员工。
     - 按 `ename` 查询员工。
     - 查询所有员工列表。
   - **新增**：支持插入包含中文状态和职位信息的员工记录。
   - **删除**：根据 `empno` 删除员工，返回影响行数，并通过业务日志记录操作结果。
   - **更新**:
     - 提供更新员工基本信息接口（姓名、状态、职位、上级、入职日期、薪资、佣金、部门等）。
     - 使用 `COALESCE` 实现 **只更新非 `null` 字段**，未传字段保持数据库原值，避免被更新为 `null`：  
       `字段 = COALESCE(#{param}, 字段)`。

3. **实现员工换部门功能**
   - 提供按 `empno` 更新 `deptno` 的接口，实现员工部门调整。
   - 使用显式 `UPDATE emp SET deptno = ... WHERE empno = ...` 语句，并记录操作日志。

4. **引入业务日志记录**
   - 在 `EmpServiceImpl` 中为 **新增 / 删除 / 更新** 员工操作增加业务日志：
     - 记录 **入参详情**（empno、ename、status、job、deptno 等）。
     - 记录 **结果信息**（影响行数、生成的主键等）。
   - 日志格式：`时间 + 日志级别 + 类名 + 中文动作说明 + 关键字段`，便于定位每一次数据变更。

---

### 版本 v2.1（2025-12-18）

1. **统一返回结构（ApiResponse）**
   - 创建通用返回类 `ApiResponse<R>`，所有接口统一返回格式：
     ```json
     {
       "success": true/false,
       "message": "提示信息",
       "data": 实际数据
     }
     ```
   - 使用 **泛型 `<R>`** 实现类型安全，避免强制类型转换：
     - `ApiResponse<Employee>`：返回单个员工对象
     - `ApiResponse<Void>`：删除/更新操作，无数据返回
     - `ApiResponse<List<Employee>>`：返回员工列表
   - 提供静态工厂方法：
     - `ApiResponse.success(data)`：成功响应
     - `ApiResponse.fail(message)`：失败响应
---