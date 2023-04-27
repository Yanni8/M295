package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.User;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {


    List<Group> findByUsersId(Long id);
}
