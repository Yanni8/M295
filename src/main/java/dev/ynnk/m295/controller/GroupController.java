package dev.ynnk.m295.controller;

import dev.ynnk.m295.conf.security.Roles;
import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.User;
import dev.ynnk.m295.service.GroupService;
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
public class GroupController {

    public final GroupService service;

    public GroupController(GroupService groupService) {
        this.service = groupService;
    }

    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/group")
    public List<Group> getAllGroups() {
        return this.service.getAllGroups();
    }

    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/group/{id}")
    public Group getGroupById(@PathVariable("id") long id) {
        return this.service.getGroupById(id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PostMapping("/api/v1/group/")
    public Group createGroup(@RequestBody @Validated(value = {Default.class, Create.class}) Group group) {
        return this.service.saveGroup(group);
    }
    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/group/{id}")
    public Group updateGroup(@RequestBody @Validated(value = {Default.class, Create.class}) Group group,
                             @PathVariable("id") Long id) {
        return this.service.updateGroup(group, id);
    }
    @RolesAllowed(Roles.ADMIN)
    @PatchMapping("/api/v1/group/{id}")
    public Group patchGroup(@RequestBody Group group, @PathVariable("id") Long id) {
        return this.service.patchGroup(group, id);
    }
    @RolesAllowed(Roles.ADMIN)
    @DeleteMapping("/api/v1/group/{id}")
    public void deleteGroup(@PathVariable("id") Long id) {
        this.service.deleteGroup(id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/group/{id}/join")
    public Group joinGroup(@PathVariable("id") Long groupId,
                           @RequestParam(value = "userId", required = true) Long userId){
        return this.service.joinGroup(groupId, userId);
    }

    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/group/{id}/leave")
    public Group leaveGroup(@PathVariable("id") Long groupId,
                            @RequestParam(value = "userId", required = true) Long userId){
        return this.service.leaveGroup(groupId, userId);
    }

    @GetMapping("/api/v1/group/whoami")
    public List<Group> getGroupsByJwt(@AuthenticationPrincipal Jwt oauth){
        return this.service.findGroupsByJwt(oauth);
    }
}
