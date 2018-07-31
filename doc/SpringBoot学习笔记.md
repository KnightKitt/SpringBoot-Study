# 一、SpringBoot 入门

## 1.1 SpringBoot 简介

* 简化Spring应用开发的一个框架，约定大于配置；
* 整个Spring技术栈的一个大整合；
* J2EE开发的一站式解决方案； 

#### SpringBoot优点 

* 快速创建独立运行的Spring项目以及与主流框架集成;
* 使用嵌入式的Servlet容器，应用无需打成WAR包;
* starters自动依赖与版本控制;
* 大量的自动配置，简化开发，也可修改默认值 ;
* 无需配置XML，无代码生成，开箱即用;
* 准生产环境的运行时应用监控;
* 与云计算的天然集成    

#### SpringBoot缺点

​	入门容易精通难。SpringBoot是基于Spring框架的再封装，如果对Spring框架不够了解，那么对SpringBoot的封装机制可能也不会很了解。另外，SpringBoot底层实现原理中很多的自动配置都需要掌握Spring框架底层的API，需要我们对Spirng底层的API非常了解后才能对SpringBoot进行深度定制。

## 1.2 微服务了解

> 微服务是一种架构风格
>
> 一个应用应该是一组小型服务；可以通过HTTP的方式进行互通。
>
> 单体应用：ALL IN ONE ，传统的应用。

> >其优点是开发调试简单，不涉及多个应用的互连互调，部署也简单。 
> >
> >缺点：当应用作了简单的修改时，所有的应用都要重新部署运行。

