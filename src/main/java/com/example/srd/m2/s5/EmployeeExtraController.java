/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m2.s5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m2.entity.Employee;

@Controller
@RequestMapping("/m2/s5")
public class EmployeeExtraController {
    private static final Logger log = LogManager.getLogger(EmployeeExtraController.class);

    private EmployeeExtraRepository repo;

    public EmployeeExtraController(EmployeeExtraRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/by-firstname")
    public String byFirstName(@RequestParam String name, Model model) {
        log.traceEntry("byFirstName({})", name);

        Iterable<Employee> entities = repo.findByFirstName(name);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }

    @GetMapping("/firstname-starting")
    public String firstNameStarting(@RequestParam String prefix, Model model) {
        log.traceEntry("firstNameStarting({})", prefix);

        Iterable<Employee> entities = repo.findByFirstNameStartingWith(prefix);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }

    @GetMapping("/firstname-starting-lastname-containing")
    public String firstNameStartingLastNameContaining(@RequestParam String first, @RequestParam String last,
            Model model) {
        log.traceEntry("firstNameStartingLastNameContaining({}, {})", first, last);

        Iterable<Employee> entities = repo.findByFirstNameStartingWithOrLastNameContainingIgnoreCase(first, last);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }
}
