# hello-java

Java 学习工程：JDK 21 + Maven 起步项目。

## 环境

| 项 | 版本 |
|---|---|
| JDK | 21.0.4 (LTS) |
| Maven | 3.9.9 |
| IDE | IntelliJ IDEA 2024.2.1 |

## 运行

```bash
mvn compile
java -cp target/classes demo.App
```

输出：

```
Hello Maven + JDK 21
```

## 结构

```
src/main/java/demo/App.java   入口
pom.xml                       Maven 配置（阿里云镜像）
```
