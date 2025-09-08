/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s4;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m2.entity.Country;
import com.example.srd.m2.entity.Region;

@Controller
@RequestMapping("/m3/s4")
public class GeoController {
    private static final Logger log = LogManager.getLogger(GeoController.class);

    private final GeoService svc;

    public GeoController(GeoService svc) {
        this.svc = svc;
    }

    @PostMapping("/create-region")
    public String createRegionWithCountries(@RequestParam String name, @RequestParam String[] ids,
            @RequestParam String[] names, Model model) {
        log.traceEntry("createRegionWithCountries({})", name);

        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (!ids[i].isBlank() && !names[i].isBlank()) {
                countries.add(new Country(ids[i], names[i]));
            }
        }

        try {
            Region region = svc.createRegionWithCountries(name, countries);
            return "redirect:/m3/s3/region?id=" + region.getId();
        } catch (Exception ex) {
            log.info("Can't create region {} and associated countries", name);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("name", "Region " + name);
            return "error";
        }
    }
}
