/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m2.s4;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
@RequestMapping("/m2/s4")
public class CrudRegionController {
    private static final Logger log = LogManager.getLogger(CrudRegionController.class);

    private CrudRegionRepository repo;

    public CrudRegionController(CrudRegionRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/create")
    public String create(@RequestParam String name, Model model) {
        log.traceEntry("create");

        try {
            Region entity = repo.save(new Region(name));
            model.addAttribute("message", "Created: " + entity);
        } catch (Exception ex) {
            log.warn("Can't persist {}", name);
            model.addAttribute("message", "Entity _not_ created!");
        }

        return "/m2/s4/result";
    }

    @PostMapping("/rename")
    public String rename(@RequestParam Long id, @RequestParam String name, Model model) {
        log.traceEntry("rename");

        repo.findById(id).ifPresentOrElse(entity -> {
            entity.setName(name);
            Region edited = repo.save(entity);
            model.addAttribute("message", "Saved: " + edited);
        }, () -> model.addAttribute("message", "No entity found with id " + id));

        return "/m2/s4/result";
    }

    @GetMapping("/check")
    public String exists(@RequestParam Long id, Model model) {
        log.traceEntry("exists({})", id);

        model.addAttribute("message", String.format("Entity %d %sfound", id, repo.existsById(id) ? "" : "NOT "));

        return "/m2/s4/result";
    }

    @GetMapping("/all")
    public String all(Model model) {
        log.traceEntry("all");

        model.addAttribute("message", "Found: " + repo.findAll());

        return "/m2/s4/result";
    }

    @GetMapping("/some")
    public String some(@RequestParam List<Long> ids, Model model) {
        log.traceEntry("some");

        model.addAttribute("message", "Found: " + repo.findAllById(ids));

        return "/m2/s4/result";
    }

    @GetMapping("/count")
    public String count(Model model) {
        log.traceEntry("count");

        model.addAttribute("message", String.format("Found %d entities", repo.count()));

        return "/m2/s4/result";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        log.traceEntry("deleteById({})", id);

        try {
            repo.deleteById(id);
            model.addAttribute("message", "Entity " + id + " has been deleted (if it was present)");
        } catch (Exception ex) {
            model.addAttribute("message", "Can't delete entity " + id);
        }

        return "/m2/s4/result";
    }

    @GetMapping("/find-delete")
    public String findDelete(@RequestParam Long id, Model model) {
        log.traceEntry("findDelete({})", id);

        Optional<Region> opt = repo.findById(id);
        if (opt.isPresent()) {
            try {
                repo.delete(opt.get());
                model.addAttribute("message", opt.get() + " deleted");
            } catch (Exception ex) {
                String msg = String.format("Can't delete %s", opt.get());
                log.warn(msg, ex);
                model.addAttribute("message", msg);
            }
        } else {
            model.addAttribute("message", "Can't find entity " + id);
        }

        return "/m2/s4/result";
    }

    @GetMapping("/delete-some")
    public String deleteSome(@RequestParam List<Long> ids, Model model) {
        log.traceEntry("deleteSome({})", ids);

        List<Region> regions = ids.stream() //
                .filter(Objects::nonNull) //
                .map(repo::findById) //
                .flatMap(Optional::stream) //
                .toList();

        model.addAttribute("message", "Attempt to delete " + regions);
        try {
            repo.deleteAll(regions);
        } catch (Exception ex) {
            log.error("Failure when deleteSome()", ex);
        }

        return "/m2/s4/result";
    }

    @GetMapping("/delete-all")
    public String deleteAll(Model model) {
        log.traceEntry("deleteAll");

        try {
            repo.deleteAll();
            model.addAttribute("message", "All entities deleted");
        } catch (Exception ex) {
            log.error("Failure when deleteAll()", ex);
            model.addAttribute("message", "Can't delete all entities");
        }

        return "/m2/s4/result";
    }
}
