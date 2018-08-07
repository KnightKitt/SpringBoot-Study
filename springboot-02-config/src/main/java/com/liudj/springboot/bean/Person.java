/**
 * FileName: Person
 * Author:   Administrator
 * Date:     2018 2018/8/1 0:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.liudj.springboot.bean;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将配置文件中配置的每一个属性的值，映射到该组件中
 * @ConfigurationProperties: 作用告诉SpringBoot将本类中的所有属性和配置文件中的相关配置进行绑定。
 *  prefix = "person",配置文件中哪个下面的所有属性进行一一映射
 *
 *  注意：只有这个组件是容器中的组件（@Component），才能使用容器提供的@ConfigurationProperties功能对属性进行绑定；
 *      @ConfigurationProperties默认从全局配置文件中获取值。
 *
 *  @PropertySource注解支持从指定配置文件中获取配置信息，不支持校验
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(Boolean boss) {
        this.boss = boss;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("lastName='").append(lastName).append('\'');
        sb.append(", age=").append(age);
        sb.append(", boss=").append(boss);
        sb.append(", birth=").append(birth);
        sb.append(", email='").append(email).append('\'');
        sb.append(", maps=").append(maps);
        sb.append(", list=").append(list);
        sb.append(", dog=").append(dog);
        sb.append(", array=").append(array == null ? "null" : Arrays.asList(array).toString());
        sb.append('}');
        return sb.toString();
    }
}
