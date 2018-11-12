package pl.krasnowski.DigitalKitchen.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krasnowski.DigitalKitchen.model.DAO.UserDAO;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;
import pl.krasnowski.DigitalKitchen.model.exceptions.UserNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDAO userDAO;
    private final UserResourceAssembler assembler;

    public UserController(UserDAO userDAO, UserResourceAssembler assembler) {
        this.userDAO = userDAO;
        this.assembler = assembler;
    }


    @GetMapping("/")
    public Resources<Resource<User>> all() {
        List<Resource<User>> users = userDAO.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/")
    ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {
        Resource<User> resource = assembler.toResource(userDAO.save(newUser));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Long id) {
        Resource<User> resource;
        try {
            resource = assembler.toResource(userDAO.getOne(id));
        } catch (EntityNotFoundException ex) {
            throw new UserNotFoundException(id);
        }
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return responseEntity;
    }


    @PutMapping("/{id}")
    ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) throws URISyntaxException {

        User updatedEmployee = userDAO.findById(id)
                .map(user -> {
                    BeanUtils.copyProperties(newUser, user);
                    return userDAO.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserID(id);
                    return userDAO.save(newUser);
                });

        Resource<User> resource = assembler.toResource(updatedEmployee);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        userDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}