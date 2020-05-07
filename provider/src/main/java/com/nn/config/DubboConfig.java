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
     *  配置UserServiceImpl暴露服务
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
