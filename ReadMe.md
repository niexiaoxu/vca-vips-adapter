# vca-vips-adapter 工程说明文档
[TOC]
## 简介
    当前工程是系统代理服务(目前是模拟服务)
## 1 工程模块
### 1.1 vos-adapter
#### 1.1.1 简介
```
 VOS代理服务后台
```
#### 1.1.2 工程说明
```
- 语言
    java
    
- 涉及框架
    spring-boot
    
- 依赖包
    spring-boot-starter-aop
    spring-boot-starter-jersey
    spring-boot-starter-validation
    spring-boot-starter-web
    spring-boot-devtools
    spring-boot-configuration-processor
    lombok
    spring-boot-starter-test
    spring-restdocs-mockmvc
    
- 接口文档
    
```
#### 1.1.2 工具类简介
#### 1.1.2.1 TestFiles CSV文本处理
##### 1.1.2.1.1 JVM配置
```
-Xms1024m
-Xmx1024m
-XX:ThreadStackSize=128m
-XX:MetaspaceSize=50m
-XX:MaxMetaspaceSize=2048m
-verbose:gc
-XX:+PrintGC
-XX:+PrintGCDetails
-XX:+PrintHeapAtGC
-XX:+PrintGCTimeStamps
-Xloggc:/test/logs/gc.log
-XX:-TraceClassResolution
```

