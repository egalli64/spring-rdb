/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m2.s4;

import org.springframework.data.repository.CrudRepository;

import com.example.srd.m2.entity.Region;

public interface CrudRegionRepository extends CrudRepository<Region, Long> {
}
