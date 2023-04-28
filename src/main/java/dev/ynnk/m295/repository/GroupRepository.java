package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByUsersId(Long id);

    Optional<Group> findByIdAndUsersId(Long id, Long userId);
}
