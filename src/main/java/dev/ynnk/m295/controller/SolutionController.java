package dev.ynnk.m295.controller;

import dev.ynnk.m295.model.Solution;
import dev.ynnk.m295.model.Test;
import dev.ynnk.m295.model.dto.SolutionDTO;
import dev.ynnk.m295.service.SolutionService;
import dev.ynnk.m295.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.TemporalType;
import jakarta.websocket.server.PathParam;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.SliderUI;

@RestController
@EnableWebSecurity
@SecurityRequirement(name = "bearerAuth")
public class SolutionController {

    private final SolutionService service;


    public SolutionController(SolutionService service){
        this.service = service;
    }


    @PostMapping("/api/v1/solution/")
    public Solution correctTest(@RequestBody @Validated SolutionDTO solution,
                                @PathParam("testID") Long testId, @PathParam("userId") Long userId){

        return this.service.correctTest(solution, testId, userId);
    }

}
