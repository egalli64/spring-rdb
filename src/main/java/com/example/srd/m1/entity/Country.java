/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m1.entity;

/**
 * Entity for Spring JDBC API
 */
public class Country {
    private String id;
    private String name;
    private Integer regionId;

    public Country() {
    }

    public Country(String id, String name, Integer regionId) {
        this.id = id;
        this.name = name;
        this.regionId = regionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "Country [id=" + id + ", name=" + name + ", regionId=" + regionId + "]";
    }
}
