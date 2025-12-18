-- 删除现有表（如果存在）
DROP TABLE IF EXISTS bonus;
DROP TABLE IF EXISTS emp;
DROP TABLE IF EXISTS dept;
DROP TABLE IF EXISTS salgrade;

-- 创建部门表
CREATE TABLE dept (
                      deptno SERIAL PRIMARY KEY,
                      dname  VARCHAR(20),
                      loc    VARCHAR(20)
);

-- 插入部门数据
INSERT INTO dept (deptno, dname, loc) VALUES
                                          (0, '待分配', '未知'),
                                          (10, '财务部', '北京'),
                                          (20, '研发部', '深圳'),
                                          (30, '销售部', '上海'),
                                          (40, '运营部', '广州'),
                                          (50, '研究院', '杭州'),
                                          (60, '人事部', '成都');

-- 创建员工状态枚举类型
CREATE TYPE emp_status AS ENUM ('在职', '待入职', '暂停', '请假', '离职');

-- 创建员工表
CREATE TABLE emp (
                     empno    SERIAL PRIMARY KEY,
                     ename    VARCHAR(10),
                     status   emp_status DEFAULT '在职',
                     job      VARCHAR(20),
                     mgr      INTEGER DEFAULT 0,
                     hiredate TIMESTAMP,
                     sal      DECIMAL(7,2),
                     comm     DECIMAL(7,2),
                     deptno   INTEGER
);

-- 插入员工数据
INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno) VALUES
                                                                          (7369, '马云',     '职员',     7902, '1980-12-17',  800.00,  NULL,    20),
                                                                          (7499, '马化腾',   '销售员',   7698, '1981-02-20', 1600.00,  300.00,  30),
                                                                          (7521, '李彦宏',   '销售员',   7698, '1981-02-22', 1250.00,  500.00,  30),
                                                                          (7566, '雷军',     '经理',     7839, '1981-04-02', 2975.00,  NULL,    20),
                                                                          (7654, '丁磊',     '销售员',   7698, '1981-09-28', 1250.00, 1400.00,  30),
                                                                          (7698, '张一鸣',   '经理',     7839, '1981-05-01', 2850.00,  NULL,    30),
                                                                          (7782, '王兴',     '经理',     7839, '1981-06-09', 2450.00,  NULL,    10),
                                                                          (7788, '刘强东',   '分析师',   7566, '1987-04-19', 3000.00,  NULL,    20),
                                                                          (7839, '任正非',   '总裁',     NULL, '1981-11-17', 5000.00,  NULL,    10),
                                                                          (7844, '黄峥',     '销售员',   7698, '1981-09-08', 1500.00,    0.00,  30),
                                                                          (7876, '程维',     '职员',     7788, '1987-05-23', 1100.00,  NULL,    20),
                                                                          (7900, '沈南鹏',   '职员',     7698, '1981-12-03',  950.00,  NULL,    30),
                                                                          (7902, '周鸿祎',   '分析师',   7566, '1981-12-03', 3000.00,  NULL,    20),
                                                                          (7934, '张小龙',   '职员',     7782, '1982-01-23', 1300.00,  NULL,    10),

                                                                          -- 新增数据：覆盖多种情况
                                                                          (8001, '孙正义',   '实习生',    7839, '2023-01-10', 3000.00,  NULL,    50),
                                                                          (8002, '柳传志',   '工程师',    7566, '2022-05-15', 7500.00,  800.00,  99),
                                                                          (8003, '杨元庆',   '顾问',      7839, '2024-03-01', 9000.00,  NULL,    NULL),
                                                                          (8004, '董明珠',   '培训师',    9999, '2020-11-11', 5500.00,  NULL,    10),
                                                                          (8005, '宗庆后',   '职员',      0, '2021-07-20',  850.00,  NULL,    40);

-- 插入特殊状态员工数据
INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno, status) VALUES
    (8006, '李开复', '助理', 0, '2024-01-15', 4500.00, NULL, 0, '待入职');

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno, status) VALUES
    (8007, '俞敏洪', '咨询顾问', 7839, '2023-08-20', 6000.00, NULL, NULL, '在职');

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno, status) VALUES
    (8008, '章四', '实习生', 0, '2024-03-01', 2500.00, NULL, 20, '在职');

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno, status) VALUES
    (8009, '鲁五', '分析师', 7566, '2023-11-10', 3500.00, 200.00, 50, '在职');

INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno, status) VALUES
    (8010, '韦六', '职员', 7782, '2020-05-15', 1200.00, NULL, 10, '暂停');

-- 创建薪资等级表
CREATE TABLE salgrade (
                          grade SERIAL PRIMARY KEY,
                          losal DECIMAL(7,2),
                          hisal DECIMAL(7,2)
);

-- 插入薪资等级数据
INSERT INTO salgrade (grade, losal, hisal) VALUES
                                               (1,   700.00, 1200.00),
                                               (2, 1201.00, 1400.00),
                                               (3, 1401.00, 2000.00),
                                               (4, 2001.00, 3000.00),
                                               (5, 3001.00, 9999.00),
                                               (6,10000.00,20000.00);

-- 创建奖金表
CREATE TABLE bonus (
                       id          SERIAL PRIMARY KEY,
                       empno       INTEGER NOT NULL,
                       comm        DECIMAL(7,2),
                       bonus_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (empno) REFERENCES emp(empno) ON DELETE CASCADE
);

-- 插入奖金数据
INSERT INTO bonus (empno, comm, bonus_date) VALUES
                                                (7369, 200.00, '1981-01-01'),
                                                (7499, 500.00, '1981-03-01'),
                                                (7521, 300.00, '1981-03-15'),
                                                (7566, 800.00, '1981-05-01'),
                                                (7654, 1000.00, '1981-10-01'),
                                                (7698, 1200.00, '1981-06-01'),
                                                (7782, 700.00, '1981-07-01'),
                                                (7788, 1500.00, '1987-05-01'),
                                                (7839, 2000.00, '1982-01-01'),
                                                (7844, 400.00, '1981-10-01');


SELECT
    d.deptno AS 部门编号,
    d.dname AS 部门名称,
    COUNT(e.empno) AS 员工总数,
    SUM(e.sal) AS 部门总薪资,
    AVG(e.sal) AS 部门平均薪资,
    MAX(e.sal) AS 部门最高薪资,
    MIN(e.sal) AS 部门最低薪资,
    SUM(COALESCE(e.comm, 0)) AS 部门总佣金,
    COUNT(CASE WHEN e.status = '在职' THEN 1 END) AS 在职人数,
    COUNT(CASE WHEN e.job LIKE '%经理%' THEN 1 END) AS 经理人数,
    ROUND(AVG(EXTRACT(EPOCH FROM (CURRENT_DATE - e.hiredate))/365), 1) AS 平均司龄
FROM emp e
         LEFT JOIN dept d ON e.deptno = d.deptno
WHERE e.status = '在职'
GROUP BY d.deptno, d.dname
HAVING d.deptno IS NOT NULL
ORDER BY 部门总薪资 DESC;