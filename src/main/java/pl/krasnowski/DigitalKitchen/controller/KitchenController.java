package pl.krasnowski.DigitalKitchen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class KitchenController {

    @GetMapping(value = "/kitchen")
    public ModelAndView kitchenPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("kitchen");
        return mav;
    }
}
