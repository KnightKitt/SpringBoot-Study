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

# 二、SpringBoot配置文件

## 1.1 配置文件

1）SpringBoot使用一个**全局的配置文件**，配置文件名是固定的；

​	•application.properties

​	•application.yml （或application.yaml）

2）配置文件的**作用**：修改SpringBoot自动配置的默认值；SpringBoot在底层都给我们自动配置好；

3）YAML（YAML *Ain't* Markup Language）了解：

​	YAML *A*  Markup Language：是一个标记语言

​	YAML *isn't* Markup Language：不是一个标记语言；

​	标记语言：

​		以前的配置文件；大多都使用的是 **xxxx.xml**文件；

​		YAML：**以数据为中心**，比json、xml等更适合做配置文件；

​		YAML：配置示例

```yaml
server:
  port: 8081
```

​	XML： 

```xml
<server>
	<port>8081</port>
</server>
```

## 1.2 YAML语法

### 1.2.1 基本语法

k:(空格)v：表示一对键值对（空格必须有）；

以**空格**的缩进来控制层级关系；缩进的空格数目不重要，只要是左对齐的一列数据，都是同一个层级的；

属性和值也是大小写敏感；

### 1.2.2 YAML 支持的三种数据结构

1. 字面量：普通的值（数字，字符串，布尔）

   k: v：字面量直接来写；字符串默认不用加上单引号或者双引号；

   ""：双引号 -> 不会转义字符串里面的特殊字符；特殊字符会作为本身想表示的意思；

   **如：**name: "zhangsan \n lisi"：输出；zhangsan 换行 lisi

   ''：单引号；会转义特殊字符，特殊字符最终只是一个普通的字符串数据

   **如：**name: ‘zhangsan \n lisi’：输出；zhangsan \n lisi

2. 对象、Map（属性和值）（键值对）

		k: v：在**下一行**来写对象的属性和值的关系；注意缩进

		对象还是k: v的方式。示例

```yaml
friends:
	lastName: zhangsan
	age: 20
```

​	行内写法： 

```yam
friends: {lastName: zhangsan,age: 18}
```

 3. 数组（Array、List、Set）

    用- 值表示数组中的一个元素。注意“-”后面有空格。 

```yaml
pets:
 - cat
 - dog
 - pig
```

​	行内写法:

```yaml
pets: [cat,dog,pig]
```

注意： Spring Boot使用 snakeyaml 解析yml文件； https://bitbucket.org/asomov/snakeyaml/wiki/Documentation#markdown-header-yamlsyntax 参考语法.

### 1.2.3 示例：配置文件值注入

* 配置文件

```yaml
person:
  last-name: zhangsan
  age: 20
  boss: true
  birth: 1988/10/01
  maps: {k1: v1, k2: v2}
  list:
    - book
    - glass
    - car
  dog:
    name: 小黑
    age: 1
    color: black
```

**注意：** 1)birth是Date类型的，注入时默认的日期格式yyyy/MM/dd，如果写成yyyy-MM-dd执行时会报错;

​	2) lastName属性可以配置为lastName或last-name，都可以被@ConfigurationProperties属性识别；但是在使用@Value注解时，必须和配置文件中配置的名称相同。参考下方1.4小结。

* JavaBean

```java
/**
 * 将配置文件中配置的每一个属性的值，映射到该组件中
 * @ConfigurationProperties: 作用告诉SpringBoot将本类中的所有属性和配置文件中的相关配置进行绑定。
 *  prefix = "person",配置文件中哪个下面的所有属性进行一一映射
 *
 *  注意：只有这个组件是容器中的组件（@Component），才能使用容器提供的@ConfigurationProperties功能对属性进行绑定；
 *  @ConfiguratioonProperties:默认从全局配置文件中获取值。
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String lasName;

    private Integer age;

    private Boolean boss;

    private Date birth;

    private Map<String, Object> maps;

    private List<Object> list;

    private Dog dog;
```

* 导入配置文件处理器(第一次在项目中添加**@ConfigurationProperties**注解时，IDEA会提示导入)

