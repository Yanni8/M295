package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;


    @NotNull
    @NotEmpty
    private String question;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<AnswerPossibilities> answerPossibilities;

}
