/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s2;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.srd.m2.entity.Region;

@RestController
public class RegionRestController {
    private static final Logger log = LogManager.getLogger(RegionRestController.class);
    private final RegionRepository repo;

    public RegionRestController(RegionRepository repo) {
        this.repo = repo;
    }

    /**
     * Test:
     * 
     * <pre>
     * curl -X GET http://localhost:8080/regions
     * </pre>
     * 
     * @return a JSON with all regions with associated countries
     */
    @GetMapping("/regions")
    public List<Region> getAllRegions() {
        log.traceEntry("getAllRegions()");
        return repo.findAll();
    }

    /**
     * Test:
     * 
     * <pre>
     * curl -X POST http://localhost:8080/regions -H "Content-Type: application/json" -d "{\"name\": \"Down Below\"}"
     * </pre>
     * 
     * @param region the JSON representing the region to be persisted, like: {"name":"Down Below"} 
     * @return a JSON like: {"id":5,"name":"Down Below","countries":null}
     */
    @PostMapping("/regions")
    public Region createRegion(@RequestBody Region region) {
        log.traceEntry("createRegion()");
        return repo.save(region);
    }
}
