package dev.ynnk.m295.controller;

import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.Test;
import dev.ynnk.m295.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.groups.Default;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableWebSecurity
@SecurityRequirement(name = "bearerAuth")
public class TestController {



    private final TestService service;

    public TestController(TestService service){
        this.service = service;
    }


    @GetMapping("/api/v1/test")
    public List<Test> getAllTests(){
        return this.service.getAllTests();
    }


    @GetMapping("/api/v1/test/{id}")
    public Test getTestById(@PathVariable("id") Long id){
        return this.service.getTestById(id);
    }

    @PostMapping("/api/v1/test")
    public Test createTest(@RequestBody @Validated(value = {Default.class, Create.class}) Test test){
        return this.service.saveTest(test);
    }

    @PatchMapping("/api/v1/test/{id}")
    public Test patchTest(@RequestBody Test partialTest, @PathVariable("id") Long id){
        System.out.println("Got Here");
        return this.service.patchTest(partialTest,id);
    }

    @PutMapping("/api/v1/test/{id}")
    public Test updateTest(@RequestBody @Validated(value = {Default.class, Create.class}) Test test,
                           @PathVariable("id") Long id){
        return this.service.updateTest(test, id);
    }

    @DeleteMapping("/api/v1/test/{id}")
    public void deleteTest(@PathVariable("id") Long id){
        this.service.deleteTest(id);
    }

    @PutMapping("/api/v1/test/{id}/invite")
    public Test inviteTest(@PathVariable("id") Long testId, @RequestParam("id") Long id, @RequestParam(value = "type",
            defaultValue = "user") String type){
        return this.service.invite(testId, id, type);
    }


    @PutMapping("/api/v1/test/{id}/uninvite")
    public Test uninviteTest(@PathVariable("id") Long testId, @RequestParam("id") Long id, @RequestParam(value = "type",
            defaultValue = "user") String type){
        return this.service.uninvite(testId, id, type);
    }

}
