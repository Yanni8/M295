package dev.ynnk.m295.service;

import dev.ynnk.m295.helper.language.ErrorMessage;
import dev.ynnk.m295.helper.patch.AutoPatch;
import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.User;
import dev.ynnk.m295.repository.GroupRepository;
import dev.ynnk.m295.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, UserService userService){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group saveGroup(Group group){
        if (group == null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    ErrorMessage.noObjectPresent("user"));
        }
        return this.groupRepository.save(group);
    }


    public Group getGroupById(long id){
        return this.groupRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessage.notFoundById("Group", id))
        );
    }

    public User addUserToGroup(User user, long groupId){

        if (user == null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    ErrorMessage.noObjectPresent("User"));
        }

        Group group = this.getGroupById(groupId);

        user.setGroup(group);

        return this.userRepository.save(user);
    }

    public void deleteGroup(long id){
        this.getGroupById(id);
        this.groupRepository.deleteById(id);
    }

    public Group patchGroup(Group group, long id){
        Group dbGroup = this.getGroupById(id);

        Group patchedGroup = AutoPatch.patch(group, dbGroup);

        if (patchedGroup == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.support(511));
        }

        this.groupRepository.save(patchedGroup);

        return patchedGroup;
    }

    public Group updateGroup(Group group, long id){
        group.setId(id);
        this.getGroupById(id);
        return this.groupRepository.save(group);
    }

    public List<Group> getAllGroups(){
        return this.groupRepository.findAll();
    }


    public Group joinGroup(long groupId, long userId){
        Group group = this.getGroupById(groupId);
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessage.notFoundById("User", groupId))
        );
        group.addUser(user);

        return this.groupRepository.save(group);
    }

    public Group leaveGroup(long groupId, long userId){
        Group group = this.getGroupById(groupId);
        Set<User> users = group.getUsers();

        users = users.stream().filter(user -> user.getId() != userId).collect(Collectors.toSet());
        group.setUsers(users);

        this.groupRepository.save(group);

        return group;
    }

}

