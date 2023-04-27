package dev.ynnk.m295.model;

import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.DocFlavor;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @DBPrefer
    @GeneratedValue
    private Long id;

    private String question;

    @OneToMany
    private Set<AnswerPossibilities> right;

    @OneToMany
    private Set<AnswerPossibilities> wrong;

}
