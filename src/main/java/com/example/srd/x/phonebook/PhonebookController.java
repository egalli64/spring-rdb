/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.phonebook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/phonebook")
public class PhonebookController {
    private static final Logger log = LogManager.getLogger(PhonebookController.class);

    private ContactRepository repo;

    public PhonebookController(ContactRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String home(Model model) {
        log.traceEntry("home()");

        model.addAttribute("contacts", repo.findAllByOrderByName());

        return "/x/phonebook/home";
    }

    @PostMapping("delete")
    public String delete(@RequestParam int id) {
        log.traceEntry("delete({})", id);

        repo.deleteById(id);

        return "redirect:/phonebook";
    }

    @PostMapping("create")
    public String create(@RequestParam String name, @RequestParam String phone) {
        log.traceEntry("create({}, {})", name, phone);

        Contact contact = repo.save(new Contact(name, phone));
        log.debug("New contact {}", contact);

        return "redirect:/phonebook";
    }
}
