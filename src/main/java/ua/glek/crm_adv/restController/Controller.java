package ua.glek.crm_adv.restController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    @RequestMapping("/authenticate")
public class Controller {
    @GetMapping
    public String index() {
        return "abobachka";
    }
}
