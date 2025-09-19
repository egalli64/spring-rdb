/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.ros;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

    public List<Category> findAll() {
        return List.of();
    }
}
