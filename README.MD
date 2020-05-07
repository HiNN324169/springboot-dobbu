## springboot dubbo zookeeper 依赖配置方式

### 1、使用aibaba 依赖
```text
        <!--    配置方式一：阿里巴巴 依赖配置-->
        <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>0.2.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty-all</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.6.7</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>2.12.0</version>
        </dependency>
        <!--        配置方式一：阿里巴巴 依赖配置-->
``` 

### 2、使用 apache 依赖

```text
<!--        apache dubbo-->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.7.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-dependencies-zookeeper</artifactId>
            <version>2.7.3</version>
            <type>pom</type>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--        apache dubbo-->
```


###3、dubbo配置
#### 3.1 provider

##### 3.1.1  需要暴露的服务service
- 使用alibaba 或 Apache 提供的@service 注解，根据你引入的依赖而定。（使用了这两个@service注册，就不需要使用 spring原生@service注解）

```$xslt
@org.apache.dubbo.config.annotation.Service
或
@com.alibaba.dubbo.config.annotation.Service
```
##### 3.1.2 入口类添加注解
- springboot 入口类 加入 @EnableDubbo 注解：使用alibaba 或者 Apache提供的
```$xslt
@org.apache.dubbo.config.spring.context.annotation.EnableDubbo
或者
@com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo
```

##### 3.1.3 .yml配置
```$xslt
server:
  port: 8080
dubbo:
  protocol:
    name: dubbo
    port: 20880
  registry:
    address: zookeeper://127.0.0.1:2181 # zookeeper 连接地址
  application:
    name: provider
```

#### 3.2 consumer
##### 3.2.1 注入provider 提供的服务
- 使用 @Reference 注入。使用alibaba 、Apache 提供的其中一个
```$xslt
    org.apache.dubbo.config.annotation.@Reference
    private UserService userService;
或
    @com.alibaba.dubbo.config.annotation.@Reference
    private UserService userService;
```

##### 3.2.2 入口类添加注解
- springboot 入口类 加入 @EnableDubbo 注解：使用alibaba 或者 Apache提供的
```$xslt
@org.apache.dubbo.config.spring.context.annotation.EnableDubbo
或者
@com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo
```

##### 3.2.3 .yml 配置
```$xslt
server:
  port: 9090
dubbo:
  registry:
    address: zookeeper://127.0.0.1:2181 # zookeeper 连接地址
  application:
    name: consumer
``