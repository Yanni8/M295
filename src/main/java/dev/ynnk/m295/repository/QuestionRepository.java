package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.Question;
import dev.ynnk.m295.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
