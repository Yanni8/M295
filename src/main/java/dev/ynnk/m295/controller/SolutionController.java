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
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SliderUI;
import java.util.List;

@RestController
@EnableWebSecurity
@SecurityRequirement(name = "bearerAuth")
public class SolutionController {

    private final SolutionService service;


    public SolutionController(SolutionService service){
        this.service = service;
    }


    @GetMapping("/api/v1/solution/")
    public List<Solution> getAllSolutions(){
        return this.service.getAllSolutions();
    }

    @GetMapping("/api/v1/solution/{id}")
    public Solution getSolutionById(@PathVariable("id") Long solutionId){
        return this.service.findById(solutionId);
    }

    @PostMapping("/api/v1/solution/")
    public Solution correctTest(@RequestBody @Validated SolutionDTO solution,
                                @PathParam("testID") Long testId, @PathParam("userId") Long userId){

        return this.service.correctTest(solution, testId, userId);
    }

    @DeleteMapping("/api/v1/solution/{id}")
    public void deleteSolution(@PathVariable("id") Long solutionId){
        this.service.deleteSolution(solutionId);
    }

}
