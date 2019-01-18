package pl.krasnowski.DigitalKitchen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@Slf4j
class MainController {

    @Autowired
    private UserService uService;

    @Autowired
    private DietManager dietManager;


    @RequestMapping(value = {"/", "/homepage"})
    public ModelAndView getPages() {
        ModelAndView modelAndView = new ModelAndView("homepage");
        modelAndView.addObject("user", uService.getCurrentUser());
        return modelAndView;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();

        //uService.updateUser(uService.getCurrentUser());
        req.getSession().invalidate();
        try {
            req.logout();
        } catch (ServletException e) {
            log.error("Error while logout ", e);
        }
        modelAndView.setViewName("logout-successful");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User formUser, BindingResult bindingResult, HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = uService.findByEmail(formUser.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            modelAndView.setViewName("homepage");
            uService.createUser(formUser);
            try {
                req.login(formUser.getUsername(), formUser.getPassword());
            } catch (ServletException e) {
                modelAndView.setViewName("registration");
                log.error("Error while login ", e);
            }
        }
        return modelAndView;
    }
}