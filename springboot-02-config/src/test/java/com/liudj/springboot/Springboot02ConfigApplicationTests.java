package com.liudj.springboot;

import com.liudj.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试
 * @SpringBootTest注解：表明这是一个SpringBoot的单元测试
 * @RunWith：指定单元测试使用Spring的驱动执行，而不是原来的Junit
 *
 * 可以在测试期间很方便的像编码一样进行自动注入到容器的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02ConfigApplicationTests {

	@Autowired
	Person person;

	@Autowired // 直接在测试类中注入application容器（IOC容器）
	ApplicationContext ioc;

	@Test
	public void testHelloService() {
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }

	@Test
	public void contextLoads() {
		System.out.println("person=" + person);
	}
}
