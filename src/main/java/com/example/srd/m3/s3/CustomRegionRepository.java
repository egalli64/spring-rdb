/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s3;

import java.util.List;
import java.util.Optional;

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
    private final EntityManager em;

    /**
     * Injecting by constructor, preferred over @PersistenceContext on the field
     */
    public CustomRegionRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Override the class @Transactional setting, here to specify that a read-only
     * transaction should be used
     */
    @Transactional(readOnly = true)
    public List<Region> findRegionsWithCountries() {
        return em.createQuery("SELECT DISTINCT r FROM Region r LEFT JOIN FETCH r.countries ORDER BY r.name",
                Region.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<Region> findByIdWithCountries(Long id) {
        Region region = em
                .createQuery("SELECT DISTINCT r FROM Region r LEFT JOIN FETCH r.countries WHERE r.id = :id", Region.class)
                .setParameter("id", id).getResultStream().findFirst().orElse(null);

        return Optional.ofNullable(region);
    }

    /**
     * Being a public method, is write @Transactional as the class specifies
     * <p>
     * Single transaction, ACID behavior. In case of failure, full roll back
     */
    public void addCountriesToExistingRegion(Long regionId, List<Country> countries) {
        // Find the region within the transaction
        Region region = em.find(Region.class, regionId);
        if (region == null) {
            throw new RuntimeException("Region not found with id: " + regionId);
        }

        for (Country country : countries) {
            country.setRegion(region);
            em.persist(country);
        }
    }
}
