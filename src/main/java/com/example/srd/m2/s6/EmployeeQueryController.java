/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.m2.s6;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.srd.m2.entity.Employee;

@Controller
@RequestMapping("/m2/s6")
public class EmployeeQueryController {
    private static final Logger log = LogManager.getLogger(EmployeeQueryController.class);

    private EmployeeQueryRepository repo;

    public EmployeeQueryController(EmployeeQueryRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/by-salary-range")
    public String bySalaryRange(@RequestParam BigDecimal low, @RequestParam BigDecimal high, Model model) {
        log.traceEntry("bySalaryRange({}, {})", low, high);

        Iterable<Employee> entities = repo.findBySalaryRange(low, high);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }

    @GetMapping("/by-salary-range-names")
    public String bySalaryRangeNames(@RequestParam BigDecimal low, @RequestParam BigDecimal high, Model model) {
        log.traceEntry("bySalaryRangeNames({}, {})", low, high);

        Iterable<Employee> entities = repo.findBySalaryRangeNames(low, high);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }

    @GetMapping("/with-prefix")
    public String withPrefix(@RequestParam String prefix, Model model) {
        log.traceEntry("withPrefix({})", prefix);

        Iterable<Employee> entities = repo.findByFirstName(prefix);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }

    @GetMapping("/with-infix")
    public String withInfix(@RequestParam String infix, Model model) {
        log.traceEntry("withInfix({})", infix);

        Iterable<Employee> entities = repo.findByFirstNameIn(infix);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }

    @GetMapping("/by-salary-range-native")
    public String bySalaryRangeNative(@RequestParam BigDecimal low, @RequestParam BigDecimal high, Model model) {
        log.traceEntry("bySalaryRangeNative({}, {})", low, high);

        Iterable<Employee> entities = repo.findBySalaryRangeNative(low, high);
        model.addAttribute("message", "Found: " + entities);

        return "/result";
    }
}
