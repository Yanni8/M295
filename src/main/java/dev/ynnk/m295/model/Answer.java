package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
import jakarta.persistence.*;
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

    @JsonView(View.Metadata.class)
    private String question;

    @ManyToMany
    @JsonView(View.Metadata.class)
    private Set<AnswerPossibilities> right;

    @ManyToMany
    @JsonView(View.Metadata.class)
    private Set<AnswerPossibilities> wrong;

}
