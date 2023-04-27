package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
public class Answer {

    @Id
    @DBPrefer
    @GeneratedValue
    private Long id;

    @JsonView(View.AnswerMetadata.class)
    private String question;

    @OneToMany
    @JsonView(View.AnswerMetadata.class)
    private Set<AnswerPossibilities> right;

    @OneToMany
    @JsonView(View.AnswerMetadata.class)
    private Set<AnswerPossibilities> wrong;

}
