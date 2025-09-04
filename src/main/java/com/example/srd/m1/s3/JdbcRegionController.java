/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m1.s3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m1.entity.Region;

@Controller
@RequestMapping("/m1/s3")
public class JdbcRegionController {
    private static final Logger log = LogManager.getLogger(JdbcRegionController.class);

    private JdbcRegionRepository repo;

    public JdbcRegionController(JdbcRegionRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/create")
    public String create(@RequestParam String name, Model model) {
        log.traceEntry("create({})", name);

        try {
            repo.save(new Region(name));
            model.addAttribute("message", "Created region with name " + name);
        } catch (DataAccessException ex) {
            log.warn("Can't create entity", ex);
            model.addAttribute("message", "Entity _not_ created!");
        }

        return "/m1/s3/result";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam String name, Model model) {
        log.traceEntry("update({}, {})", id, name);

        try {
            Region region = new Region(id, name);
            repo.save(region);
            model.addAttribute("message", "Updated: " + region);
        } catch (DataAccessException ex) {
            log.warn("Can't update", ex);
            model.addAttribute("message", "Entity _not_ updated!");
        }

        return "/m1/s3/result";
    }
}
