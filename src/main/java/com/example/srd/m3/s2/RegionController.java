/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m3.s2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m2.entity.Region;

@Controller
@RequestMapping("/m3/s2")
public class RegionController {
    private static final Logger log = LogManager.getLogger(RegionController.class);

    private final RegionRepository repo;

    public RegionController(RegionRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/all-regions")
    public String getAllRegions(Model model) {
        log.traceEntry("getAllRegions()");
        model.addAttribute("message", "Found: " + repo.findAll());

        return "/result";
    }

    @GetMapping("/get-region")
    public String getRegion(@RequestParam long id, Model model) {
        log.traceEntry("getRegion({})", id);

        repo.findById(id).ifPresentOrElse(region -> model.addAttribute("message", region), () -> {
            model.addAttribute("message", "Entity _not_ found!");
        });

        return "/result";
    }

    @PostMapping("/create-region")
    public String createRegion(@RequestParam String name, Model model) {
        log.traceEntry("createRegion({})", name);

        try {
            Region entity = repo.save(null);
            return "redirect:/m3/s2/get-region?id=" + entity.getId();
        } catch (Exception ex) {
            log.info("Can't get {}", name);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("name", name);
            return "error";
        }
    }
}
