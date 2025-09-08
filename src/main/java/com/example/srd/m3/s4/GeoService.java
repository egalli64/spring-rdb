/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s4;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.srd.m2.entity.Country;
import com.example.srd.m2.entity.Region;
import com.example.srd.m3.s2.CountryRepository;
import com.example.srd.m3.s2.RegionRepository;

@Service
public class GeoService {
    private final RegionRepository regionRepo;
    private final CountryRepository countryRepo;

    public GeoService(RegionRepository regionRepo, CountryRepository countryRepo) {
        this.regionRepo = regionRepo;
        this.countryRepo = countryRepo;
    }

    @Transactional
    public Region createRegionWithCountries(String regionName, List<Country> countries) {
        // Step 1: Create the region
        Region region = new Region(regionName);
        region = regionRepo.save(region);

        // Step 2: Create and associate countries
        for (Country country : countries) {
            country.setRegion(region);
            countryRepo.save(country);
        }

        // Step 3: Update region with countries
        region.setCountries(countries);
        regionRepo.save(region);

        return region;
    }

    /**
     * Being in a transaction, we guarantee no partial migrations
     */
    @Transactional
    public void moveCountriesToNewRegion(List<String> countryIds, String newRegionName) {
        // Step 1: Create the new region
        Region newRegion = new Region(newRegionName);
        newRegion = regionRepo.save(newRegion);

        // Step 2: Move each country to the new region
        for (String countryId : countryIds) {
            Country country = countryRepo.findById(countryId).orElseThrow( //
                    () -> new RuntimeException("Country not found: " + countryId));

            // Remove from old region
            Region oldRegion = country.getRegion();
            if (oldRegion != null) {
                oldRegion.getCountries().remove(country);
                regionRepo.save(oldRegion);
            }

            // Assign to new region
            country.setRegion(newRegion);
            countryRepo.save(country);
        }

        // Step 3: Validate the new region has countries
        if (countryRepo.countByRegionId(newRegion.getId()) == 0) {
            throw new RuntimeException("No countries were successfully moved");
        }
    }

    @Transactional
    public void exampleWithRollback(String regionName, Country country) {
        regionRepo.save(new Region(regionName));
        countryRepo.save(country);

        throw new RuntimeException("Rollback, something went wrong");
    }

    @Transactional(rollbackFor = Exception.class)
    public void exampleWithCheckedExceptionRollback(String regionName) throws Exception {
        regionRepo.save(new Region(regionName));
        throw new Exception("This will also trigger rollback");
    }

    /**
     * Multiple read operations in same transaction optimized for read-only access
     */
    @Transactional(readOnly = true)
    public String getRegionInfo(Long regionId) {
        Region region = regionRepo.findById(regionId).orElseThrow(() -> new RuntimeException("Region not found"));
        Integer countryCount = countryRepo.countByRegionId(regionId);

        return String.format("Region %s contains %d countries", region.getName(), countryCount);
    }
}
