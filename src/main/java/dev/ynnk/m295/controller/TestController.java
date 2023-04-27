package dev.ynnk.m295.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.conf.security.Roles;
import dev.ynnk.m295.helper.serializer.View;
import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.Test;
import dev.ynnk.m295.service.TestService;
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
public class TestController {


    private final TestService service;

    public TestController(TestService service) {
        this.service = service;
    }


    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/test")
    public List<Test> getAllTests() {
        return this.service.getAllTests();
    }

    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/test/{id}")
    public Test getTestById(@PathVariable("id") Long id) {
        return this.service.getTestById(id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PostMapping("/api/v1/test")
    public Test createTest(@RequestBody @Validated(value = {Default.class, Create.class}) Test test) {
        return this.service.saveTest(test);
    }

    @RolesAllowed(Roles.ADMIN)
    @PatchMapping("/api/v1/test/{id}")
    public Test patchTest(@RequestBody Test partialTest, @PathVariable("id") Long id) {
        System.out.println("Got Here");
        return this.service.patchTest(partialTest, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/test/{id}")
    public Test updateTest(@RequestBody @Validated(value = {Default.class, Create.class}) Test test,
                           @PathVariable("id") Long id) {
        return this.service.updateTest(test, id);
    }

    @DeleteMapping("/api/v1/test/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        this.service.deleteTest(id);
    }

    @PutMapping("/api/v1/test/{id}/invite")
    public Test inviteTest(@PathVariable("id") Long testId, @RequestParam("id") Long id, @RequestParam(value = "type",
            defaultValue = "user") String type) {
        return this.service.invite(testId, id, type);
    }


    @PutMapping("/api/v1/test/{id}/uninvite")
    public Test uninviteTest(@PathVariable("id") Long testId, @RequestParam("id") Long id, @RequestParam(value = "type",
            defaultValue = "user") String type) {
        return this.service.uninvite(testId, id, type);
    }


    @JsonView(View.Metadata.class)
    @GetMapping("/api/v1/test/my")
    public @ResponseBody List<Test> getOwnTestMetadata(@AuthenticationPrincipal Jwt jwt){
        return this.service.getTestMetdataByJwt(jwt);
    }

}
