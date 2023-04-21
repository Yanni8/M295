package dev.ynnk.m295.model;

import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPossibilities {

    @GeneratedValue
    @Id
    @DBPrefer
    @NotNull
    private Long id;

    @NotNull
    private String answer;

    @NotNull
    private boolean correctAnswer;

}
