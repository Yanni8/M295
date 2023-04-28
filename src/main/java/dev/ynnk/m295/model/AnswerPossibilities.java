package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
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

    @Id
    @GeneratedValue
    @DBPrefer
    @JsonView(View.Metadata.class)
    private Long id;

    @NotNull
    @JsonView(View.Metadata.class)
    private String answer;


    @NotNull
    @JsonView(View.Hide.class)
    private boolean correctAnswer;

    @JsonIgnore
    public boolean isCorrectAnswer() {
        return this.correctAnswer;
    }
}
