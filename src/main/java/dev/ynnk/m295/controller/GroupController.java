package dev.ynnk.m295.controller;

import dev.ynnk.m295.helper.validation.Create;
import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.service.GroupService;
import jakarta.validation.groups.Default;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    public final GroupService service;

    public GroupController(GroupService groupService) {
        this.service = groupService;
    }

    @GetMapping("/api/v1/group")
    public List<Group> getAllGroups() {
        return this.service.getAllGroups();
    }

    @GetMapping("/api/v1/group/{id}")
    public Group getGroupById(@PathVariable("id") long id) {
        return this.service.getGroupById(id);
    }

    @PostMapping("/api/v1/group/")
    public Group createGroup(@RequestBody @Validated(value = {Default.class, Create.class}) Group group) {
        return this.service.saveGroup(group);
    }

    @PutMapping("/api/v1/group/{id}")
    public Group updateGroup(@RequestBody @Validated(value = {Default.class, Create.class}) Group group,
                             @PathVariable("id") Long id) {
        return this.service.updateGroup(group, id);
    }

    @PatchMapping("/api/v1/group/{id}")
    public Group patchGroup(@RequestBody Group group, @PathVariable("id") Long id) {
        return this.service.patchGroup(group, id);
    }

    @DeleteMapping("/api/v1/group/{id}")
    public void deleteGroup(@PathVariable("id") Long id) {
        this.deleteGroup(id);
    }

    @PutMapping("/api/v1/group/{id}/join")
    public Group joinGroup(@PathVariable("id") Long groupId,
                           @RequestParam(value = "userId", required = true) Long userId){
        return this.service.joinGroup(groupId, userId);
    }

    @PutMapping("/api/v1/group/{id}/leave")
    public Group leaveGroup(@PathVariable("id") Long groupId,
                            @RequestParam(value = "userId", required = true) Long userId){
        return this.service.leaveGroup(groupId, userId);
    }
}
