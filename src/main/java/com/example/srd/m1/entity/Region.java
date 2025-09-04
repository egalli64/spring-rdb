/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m1.entity;

/**
 * Entity for Spring JDBC API
 */
public class Region {
    private Long id;
    private String name;

    public Region() {
    }

    public Region(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Region(String name) {
        this(null, name);
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

    @Override
    public String toString() {
        return "Region [id=" + id + ", name=" + name + "]";
    }
}
