/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s3;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.srd.m2.entity.Country;
import com.example.srd.m2.entity.Region;

@RestController
@RequestMapping("/regions")
public class CustomRegionRestController {
    private static final Logger log = LogManager.getLogger(CustomRegionRestController.class);
    private final CustomRegionRepository repo;

    public CustomRegionRestController(CustomRegionRepository repo) {
        this.repo = repo;
    }

    /**
     * Test
     * 
     * <pre>
     * curl -X GET localhost:8080/regions/with-countries
     * </pre>
     */
    @GetMapping("/with-countries")
    public List<Region> getRegionsWithCountries() {
        log.traceEntry("getRegionsWithCountries()");
        return repo.findRegionsWithCountries();
    }

    /**
     * Test
     * 
     * <pre>
     * curl -X POST localhost:8080/regions/1/countries -H "Content-Type: application/json" -d "[{\"id\": \"AA\", \"name\": \"Aaa\"}, {\"id\": \"BB\", \"name\": \"Bbb\"}]"
     * </pre>
     */
    @PostMapping("/{regionId}/countries")
    public ResponseEntity<String> addCountriesToRegion(@PathVariable Long regionId,
            @RequestBody List<Country> countries) {
        log.traceEntry("addCountriesToRegion({})", regionId);

        try {
            repo.addCountriesToExistingRegion(regionId, countries);
            return ResponseEntity.ok("Countries successfully added to region " + regionId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add countries: " + e.getMessage());
        }
    }
}
