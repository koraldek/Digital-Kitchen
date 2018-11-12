package pl.krasnowski.DigitalKitchen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
class MainController {

    @Autowired
    private UserService uService;

    @Autowired
    private User user;


    @RequestMapping(value = {"/", "/homepage"})
    public ModelAndView getPages() {
        return new ModelAndView("homepage");
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        uService.updateUser(uService.getCurrentUser());
        request.getSession().invalidate();
        modelAndView.setViewName("logout_successful");
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
            User newUser = uService.preCreateUser(formUser);
            try {
                req.login(formUser.getUsername(), formUser.getPassword());
            } catch (ServletException e) {
                log.error("Error while login ", e);
            }
            BeanUtils.copyProperties(newUser, user);
            newUser = uService.postCreateUser(newUser);
            BeanUtils.copyProperties(newUser, user);
        }
        return modelAndView;
    }
}