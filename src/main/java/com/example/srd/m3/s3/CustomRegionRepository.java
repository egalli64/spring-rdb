/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s3;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.srd.m2.entity.Country;
import com.example.srd.m2.entity.Region;

import jakarta.persistence.EntityManager;

/**
 * This is not a standard JPA repository, the explicit @Transactional annotation
 * signals that each public method should be managed in a transaction
 */
@Repository
@Transactional
public class CustomRegionRepository {
    /** Spring takes care of using the entity manager in a thread safe way */
    private final EntityManager entityManager;

    /**
     * Injecting by constructor, preferred over using the @Autowired on the field
     */
    public CustomRegionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Override the class @Transactional setting, here to specify that a read-only
     * transaction should be used
     */
    @Transactional(readOnly = true)
    public List<Region> findRegionsWithCountryCount() {
        return entityManager.createQuery("SELECT r FROM Region r LEFT JOIN FETCH r.countries", Region.class)
                .getResultList();
    }

    /**
     * Being a public method, is write @Transactional as the class specifies
     * <p>
     * Single transaction, ACID behavior. In case of failure, full roll back
     */
    public void addCountriesToExistingRegion(Long regionId, List<Country> countries) {
        // Find the region within the transaction
        Region region = entityManager.find(Region.class, regionId);
        if (region == null) {
            throw new RuntimeException("Region not found with id: " + regionId);
        }

        for (Country country : countries) {
            country.setRegion(region);
            entityManager.persist(country);
        }
    }
}
