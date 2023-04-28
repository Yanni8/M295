package dev.ynnk.m295.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.conf.security.Roles;
import dev.ynnk.m295.helper.serializer.View;
import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.Test;
import dev.ynnk.m295.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.groups.Default;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SliderUI;
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

    @Operation(summary = "Endpoint to get a test by its id. You can only " +
            "access tests that you/your group is assigned to " +
            "You need to have a user with a username equal to your jwt username to access this endpoint" )
    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/test/{id}")
    public Test getTestById(@PathVariable("id") Long id, @AuthenticationPrincipal Jwt jwt) {
        return this.service.getTestByIdAndJwt(id, jwt);
    }

    @Operation(summary = "Get any test by id. Only administrators can access this endpoint")
    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/test/{id}/administrator")
    public Test getTestById(@PathVariable("id") Long id) {
        return this.service.getTestById(id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PostMapping("/api/v1/test")
    public Test createTest(@RequestBody @Validated(value = {Default.class, Create.class})
                               @JsonView(View.CreateTest.class) Test test) {
        return this.service.saveTest(test);
    }

    @RolesAllowed(Roles.ADMIN)
    @PatchMapping("/api/v1/test/{id}")
    public Test patchTest(@RequestBody @JsonView(View.UpdateTest.class) Test partialTest, @PathVariable("id") Long id) {
        return this.service.patchTest(partialTest, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/test/{id}")
    public Test updateTest(@RequestBody @JsonView(View.UpdateTest.class)
                               @Validated(value = {Default.class, Create.class}) Test test,
                           @PathVariable("id") Long id) {
        return this.service.updateTest(test, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @DeleteMapping("/api/v1/test/{id}")
    public void deleteTest(@PathVariable("id") Long id) {
        this.service.deleteTest(id);
    }

    @Operation(summary = "Endpoint to enable a test for a specific user or group " +
            "The parameter type needs to be group or user")
    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/test/{id}/invite")
    @JsonView(View.Metadata.class)
    public Test inviteTest(@PathVariable("id") Long testId, @RequestParam("userGroupId") Long id, @RequestParam(value = "type",
            defaultValue = "user") String type) {
        return this.service.invite(testId, id, type);
    }


    @Operation(summary = "Endpoint to disable a test for a specific user or group " +
            "The parameter type needs to be group or user")
    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/test/{id}/uninvite")
    @JsonView(View.Metadata.class)
    public Test uninviteTest(@PathVariable("id") Long testId, @RequestParam("id") Long id, @RequestParam(value = "type",
            defaultValue = "user") String type) {
        return this.service.uninvite(testId, id, type);
    }


    @Operation(summary = "Get all tests that you are asigned to. " +
            "You need to have a user with a username equal to your jwt username to access this endpoint")
    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/test/whoami")
    @JsonView(View.Metadata.class)
    public @ResponseBody List<Test> getOwnTestMetadata(@AuthenticationPrincipal Jwt jwt){
        return this.service.getTestMetdataByJwt(jwt);
    }

}
