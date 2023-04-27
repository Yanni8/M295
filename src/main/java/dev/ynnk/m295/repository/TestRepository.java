package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByUsersIdOrGroupsUsersId(Long userId, Long groupIds);

}
