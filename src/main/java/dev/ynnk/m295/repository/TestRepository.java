package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
