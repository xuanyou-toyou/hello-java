# hello-java

Java 后端学习工程 —— 从控制台程序逐步演进为数据库驱动的应用。

包含一个完整的**学生管理系统**：支持增删改查、数据持久化到 MySQL、分层设计（DAO）。

---

## 功能

- 添加 / 删除 / 修改 / 按学号查询 / 列出全部学生
- 三科成绩录入，总分与平均分实时计算
- 数据持久化到 MySQL，程序重启后数据不丢失
- 学号重复、记录不存在、数据库异常均有处理，程序不会崩溃

## 技术栈

| 分类 | 技术 |
|---|---|
| 语言 | Java 21 |
| 构建 | Maven 3.9.9 |
| 数据库 | MySQL 8.0 |
| 数据访问 | JDBC（`PreparedStatement` 参数化查询，防 SQL 注入）|
| 设计 | DAO 分层、try-with-resources 资源管理 |
| 工具 | IntelliJ IDEA、Git、DataGrip |

## 目录结构

```
src/main/java/demo/
├── day01/Scores.java              成绩统计器（基础语法练习）
├── day02/Student.java             学生实体类（封装、构造方法、方法）
├── day02/StudentDemo.java         实体类使用示例
├── day03/Student.java             学生实体（早期版本）
├── day03/StudentManager.java      学生管理系统主程序（控制台菜单）
├── day03/StudentStorage.java      CSV 存储（历史版本，已被数据库方案替代）
├── day07/DbUtil.java              JDBC 连接工具类
└── day07/StudentDao.java          学生数据访问对象（增删改查）
```

## 环境要求

| 项 | 版本 |
|---|---|
| JDK | 21+ |
| Maven | 3.9+ |
| MySQL | 8.0+ |

## 数据库准备

### 1. 建库建表

```sql
CREATE DATABASE IF NOT EXISTS student_db DEFAULT CHARSET utf8mb4;

USE student_db;

CREATE TABLE student (
    id      VARCHAR(20) PRIMARY KEY COMMENT '学号',
    name    VARCHAR(20) NOT NULL    COMMENT '姓名',
    chinese INT NOT NULL DEFAULT 0  COMMENT '语文',
    math    INT NOT NULL DEFAULT 0  COMMENT '数学',
    english INT NOT NULL DEFAULT 0  COMMENT '英语'
) COMMENT '学生表';
```

### 2. 配置数据库连接

复制模板文件，填入你自己的数据库密码：

```bash
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

编辑 `db.properties`：

```properties
db.url=jdbc:mysql://localhost:3306/student_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
db.user=root
db.password=你的密码
```

> `db.properties` 已在 `.gitignore` 中，不会被提交。请不要把真实密码写进 `.example` 文件。

## 运行

```bash
# 编译
mvn compile

# 复制依赖到 target/lib（首次运行或依赖变更后需要）
mvn dependency:copy-dependencies -DoutputDirectory=target/lib

# 启动学生管理系统
java -cp "target/classes;target/lib/*" demo.day03.StudentManager
```

> Linux / macOS 下 classpath 分隔符是 `:` 而不是 `;`。

### 验证数据库连接

```bash
java -cp "target/classes;target/lib/*" demo.day07.DbUtil
```

输出 `数据库版本: 8.0.43` 即表示连接正常。

## 使用

启动后按菜单提示操作：

```
1. 添加学生
2. 查看全部
3. 按学号查询
4. 删除学生
5. 修改学生
0. 退出
```

## 开发记录

| 阶段 | 内容 |
|---|---|
| W1–W2 | 环境搭建、Java 基础语法、成绩统计器 |
| W3 | 面向对象、学生实体设计、控制台 CRUD 系统 |
| W4 | 文件 IO 与异常处理，CSV 持久化 |
| W5–W6 | MySQL 建表与 SQL 查询（连接、分组、子查询）|
| W7 | JDBC 接入数据库，DAO 分层，PreparedStatement 防注入 |