```xml
<!--导入配置文件处理器，配置文件进行绑定就会有提示（第一次添加需要运行一次应用才会有提示）-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

**注意：** 如果属性中有驼峰式的属性名，如lastName，配置文件会自动将其写为中划线格式，如last-name，它和lastName是等价的。

【IDEA快捷键】

​	Ctrl+? : 在xml中快速生成注释的尖括号<!---->

​	Alt + Insert: 打开代码生成窗口，选择生成构造函数、Getter/Setter、toString()方法等，类似Eclipse的[Shift + Alt + S]

* 测试

 ```java
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

	@Test
	public void contextLoads() {
		System.out.println("person=" + person);
	}
}
 ```

## 1.3 properties配置文件编码问题

```properties
#IDEA的properties配置文件默认使用UTF-8编码
#设置运行时将properties编码转换为ASCII编码，解决乱码问题
#配置person的值
person.last-name=张三
person.age=22
person.boss=false
person.birth=1995/10/09
person.maps.height=175cm
person.maps.weight=70kg
person.list=house,car,book
person.dog.name=大黄
person.dog.age=2
person.dog.color=yellow
```

properties配置文件在idea中默认utf-8可能会乱码，因为IDEA的properties配置文件默认采用UTF-8编码，而之前properties配置文件都使用ascii编码。因此作如下调整：

![1533139606972](.\images\003-IDEA-properties配置文件编码设置.png)

## 1.4 从配置文件中获取值的方法-@ConfigurationProperties与@Value区别

* @Value 注解

  其作用类似于xml配置时，<bean>中property节点的value属性的作用，支持 字面量/${key}从环境变量、配置文件中获取值/#{SpEL} 几种方式获取值。

  ```xml
  <bean class="Person">
      <property name="lastName" value="字面量/${key}从环境变量、配置文件中获取值/#{SpEL}">		       </property>
  <bean/>
  ```

|                      | @ConfigurationProperties                                     | @Value                                          |
| -------------------- | ------------------------------------------------------------ | ----------------------------------------------- |
| 功能                 | 批量注入配置文件中的属性                                     | 一个个指定，格式：@Value("${person.last-name}") |
| 松散语法（松散绑定） | 支持，如JavaBean中属性为lastName，配置文件中为last-name或lastName都可以被注入 | 不支持                                          |
| SpEL                 | 不支持，即不能将SpEL写在配置文件中                           | 支持，格式: #{SpEL}，如@Value("#{11*2}")        |
| JSR303数据校验       | 支持，在类上添加@Validated，在需要校验的属性上添加相关注解，如@Email等 | 不支持                                          |
| 复杂类型封装         | 支持                                                         | 不支持                                          |

配置文件yml还是properties他们都能获取到值；

如果只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value；

如果专门编写了一个javaBean来和配置文件进行映射，我们就直接使用@ConfigurationProperties；

* 示例：

```java
/**
 * 将配置文件中配置的每一个属性的值，映射到该组件中
 * @ConfigurationProperties: 作用告诉SpringBoot将本类中的所有属性和配置文件中的相关配置进行绑定。
 *  prefix = "person",配置文件中哪个下面的所有属性进行一一映射
 *
 *  注意：只有这个组件是容器中的组件（@Component），才能使用容器提供的@ConfigurationProperties功能对属性进行绑定；
 *      @ConfigurationProperties默认从全局配置文件中获取值。
 */
@Validated
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

//    @Value("${person.lastName}")
//    @Value("${person.last-name}")
    private String lastName;

//    @Value("#{10*2}")
    private Integer age;

    private Boolean boss;

    private Date birth;

    @Email // email必须符合Email地址格式
    private String email;
```

## 1.5 从配置文件中获取值-@PropertySource、@ImportResource、@Bean

### @**PropertySource**：

​	加载指定的配置文件(注意：只能用于properties文件)，即@ConfigurationProperties是默认从全局配置文件中获取属性值的，对于不需要配置在全局配置文件中的内容单独放在其它配置文件中，可以使用@PropertySource指定加载这些配置文件的内容。

* 示例：将person的配置信息移到person.properties配置文件中

```properties
person.last-name=李四
person.age=24
person.boss=false
person.birth=1996/10/09
person.email=lisi@qq.com
person.maps.height=175cm
person.maps.weight=70kg
person.list=house,car,book
person.dog.name=大黄
person.dog.age=2
person.dog.color=yellow
```

在JavaBean中通过@PropertySource获取指定配置文件中的属性值

```java
/**
 * 将配置文件中配置的每一个属性的值，映射到该组件中
 * @ConfigurationProperties: 作用告诉SpringBoot将本类中的所有属性和配置文件中的相关配置进行绑定。
 *  prefix = "person",配置文件中哪个下面的所有属性进行一一映射
 *
 *  注意：只有这个组件是容器中的组件（@Component），才能使用容器提供的@ConfigurationProperties功能对属性进行绑定；
 *      @ConfigurationProperties默认从全局配置文件中获取值。
 *
 *  @PropertySource注解支持从指定配置文件中获取配置信息，不支持 JSR303校验
 */
@PropertySource(value = {"classpath:person.properties"})
@Validated
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
//    @Value("${person.lastName}")
//    @Value("${person.last-name}")
    private String lastName;
//    @Value("#{10*2}")
    private Integer age;
    private Boolean boss;
    private Date birth;
//    @Email // email必须符合Email地址格式
    private String email;
    private Map<String, Object> maps;
    private List<Object> list;
    private Dog dog;
    private String[] array;
