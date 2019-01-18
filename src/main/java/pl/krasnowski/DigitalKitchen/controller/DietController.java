package pl.krasnowski.DigitalKitchen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.krasnowski.DigitalKitchen.model.DAO.FoodDAO;
import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
import pl.krasnowski.DigitalKitchen.model.DTO.LoggedMealDTO;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
import pl.krasnowski.DigitalKitchen.services.diet.DietUtilities;
import pl.krasnowski.DigitalKitchen.services.diet.FoodLogger;
import pl.krasnowski.DigitalKitchen.services.diet.MealScheduler;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/diets")
@Slf4j
class DietController {

    @Autowired
    private DietManager dietManager;

    @Autowired
    private DietUtilities dietUtilities;

    @Autowired
    private UserService uService;

    @Autowired
    private FoodLogger foodLogger;

    @Autowired
    private MealScheduler mealScheduler;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private FoodDAO foodDAO;

    @Autowired
    private DatabaseManager dbManager;


    @GetMapping(value = "/")
    public ModelAndView getDietsList() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("diets", dietManager.getDiets());
        mav.setViewName("diet/current");
        return mav;
    }

    @PostMapping(value = "/logger", consumes = "application/json")
    @Transactional
    public ResponseEntity<Void> logFood(@RequestBody LoggedMealDTO loggedMealDTO) {
        Food entity = dbManager.getFoodByID(loggedMealDTO.getEntityID(), loggedMealDTO.getEntityDbName());
        log.debug("Logging food to diary:{}. Food details:{}", loggedMealDTO.toString(), entity.toString());

        foodLogger.logFood(
                dietUtilities.toFoodWrapper(loggedMealDTO.getServing_unit(), loggedMealDTO.getServing_size(), entity),
                dietUtilities.toDateTime(loggedMealDTO.getDate(), loggedMealDTO.getTime()),
                loggedMealDTO.getMeal());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "/current")
    public ModelAndView getCurrentDiet() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("diet", dietManager.getCurrentDiet());
        mav.addObject("dietList", dietManager.getDiets());
        mav.addObject("bmi", dietUtilities.calculateBMI(
                uService.getCurrentUser().getWeight(), uService.getCurrentUser().getHeight()));
        mav.addObject("dietDays", dietManager.getCurrentDiet().getDietDays());
        mav.addObject("diary", userProfileService.getHistoryDay(LocalDate.now()).getFoodAndWorkoutDiary());
        mav.setViewName("diet/current");
        return mav;
    }

    @GetMapping("/{dietID}")
    @PreAuthorize("isAuthenticated() and @ownerVal.isDietOwner(#dietID, authentication)")
    public ResponseEntity<Diet> getDiet(@PathVariable long diet_id) {
        return ResponseEntity.ok(dietManager.getByID(diet_id));
    }

    @RequestMapping(value = "/food-diary", method = RequestMethod.GET)
    public DietDay getAutocompleteFoodList(@RequestParam("date") String date) {
        return userProfileService.getHistoryDay(
                dietUtilities.toDateTime(date, "00:00").toLocalDate())
                .getFoodAndWorkoutDiary();
    }
}