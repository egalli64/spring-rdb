/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.srd.m2.entity.Region;

/**
 * Each public query method in a JPA repository is implicitly
 * read-only @Transactional
 */
public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByName(String name);
}
