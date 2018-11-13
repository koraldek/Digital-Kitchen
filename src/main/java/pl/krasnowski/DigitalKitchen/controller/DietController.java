package pl.krasnowski.DigitalKitchen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pl.krasnowski.DigitalKitchen.config.security.OwnerValidator;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
import pl.krasnowski.DigitalKitchen.services.diet.DietUtilities;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diets")
class DietController {


    @Autowired
    private final DietManager dietManager;

    @Autowired
    private final DietUtilities dietUtilities;

    @Autowired
    private final UserService uService;


    @GetMapping(value = "/")
    public ModelAndView getDietsList() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("diets", dietManager.getDiets());
        mav.setViewName("diet/diets");
        return mav;
    }


    @GetMapping(value = "/current")
    public ModelAndView getCurrentDiet() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("diet", dietManager.getCurrentDiet());
        mav.addObject("dietList", dietManager.getDiets());
        mav.addObject("bmi", dietUtilities.calculateBMI(uService.getCurrentUser().getWeight(), uService.getCurrentUser().getHeight()));
        mav.addObject("dietDays", dietManager.getCurrentDiet().getDietDays());
        mav.setViewName("diet/current");

        return mav;
    }

    @GetMapping("/{dietID}")
    @PreAuthorize("isAuthenticated() and @ownerVal.isDietOwner(#dietID, authentication)")
    public ResponseEntity<Diet> getDiet(@PathVariable long diet_id) {
        return ResponseEntity.ok(dietManager.getByID(diet_id));
    }
}
