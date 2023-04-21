package dev.ynnk.m295.model;

import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue
    @DBPrefer
    @NotNull
    private Long id;


    @NotNull
    @NotEmpty
    private String question;


    @OneToMany
    private Set<AnswerPossibilities> answerPossibilities;



}
