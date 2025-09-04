/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m1.s4;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.srd.m1.entity.Region;

/**
 * A Spring JDBC API Repository using JdbcClient
 */
@Repository
public class JdbcClientRegionRepository {
    private final JdbcClient jdbc;

    public JdbcClientRegionRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Get the required region
     * 
     * @param id the region id
     * @return the found region
     * @throws DataAccessException due to single()
     */
    public Region findById(Long id) {
        return jdbc.sql("SELECT region_id as id, name FROM Region WHERE region_id = :id") //
                .param("id", id) //
                .query(Region.class) //
                .single();
    }

    /**
     * Saves a Region entity. The id determines if it is an INSERT (if missing) or
     * an UPDATE. Assuming the DBMS generates the id on its own.
     */
    public Region save(Region region) {
        return (region.getId() == null) ? insert(region) : update(region);
    }

    private Region insert(Region region) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.sql("INSERT INTO region (name) VALUES (:name)") //
                .param("name", region.getName()) //
                .update(keyHolder);

        if (keyHolder.getKey() != null) {
            region.setId(keyHolder.getKey().longValue());
        }
        return region;
    }

    private Region update(Region region) {
        int affected = jdbc.sql("UPDATE region SET name = :name WHERE region_id = :id") //
                .param("name", region.getName()).param("id", region.getId()) //
                .update();

        if (affected == 0) {
            throw new DataRetrievalFailureException("No region found with ID " + region.getId() + " to update.");
        }

        return region;
    }
}
