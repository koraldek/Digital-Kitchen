package pl.krasnowski.DigitalKitchen.controller;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import pl.krasnowski.DigitalKitchen.model.domain.user.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

    @Override
    public Resource<User> toResource(User user) {

        return new Resource<>(user,
                ControllerLinkBuilder.linkTo(methodOn(UserController.class).one(user.getUserID())).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
}
