package dev.ynnk.m295.repository;

import dev.ynnk.m295.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
