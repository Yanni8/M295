package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Question {

    @Id
    @GeneratedValue
    @DBPrefer
    private Long id;


    @NotNull
    @NotEmpty
    @JsonView(View.QuestionMetadata.class)
    private String question;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<AnswerPossibilities> answerPossibilities;

}
