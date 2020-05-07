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


### 3、dubbo配置
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
- consumer 中的 service 中 spring 提供的@service 注解
- 在service中使用 @Reference 引用服务。使用alibaba 、Apache 提供的其中一个
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
```

### 4、使用配置类 进行 dubbo配置
- 在 上面的基础上进行修改
#### 4.1 provider 服务提供者
- 创建一个 dubbo 配置类：Dubboconfig
- 公共接口实现类使用 spring 原生@service注解
- 删除 yml 里面关于 dubbo的所有配置
- 入口类的 @EnableDubbo 注解 要加上哟
- 启动服务即可...
```java
package com.nn.config;

import com.nn.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DubboConfig
 * @Author nn
 * @Date 2020/5/7 11:30
 */

@Configuration
public class DubboConfig {

    /**
     *  配置应用名
     */

    @Bean
    public ApplicationConfig applicationConfig(){
        return new ApplicationConfig("provider");
    }

    /**
     *  配置 注册中心地址
     */
    @Bean
    public RegistryConfig registryConfig(){
        return new RegistryConfig("zookeeper://127.0.0.1:2181");
    }

    /**
     *  配置节点名称和服务端口
     */
    @Bean
    public ProtocolConfig protocolConfig(){
        return new ProtocolConfig("dubbo", 20880);
    }


    /**
     *  配置暴露服务
     */
    @Bean
    public ServiceConfig<UserService> userServiceServiceConfig(UserService userService,ApplicationConfig applicationConfig,RegistryConfig registryConfig,ProtocolConfig protocolConfig){
        ServiceConfig<UserService> serviceConfig = new ServiceConfig<>();
        // 设置你要暴露的接口
        serviceConfig.setInterface(UserService.class);
        // 将暴露的接口指向userService引用
        serviceConfig.setRef(userService);

        // 注入applicationName
        serviceConfig.setApplication(applicationConfig);
        // 注入节点名称和服务端口
        serviceConfig.setProtocol(protocolConfig);
        //注入 注册中心
        serviceConfig.setRegistry(registryConfig);

        serviceConfig.export();
        return serviceConfig;
    }
}
```

#### 4.2 consumer 服务消费者
- 在 上面的基础上进行修改
- 使用 set 方式注入 服务
```text
private UserService userService;
public void setUserService(UserService userService) {
    this.userService = userService;
}
```
- 创建一个 dubbo 配置类：Dubboconfig
- 删除 yml 里面关于 dubbo的所有配置
- 入口类的 @EnableDubbo 注解 要加上哟
- 启动服务即可...
```java
package com.nn.config;

import com.nn.service.ConsumerServiceImpl;
import com.nn.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DubboConfig
 * @Author nn
 * @Date 2020/5/7 12:04
 */

@Configuration
public class DubboConfig {

    /**
     *  配置应用名
     */
    @Bean
    public ApplicationConfig applicationConfig(){
        return new ApplicationConfig("consumer");
    }

    /**
     *  配置 注册中心地址
     */
    @Bean
    public RegistryConfig registryConfig(){
        return new RegistryConfig("zookeeper://127.0.0.1:2181");
    }


    /**
     *   引用UserServiceImpl服务
     */
    @Bean
    public ReferenceConfig<UserService> userServiceReference(ConsumerServiceImpl consumerServiceImpl,ApplicationConfig applicationConfig,RegistryConfig registryConfig){

        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setId("userService");
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);

        // 给 consumerServiceImpl 注入数据
        consumerServiceImpl.setUserService(referenceConfig.get());
        return referenceConfig;
    }
}

```
