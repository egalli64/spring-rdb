/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.ros;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MenuRepository {

    public List<Menu> findAll() {
        return List.of();
    }
}
