/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.srd.m1.s3.JdbcRegionController;
import com.example.srd.m2.entity.Region;

@RestController
@RequestMapping("/api/regions")
public class RegionOneToManyController {
    private static final Logger log = LogManager.getLogger(JdbcRegionController.class);

    private final RegionService svc;

    public RegionOneToManyController(RegionService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
     * curl -X GET "http://localhost:8080/api/regions/Europe" -H "Content-Type: application/json"
     * </pre>
     */
    @GetMapping("/{name}")
    public ResponseEntity<Region> getRegionWithCountries(@PathVariable String name) {
        log.traceEntry("getRegionWithCountries({})", name);

        try {
            Region region = svc.getRegionWithCountries(name);
            return ResponseEntity.ok(region);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * <pre>
     * curl -X POST "http://localhost:8080/api/regions" -H "Content-Type: application/json" -d "{\"name\": \"Africa\", \"countries\": [{\"id\": \"MA\", \"name\": \"Morocco\"}, {\"id\": \"LY\", \"name\": \"Libya\"}]}"
     * </pre>
     */
    @PostMapping
    public ResponseEntity<Region> createRegionWithCountries(@RequestBody Region region) {
        try {
            Region result = svc.createRegionWithCountries(region);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
