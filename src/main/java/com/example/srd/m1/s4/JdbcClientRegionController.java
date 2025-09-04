/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m1.s4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m1.entity.Region;

@Controller
@RequestMapping("/m1/s4")
public class JdbcClientRegionController {
    private static final Logger log = LogManager.getLogger(JdbcClientRegionController.class);

    private JdbcClientRegionRepository repo;

    public JdbcClientRegionController(JdbcClientRegionRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/read")
    public String read(@RequestParam Long id, Model model) {
        log.traceEntry("read({})", id);

        try {
            Region region = repo.findById(id);
            model.addAttribute("message", "Read " + region);
        } catch (DataAccessException ex) {
            String msg = String.format("Can't read region %d", id);
            log.warn(msg, ex);
            model.addAttribute("message", msg);
        }

        return "/m1/s3/result";
    }

    @PostMapping("/create")
    public String create(@RequestParam String name, Model model) {
        log.traceEntry("create({})", name);

        try {
            Region region = repo.save(new Region(name));
            model.addAttribute("message", "Created " + region);
        } catch (DataAccessException ex) {
            String msg = String.format("Can't create region %s", name);
            log.warn(msg, ex);
            model.addAttribute("message", msg);
        }

        return "/m1/s3/result";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id, @RequestParam String name, Model model) {
        log.traceEntry("update({}, {})", id, name);

        Region region = new Region(id, name);
        try {
            repo.save(region);
            model.addAttribute("message", "Updated: " + region);
        } catch (DataAccessException ex) {
            String msg = String.format("Can't update %s", region);
            log.warn(msg, ex);
            model.addAttribute("message", msg);
        }

        return "/m1/s3/result";
    }
}
