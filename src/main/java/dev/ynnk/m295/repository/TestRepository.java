package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByUsersIdOrGroupsUsersId(Long userId, Long groupIds);

    Optional<Test> findByIdAndUsersUsernameOrIdAndGroupsUsersUsername(
            long id, String username, long id2,String username2);

}
