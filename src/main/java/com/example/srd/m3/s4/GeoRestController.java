/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s4;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.srd.m2.entity.Country;
import com.example.srd.m2.entity.Region;

@RestController
@RequestMapping("/geo")
public class GeoRestController {

    private final GeoService svc;

    public GeoRestController(GeoService svc) {
        this.svc = svc;
    }

    /**
     * <pre>
     curl -X POST "http://localhost:8080/geo/regions/ABC/countries" ^
     -H "Content-Type: application/json" ^
     -d "[{\"id\":\"AA\",\"name\":\"Aaa\"},{\"id\":\"BB\",\"name\":\"Bbb\"}]"
     * </pre>
     */
    @PostMapping("/regions/{regionName}/countries")
    public ResponseEntity<Region> createRegionWithCountries( //
            @PathVariable String regionName, @RequestBody List<Country> countries) {
        try {
            Region createdRegion = svc.createRegionWithCountries(regionName, countries);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRegion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
