package dev.ynnk.m295.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.conf.security.Roles;
import dev.ynnk.m295.helper.serializer.View;
import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.User;
import dev.ynnk.m295.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.groups.Default;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableWebSecurity
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/user")
    public List<User> getAllUsers() {
        return this.service.getAllUser();
    }

    @Operation(summary = "Get a User by the ID. You can only access your own user " +
            "(must be equal to the username of the jwt token)")
    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/user/{id}")
    public User getUserById(@PathVariable("id") Long id, @AuthenticationPrincipal Jwt jwt) {
        return this.service.getUserByIdAndJwt(id, jwt);
    }

    @Operation(summary = "Endpoint where you can find a User by the id. It is only available for administrators")
    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/user/{id}/administrator")
    public User getUserById(@PathVariable("id") Long id) {
        return this.service.getUserById(id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PostMapping("/api/v1/user")
    public User createUser(@RequestBody @Validated(value = {Default.class, Create.class})
                               @JsonView({View.CreateAndUpdateUser.class}) User user) {
        return this.service.saveUser(user);
    }

    @RolesAllowed(Roles.ADMIN)
    @PatchMapping("/api/v1/user/{id}")
    public User patchUser(@RequestBody  @JsonView({View.CreateAndUpdateUser.class})
                              User partialUser, @PathVariable("id") Long id) {
        return this.service.patchUser(partialUser, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/user/{id}")
    public User updateUser(@RequestBody  @JsonView({View.CreateAndUpdateUser.class})
                               @Validated(value = {Default.class, Create.class}) User user,
                           @PathVariable("id") Long id) {
        return this.service.updateUser(user, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @DeleteMapping("/api/v1/user/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        this.service.deleteUser(id);
    }


    @Operation(summary = "Returns the User where the username is equal to the provided username in the jwt")
    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/user/whoami")
    public User getUserByJwt(@AuthenticationPrincipal Jwt oauth) {
        return this.service.getUserByJwt(oauth);
    }

}
