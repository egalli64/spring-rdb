/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s3;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m2.entity.Country;

@Controller
@RequestMapping("/m3/s3")
public class CustomRegionController {
    private static final Logger log = LogManager.getLogger(CustomRegionController.class);
    private final CustomRegionRepository repo;

    public CustomRegionController(CustomRegionRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/region")
    public String getRegionWithCountries(@RequestParam Long id, Model model) {
        log.traceEntry("getRegionsWithCountries({})", id);

        repo.findByIdWithCountries(id).ifPresentOrElse(region -> model.addAttribute("region", region), () -> {
            model.addAttribute("message", "Entity _not_ found!");
        });

        return "/m3/s3/result";
    }

    @GetMapping("/regions")
    public String getRegionsWithCountries(Model model) {
        log.traceEntry("getRegionsWithCountries()");

        model.addAttribute("regions", repo.findRegionsWithCountries());

        return "/m3/s3/result";
    }

    @PostMapping("/add-countries")
    public String addCountriesToRegion(@RequestParam Long id, @RequestParam String[] ids, @RequestParam String[] names,
            Model model) {
        log.traceEntry("addCountriesToRegion({})", id);

        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (!ids[i].isBlank() && !names[i].isBlank()) {
                countries.add(new Country(ids[i], names[i]));
            }
        }

        try {
            repo.addCountriesToExistingRegion(id, countries);
            return "redirect:/m3/s3/region?id=" + id;
        } catch (Exception ex) {
            log.info("Can't add countries to region {}", id);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("name", "Region " + id);
            return "error";
        }
    }
}
