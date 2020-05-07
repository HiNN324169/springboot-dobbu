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
