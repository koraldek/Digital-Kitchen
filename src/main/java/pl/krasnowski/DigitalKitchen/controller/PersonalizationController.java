package pl.krasnowski.DigitalKitchen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.krasnowski.DigitalKitchen.model.domain.user.UserDTO;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

@Controller
@RequestMapping("/personalization")
public class PersonalizationController {


    private final UserService userService;

    private final UserProfileService userProfileService;

    public PersonalizationController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(String oldPassword, String newPassword) {
        boolean passedGoodPassword = userProfileService.changePassword(oldPassword, newPassword);
        if (passedGoodPassword)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    //****************************** UPDATE PAGE ************************************************************

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView updateProfile(UserDTO userDTO) {
        ModelAndView mav = new ModelAndView();
        userService.updateUser(userDTO);

        mav.addObject("usr", userService.getCurrentUser());
        mav.setViewName("update-profile");
        return mav;
    }

    @GetMapping(value = "/update")
    public ModelAndView updateProfile() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("usr", userProfileService.toDTO(userService.getCurrentUser()));
        mav.setViewName("update-profile");
        return mav;
    }

}
