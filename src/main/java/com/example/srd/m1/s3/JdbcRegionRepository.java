/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m1.s3;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.srd.m1.entity.Region;

/**
 * A Spring JDBC API Repository
 */
@Repository
public class JdbcRegionRepository {
    private final JdbcTemplate jdbc;

    public JdbcRegionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Saves a Region entity. The id determines if it is an INSERT (if missing) or
     * an UPDATE. Assuming the DBMS generates the id on its own.
     * <p>
     * This simplified version does not return the entity as persisted
     * 
     * @param region The Region object to save.
     */
    public void save(Region region) {
        if (region.getId() == null) {
            insert(region);
        } else {
            update(region);
        }
    }

    private void insert(Region region) {
        String sql = "INSERT INTO region (name) VALUES (?)";
        jdbc.update(sql, region.getName());
    }

    private void update(Region region) {
        String sql = "UPDATE region SET name = ? WHERE region_id = ?";
        int rowsAffected = jdbc.update(sql, region.getName(), region.getId());

        if (rowsAffected == 0) {
            throw new DataRetrievalFailureException("No region found with ID " + region.getId() + " to update.");
        }
    }
}
