package pl.krasnowski.DigitalKitchen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/")
    public ModelAndView getPages() {
        System.out.println("Strona glowna");
        return new ModelAndView("test_autocomplete");
    }

}