```

### @ImportResource

​	导入Spring的配置文件（即定义bean的 xml文件），让配置文件里面的内容生效。因为SpringBoot里面没有Spring的配置文件，我们自己添加的配置文件也不能被SpringBoot自动识别，要想让Spring的配置文件生效，加载到容器中，需要**将@ImportResource标注在一个配置类上**。

* 示例验证

在resouce中添加一个Spring配置文件beans.xml，并在其中定义一个bean

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="helloService" class="com.liudj.springboot.service.HelloService"/>
</beans>
```

通过单元测试类验证IOC容器中是否有helloService这个bean存在。如下，在测试类中直接注入IOC容器，运行testHelloService()方法验证该bean是否存在。从结果可以看到是不存在的。

```java
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
```

再在配置类上添加上@ImportResource注解（这里将其加到主配置类上），如下。之后再次运行测试单元测试，可以发现该bean已经存在，说明beans.xml配置文件已经生效了。

```java
@ImportResource(locations = {"classpath:beans.xml"}) // 数组，可以导致多个配置文件
@SpringBootApplication
public class Springboot02ConfigApplication {
	public static void main(String[] args) {
		SpringApplication.run(Springboot02ConfigApplication.class, args);
	}
}
```

### SpringBoot推荐给容器中添加组件的方式-推荐使用全注解的方式 

* 编写配置类，使用**@Configuration**指明类为配置类，等价于Spring配置文件 
* 使用**@Bean**给容器中添加组件 

示例：编写配置类，执行单元测试验证。注意：要将上一节中@ImportResource导入配置文件的方式注释掉。

```java
**
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
```

## 1.6 配置文件占位符

* 随机数  RandomValuePropertySource

```properties
${random.value}、${random.int}、${random.long}
${random.int(10)}、${random.int[1024,65536]}
```

* **占位符获取之前配置的值**，如果没有可以是用**:指定默认值**

```properties
person.last-name=张三${random.uuid}
person.age=${random.int}
person.birth=2017/12/15
person.boss=false
person.maps.k1=v1
person.maps.k2=14
person.list=a,b,c
person.dog.name=${person.hello:hello}_dog
person.dog.age=15
```

## 1.7 Profile 多环境支持

Profile是Spring**对不同环境提供不同配置功能的支持**，可以通过激活、 指定参数等方式快速切换环境。

### 1.7.1 多Profile文件

我们在主配置文件编写的时候，**文件名**可以是 application**-{profile}**.properties/yml，如：

application-dev.properties, applicaation-prod.properties等

**默认使用**application.properties的配置；

### 1.7.2 yml文件--支持多文档块方式

使用yml文件作为配置文件时有更方便的写法，即多文档块的方式。**以3个短横线（---）分隔文档块**。

（说明：测试前将properties文件配置内容先注释掉，避免影响）

```yaml
server:
  port: 8081
spring:
  profiles:
    active: dev #指定要激活的环境配置

---

server:
  port: 8083
spring:
  profiles: dev

---

server:
  port: 8084
spring:
  profiles: prod #指定文档块属于哪个环境

```

### 1.7.3 激活指定profile★

​	1、在**默认配置文件（即application.properties文件）中**指定 **spring.profiles.active**=dev

​	2、yml配置文件多文档块中指定激活的profile

​	3、命令行：

​	方式1）在IDE中启动时配置运行时的参数

![1533655573076](.\images\004-命令行方式激活profile之IDE中配置程序参数.png)	

​	方式2）项目打包后，以命令行方式运行时指定程序参数

​		java -jar springboot-02-config-0.0.1-SNAPSHOT.jar **--spring.profiles.active=dev**；

​		可以直接在测试的时候，配置传入命令行参数

​	3、虚拟机参数：-**Dspring.profiles.active**=dev

![1533656312633](.\images\005-配置虚拟机参数方式激活profile.png)

## 1.8 SpringBoot配置文件的加载位置

### 1.8.1 配置文件加载位置及优先级

SpringBoot 启动会**扫描以下位置**的application.properties或者application.yml文件作为SpringBoot的默认配置文件

–file:./config/		当前项目根目录下的config目录中

–file:./			当前项目根目录下

–classpath:/config/	类路径下的config目录中

–classpath:/			类路径根目录中（即创建项目时默认的resources目录中）

**优先级由高到底，高优先级的配置会覆盖低优先级的配置**；

**互补配置** ，即所有位置的文件都会被加载，高优先 级配置内容会覆盖低优先级配置内容。    

### 1.8.2 修改默认配置文件的加载位置

我们还可以通过**spring.config.location**来**改变默认的配置文件位置**

项目**打包好以后**，我们可以**使用命令行参数的形式**，启动项目的时候来指定配置文件的新位置；指定配置文件和默认加载的这些配置文件共同起作用形成**互补配置**；

java -jar springboot-02-config-02-0.0.1-SNAPSHOT.jar --spring.config.location=G:/application.properties