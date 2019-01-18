package pl.krasnowski.DigitalKitchen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.krasnowski.DigitalKitchen.model.DTO.LoggedMealDTO;
import pl.krasnowski.DigitalKitchen.model.domain.diet.Diet;
import pl.krasnowski.DigitalKitchen.model.domain.diet.DietDay;
import pl.krasnowski.DigitalKitchen.model.domain.food.Food;
import pl.krasnowski.DigitalKitchen.services.diet.DietManager;
import pl.krasnowski.DigitalKitchen.services.diet.DietUtilities;
import pl.krasnowski.DigitalKitchen.services.diet.FoodLogger;
import pl.krasnowski.DigitalKitchen.services.foodDbManager.DatabaseManager;
import pl.krasnowski.DigitalKitchen.services.user.UserProfileService;
import pl.krasnowski.DigitalKitchen.services.user.UserService;

@RestController
@RequestMapping("/api/diets")
@Slf4j
class DietController {

    @Autowired
    private final DietManager dietManager;

    @Autowired
    private final DietUtilities dietUtilities;

    @Autowired
    private final UserService uService;

    @Autowired
    private final FoodLogger foodLogger;

    @Autowired
    private final UserProfileService userProfileService;

    @Autowired
    private final DatabaseManager dbManager;

    public DietController(@Lazy DietManager dietManager, @Lazy DietUtilities dietUtilities, @Lazy UserService uService,
                          @Lazy FoodLogger foodLogger, @Lazy UserProfileService userProfileService,
                          @Lazy DatabaseManager dbManager) {
        this.dietManager = dietManager;
        this.dietUtilities = dietUtilities;
        this.uService = uService;
        this.foodLogger = foodLogger;
        this.userProfileService = userProfileService;
        this.dbManager = dbManager;
    }


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

        DietDay tempDD = new DietDay();
        userProfileService.getDietHistory().forEach(historyDay -> // Aggregate meals from diary
                historyDay.getFoodAndWorkoutDiary().getMeals().forEach(
                        tempDD::addMeal));
        mav.addObject("diary", tempDD);
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