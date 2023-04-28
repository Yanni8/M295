package dev.ynnk.m295.controller;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.conf.security.Roles;
import dev.ynnk.m295.helper.serializer.View;
import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.groups.Default;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@EnableWebSecurity
@SecurityRequirement(name = "bearerAuth")
public class GroupController {

    public final GroupService service;

    public GroupController(GroupService groupService) {
        this.service = groupService;
    }

    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/group")
    public List<Group> getAllGroups() {
        return this.service.getAllGroups();
    }

    @Operation(summary = "Get more details by a group that you are a member. " +
            "You can only access groups that you are a member" +
            "You need to have a user with a username equal to your jwt username to access this endpoint")
    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/group/{id}")
    public Group getGroupById(@PathVariable("id") long id, @AuthenticationPrincipal Jwt jwt) {
        return this.service.findGroupByIdAndJwt(id, jwt);
    }


    @Operation(summary = "Get more details for any group (only admins can request this endpoint)")
    @RolesAllowed(Roles.ADMIN)
    @GetMapping("/api/v1/group/{id}/administrator")
    public Group getGroupById(@PathVariable("id") long id) {
        return this.service.getGroupById(id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PostMapping("/api/v1/group/")
    public Group createGroup(@RequestBody @Validated(value = {Default.class, Create.class})
                                 @JsonView({View.CreateGroup.class}) Group group) {
        return this.service.saveGroup(group);
    }

    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/group/{id}")
    public Group updateGroup(@RequestBody @Validated(value = {Default.class, Create.class})
                                 @JsonView({View.UpdateGroup.class}) Group group,
                             @PathVariable("id") Long id) {
        return this.service.updateGroup(group, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @PatchMapping("/api/v1/group/{id}")
    public Group patchGroup(@RequestBody @JsonView({View.UpdateGroup.class}) Group group, @PathVariable("id") Long id) {
        return this.service.patchGroup(group, id);
    }

    @RolesAllowed(Roles.ADMIN)
    @DeleteMapping("/api/v1/group/{id}")
    public void deleteGroup(@PathVariable("id") Long id) {
        this.service.deleteGroup(id);
    }

    @Operation(summary = "Can be used to add a user to a group")
    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/group/{id}/join")
    public Group joinGroup(@PathVariable("id") Long groupId,
                           @RequestParam(value = "userId", required = true) Long userId) {
        return this.service.joinGroup(groupId, userId);
    }

    @Operation(summary = "Can be used to remove a user from a group")
    @RolesAllowed(Roles.ADMIN)
    @PutMapping("/api/v1/group/{id}/leave")
    public Group leaveGroup(@PathVariable("id") Long groupId,
                            @RequestParam(value = "userId", required = true) Long userId) {
        return this.service.leaveGroup(groupId, userId);
    }

    @Operation(summary = "Get all the groups that you are member " +
            "You need to have a user with a username equal to your jwt username to access this endpoint")
    @RolesAllowed(Roles.USER)
    @GetMapping("/api/v1/group/whoami")
    public List<Group> getGroupsByJwt(@AuthenticationPrincipal Jwt oauth) {
        return this.service.findGroupsByJwt(oauth);
    }
}
