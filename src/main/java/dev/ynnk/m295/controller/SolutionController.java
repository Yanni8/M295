package dev.ynnk.m295.controller;

import dev.ynnk.m295.conf.security.Roles;
import dev.ynnk.m295.model.Solution;
import dev.ynnk.m295.model.dto.SolutionDTO;
import dev.ynnk.m295.service.SolutionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.websocket.server.PathParam;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableWebSecurity
@SecurityRequirement(name = "bearerAuth")
public class SolutionController {

    private final SolutionService service;


    public SolutionController(SolutionService service) {
        this.service = service;
    }


    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/solution/")
    public List<Solution> getAllSolutions() {
        return this.service.getAllSolutions();
    }

    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/solution/{id}")
    public Solution getSolutionById(@PathVariable("id") Long solutionId) {
        return this.service.findById(solutionId);
    }

    @RolesAllowed(Roles.USER)
    @PostMapping("/api/v1/solution/")
    public Solution correctTest(@RequestBody @Validated SolutionDTO solution,
                                @PathParam("testID") Long testId, @PathParam("userId") Long userId,
                                @AuthenticationPrincipal Jwt jwt) {

        return this.service.correctTest(solution, testId, userId, jwt);
    }

    @RolesAllowed(Roles.ADMIN)
    @DeleteMapping("/api/v1/solution/{id}")
    public void deleteSolution(@PathVariable("id") Long solutionId) {
        this.service.deleteSolution(solutionId);
    }

}
