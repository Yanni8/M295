package dev.ynnk.m295.model;

import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany
    private Set<AnswerPossibilities> right;

    @OneToMany
    private Set<AnswerPossibilities> wrong;

}
