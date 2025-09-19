/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd.x.ros;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ros")
public class RosController {
    private static final Logger log = LogManager.getLogger(RosController.class);

    private CategoryRepository catRepo;
    private MenuRepository menuRepo;
    private OrderingRepository ordRepo;

    public RosController(CategoryRepository catRepo, MenuRepository menuRepo, OrderingRepository ordRepo) {
        this.catRepo = catRepo;
        this.menuRepo = menuRepo;
        this.ordRepo = ordRepo;
    }

    @GetMapping
    public String home(HttpSession session) {
        log.traceEntry("home()");

        if (session.getAttribute("ordering") == null) {
            Ordering ord = ordRepo.save(new Ordering());
            session.setAttribute("ordering", ord);

            Iterable<Menu> items = menuRepo.findAll();
            session.setAttribute("menuItems", items);

            Map<Integer, Menu> orders = new HashMap<>();
            for (Menu item : items) {
                orders.put(item.getId(), item);
            }

            session.setAttribute("orders", orders);
            session.setAttribute("categories", catRepo.findAll());
        }

        return "/ros/home";
    }

    @GetMapping("cart")
    public String cart(HttpSession session) {
        log.traceEntry("cart()");

        Ordering ord = (Ordering) session.getAttribute("ordering");
        log.trace("{}", ord);

        return "/ros/cart";
    }

    @GetMapping("checkout")
    public String checkout(HttpSession session) {
        log.traceEntry("checkout()");

        session.invalidate();

        return "redirect:/ros";
    }
}
