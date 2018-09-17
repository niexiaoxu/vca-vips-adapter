# vca-vips-adapter 工程说明文档
# JVM配置项参考
```
-Xms2g
-Xmx2g
-Xmn1g //设置年轻代大小
-XX:MetaspaceSize=128m //持久代的初始大小
-XX:MaxMetaspaceSize=320m //持久代的上限
-XX:+UseConcMarkSweepGC //指定老年代垃圾回收的方式为CMS,CMS收集器是一种以获取最短回收停顿时间为目标的收集器。
-XX:+UseCMSCompactAtFullCollection //开关参数，用于在Full GC之后增加一个碎片整理过程
-XX:CMSInitiatingOccupancyFraction=70 //CMS收集器的启动阈值,70表示当老年代使用了70%空间后就会被激活
-XX:+CMSParallelRemarkEnabled //开启并行remark,减少第二次暂停的时间
-XX:SoftRefLRUPolicyMSPerMB=0 //每1M空闲空间可保持的SoftReference对象生存的时长（单位毫秒）
-XX:+CMSClassUnloadingEnabled //相对于并行收集器，CMS收集器默认不会对永久代进行垃圾回收。如果希望对永久代进行垃圾回收，可设置
-XX:SurvivorRatio=8 //Eden区和Survivor区占比配置，8表示两个Survivor区各占1
-XX:-UseParNewGC //设置年轻代为多线程收集
-verbose:gc //表示输出虚拟机中GC的详细情况
-Xloggc:"C:\Users\Administrator.GSPPK577ZXLETGW\rmq_srv_gc.log" //GC日志文件的输出路径
-XX:+PrintGCDetails //打印GC详细信息
-XX:-OmitStackTraceInFastThrow //是否省略堆栈异常
-XX:-UseLargePages //启用大内存分页
-Djava.ext.dirs=F:\rocketmq\rocketmq-all-4.2.0-bin-release\lib //指定依赖的jar的目录
-cp ".;F:\rocketmq\rocketmq-all-4.2.0-bin-release\conf;" //-cp 和 -classpath 一样，是指定类运行所依赖其他类的路径
```
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