> 微服务：每一个功能元素最终都是一个可独立替换和独立升级的软件单元； 
>
> [详细参照微服务文档](https://martinfowler.com/articles/microservices.html#MicroservicesAndSoa) 

## 1.3 开发环境

环境约束

–jdk1.8：Spring Boot 推荐jdk1.7及以上；java version "1.8.0_162"

–maven3.x：maven 3.3以上版本；Apache Maven 3.3.9

–IntelliJIDEA2017：IntelliJ IDEA 2017.3.4 x64、STS（Spring Tool Suite）

–SpringBoot 1.5.14.RELEASE：1.5.14；

#### Maven配置

1. 给maven 的settings.xml配置文件的profiles标签添加，使maven使用JDK1.8编译源码 

```xml
<profiles>
    <profile>
      <id>jdk-1.8</id>
      <activation>
        <activeByDefault>true</activeByDefault>
        <jdk>1.8</jdk>
      </activation>
      <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
      </properties>
    </profile>
</profiles>
```

2. IDEA中Maven配置

   配置全局默认的Maven设置：

   File-> Other Settings-> Default Settings... -> [Build, Execution, Deployment] -> Build Tools -> Maven

## 1.4 Spring Boot HelloWorld

- 基于Maven创建简单的SpringBoot HelloWorld项目

  功能：浏览器发送hello请求，服务器接受请求并处理，响应Hello World字符串； 

  步骤：

   1. 创建一个maven工程；（jar）

   2. 导入SpringBoot相关依赖

      ```xml
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>1.5.9.RELEASE</version>
      </parent>
      <dependencies>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
      </dependencies>
      ```

  3. 编写一个主程序，启动SpringBoot应用

     ```java
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
     ```

  4. 编写Controller

     ```java
     @Controller
     public class HelloController {
         @ResponseBody
         @RequestMapping("/hello")
         public String hello() {
             return "hello world!";
         }
     }
     ```

   5. 运行主程序测试

   6. 简化部署

      参考[SpringBoot官方文档-11.5 Creating an executable jar](https://docs.spring.io/spring-boot/docs/1.5.14.RELEASE/reference/htmlsingle/#getting-started-first-application-executable-jar)

      在pom.xml中配置SpringBoot的maven插件：

      ```xml
      <!-- 这个插件，可以将应用打包成一个可执行的jar包；-->
      <build>
          <plugins>
              <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
              </plugin>
          </plugins>
      </build>
      ```

      将这个应用打成jar包，直接使用java -jar的命令进行执行。

      ##### *【小技巧】* IDEA中Maven使用小技巧

      IDEA中执行Maven指定生命周期，只需打开Maven依赖管理窗口，然后在【LifeCycle】中选则对应的生命周期阶段执行即可。

      SpringBoot的Maven插件所打的jar包的结构如下图：

      ![1532868564707](.\images\0001-SpringBoot的Maven插件所打的jar包的结构-01.png)

其中BOOT-INF目录中包含了我们自己所写代码编译后的class文件，位于classes目录中；还包含了所有依赖的jar包，如嵌入式的Tomcat等，位于lib目录中。

## 1.5 Hello World探究

### 1.5.1 pom文件

	#### 父项目：

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.14.RELEASE</version>
</parent>

该父项目的父项目：
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>1.5.14.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
该父项目是真正来管理SpringBoot应用里面的所有依赖版本。
```

可以将其称为SpringBoot的版本仲裁中心。以后我们导入依赖默认是不需要写版本号的。（没有在dependencies中管理的依赖自然需要声明版本号。）

#### 导入的依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

spring-boot-starter-**web**

**spring-boot-starter:**  SpringBoot场景启动器。当我们要有web模块的时候，帮我们导入了Web模块正常运行所依赖的组件，并且依赖的版本都受父项目仲裁。

Spring Boot将所有的功能场景都抽取出来，做成一个个的starters（启动器），只需要在项目里面引入这些starter，相关场景的所有依赖都会导入进来。要用什么功能就导入什么场景的启动器。

参考[SpringBoot官方文档 13.5 - Starters](https://docs.spring.io/spring-boot/docs/1.5.14.RELEASE/reference/htmlsingle/#using-boot-starter)

### 1.5.2 主程序类、主入口类

```java
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
```

@**SpringBootApplication**: Spring Boot应用注解标注在某个类上说明这个类是SpringBoot的主配置类，SpringBoot就应该运行这个类的main方法来启动SpringBoot应用； 

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
```

@SpringBootApplication是一个组合注解。

**@SpringBootConfiguration** ：Spring Boot的配置类 ，标注在某个类上，表示这是一个Spring Boot的配置类； 

@**Configuration**: 配置类上来标注这个注解； 

配置类 <==> 配置文件；配置类也是容器中的一个组件；@Component 

@**EnableAutoConfiguration**：开启自动配置功能； 

​	以前我们需要配置的东西，Spring Boot帮我们自动配置；@**EnableAutoConfiguration**告诉SpringBoot开启自动配置功能；这样自动配置才能生效。自动配置的原理是什么？如下：@**EnableAutoConfiguration** 也是一个组合注解。

```java
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
```

 @**AutoConfigurationPackage**：自动配置包 ，其定义如下，

```java
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {}
```

**@Import** 注解是Spring的底层注解，其作用是给容器中导入一个组件；导入的组件由其后的 AutoConfigurationPackages.Registrar.class决定。

AutoConfigurationPackages.Registrar.class的**作用**是将主配置类（@SpringBootApplication标注的类）的所在包及下面所有子包里面的所有组件扫描到Spring容器； 

**@Import**(EnableAutoConfigurationImportSelector.class) 给容器中导入组件 

**EnableAutoConfigurationImportSelector**：导入哪些组件的选择器；

​	将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中；

​	会给容器中导入非常多的自动配置类（xxxAutoConfiguration）；这些自动配置类的作用就是给容器中导入对应场景需要的所有组件，并配置好这些组件；

![1533046918656](.\images\002-AutoConfigurationImportSelector为我们导入的自动配置类.png)

有了自动配置类，免去了我们手动编写配置注入功能组件等的工作 .

​	SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class,classLoader)；

​	Spring Boot在启动的时候**从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值，将这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作**；以前我们需要自己配置的东西，自动配置类都帮我们；

J2EE的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.5.14.RELEASE.jar；

## 1.6 使用Spring Initializer快速创建Spring Boot项目

IDE都支持使用Spring的项目创建向导快速创建一个Spring Boot项目。

选择我们需要的模块，向导会联网创建Spring Boot项目； 

默认生成的Spring Boot项目； 

> - 主程序已经生成好了，我们只需要我们自己的逻辑
> - resources文件夹中目录结构 
>   - static：保存所有的静态资源； js css images；
>   - templates：保存所有的模板页面；（Spring Boot默认jar包使用嵌入式的Tomcat，默认不支持JSP页面）；可以使用模板引擎（freemarker、thymeleaf）；
>   - application.properties：Spring Boot应用的配置文件；可以修改一些默认设置；