/**
 * FileName: HelloWorldApplication
 * Author:   Administrator
 * Date:     2018 2018/7/29 17:40
 * Description: SpringBoot项目启动类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liudj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 〈SpringBoot项目启动类〉
 *  @SpringBootApplication 用来标注一个主程序类，说明这是一个SpringBoot应用
 */
@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[]args){
        // 启动Spring应用
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}
