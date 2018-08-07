/**
 * FileName: MyAppConfig
 * Author:   Administrator
 * Date:     2018 2018/8/2 23:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liudj.springboot.config;

import com.liudj.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 指明当前类是一个配置类，用来替代之前的Spring配置文件
 *
 * 在配置文件中使用<bean></bean>标签添加组件；在配置类中使用@Bean注解
 */
@Configuration
public class MyAppConfig {

    // 将方法的返回值添加到容器中，容器中这个组件的默认id就是方法名。
    @Bean
    public HelloService helloService(){
        System.out.println("配置类@Bean给容器中添加组件了。。。");
        return  new HelloService();
    }
}
