package com.lagou.edu.pojo;

import javax.persistence.*;

/**
 * 简历实体类（在类中药使用注解建立实体类和数据表之间的映射关系以及属性和字段之间的映射关系）
 * 1.建立实体类和数据表的映射关系
 * @Entity
 * @Table
 * 2.实体类属性和表字段的映射关系
 * @Id 标识主键
 * @GeneratedValue 标识主键生成的策略
 * @Column 简历属性和字段的映射
 *
 */

@Entity
@Table(name="tb_resume")
public class Resume {
    @Id
    /**
     * 生成策略经常使用的两种：
     * GenerationType.IDENTITY:依赖数据库中主键自增功能 Mysql
     * GenerationType.SEQUENCE：依靠序列来产生主键 Oracle
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="phone")
    private String phone;

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
