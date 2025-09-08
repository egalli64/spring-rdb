/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s4;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.srd.m2.entity.Region;

public interface RegionQueryRepository extends JpaRepository<Region, Integer> {
    @Query("SELECT r FROM Region r LEFT JOIN FETCH r.countries WHERE r.name = :name")
    Optional<Region> findByNameWithCountries(@Param("name") String name);

    Optional<Region> findByName(String name);
}
