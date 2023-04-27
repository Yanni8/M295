package dev.ynnk.m295.service;


import dev.ynnk.m295.helper.language.ErrorMessage;
import dev.ynnk.m295.helper.patch.AutoPatch;
import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.Test;
import dev.ynnk.m295.model.User;
import dev.ynnk.m295.repository.TestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final GroupService groupService;

    private final UserService userService;

    public TestService(TestRepository testRepository, UserService userService, GroupService groupService) {
        this.testRepository = testRepository;
        this.userService = userService;
        this.groupService = groupService;
    }

    public Test saveTest(Test test) {
        if (test == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    ErrorMessage.noObjectPresent("test"));
        }
        return this.testRepository.save(test);
    }

    public List<Test> getAllTests() {
        return this.testRepository.findAll();
    }

    public Test getTestById(long id) {
        return this.testRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.notFoundById("test", id))
        );
    }


    public Test getTestByIdAndJwt(long id, Jwt jwt){
        String username = jwt.getClaimAsString("preferred_username");
        return this.testRepository.findByIdAndUsersUsernameOrIdAndGroupsUsersUsername(id,username, id, username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "Your not allowed to access other once resources")
        );
    }

    public Test patchTest(Test test, long id) {
        Test dbTest = this.getTestById(id);

        Test patchedTest = AutoPatch.patch(test, dbTest);

        if (patchedTest == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.support(513));
        }

        this.testRepository.save(patchedTest);

        return patchedTest;
    }

    public Test updateTest(Test test, long id) {
        test.setId(id);
        this.getTestById(id);
        return this.testRepository.save(test);
    }

    public void deleteTest(long id) {
        this.getTestById(id);
        this.testRepository.deleteById(id);
    }

    protected Test inviteGroup(long id, Test test) {
        Group group = this.groupService.getGroupById(id);
        test.setGroup(group);
        return this.testRepository.save(test);
    }

    protected Test inviteUser(long id, Test test) {
        User user = this.userService.getUserById(id);
        test.setUser(user);
        return this.testRepository.save(test);
    }

    public Test invite(long testId, long id, String type) {

        Test test = this.getTestById(testId);

        type = type.toLowerCase(Locale.ENGLISH);
        if (type.equals("group")) {
            return inviteGroup(id, test);
        } else if (type.equals("user")) {
            return inviteUser(id, test);
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                "The URL parameter 'type' needs to be equals to 'group' or 'user'");
    }


    protected Test uninviteUser(long id, Test test) {
        test.setUsers(test.getUsers().stream().filter(
                user -> user.getId() != id).collect(Collectors.toSet()));
        return this.testRepository.save(test);
    }

    protected Test uninviteGroup(long id, Test test) {
        test.setGroups(test.getGroups().stream().filter(
                group -> group.getId() != id).collect(Collectors.toSet()));
        return this.testRepository.save(test);
    }

    public Test uninvite(long testId, long id, String type) {

        Test test = this.getTestById(testId);

        type = type.toLowerCase(Locale.ENGLISH);
        if (type.equals("group")) {
            return this.uninviteGroup(id, test);
        } else if (type.equals("user")) {
            return this.uninviteUser(id, test);
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                "The URL parameter 'type' needs to be equals to 'group' or 'user'");
    }

    public List<Test> getTestMetdataByJwt(Jwt jwt){
        Long userId = this.userService.getUserByJwt(jwt).getId();
        List<Test> tests = this.testRepository.findByUsersIdOrGroupsUsersId(userId,userId);
        return this.testRepository.findByUsersIdOrGroupsUsersId(userId,userId);
    }

}
