package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.patch.DBPrefer;
import dev.ynnk.m295.helper.serializer.View;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
public class Solution {

    @Id
    @DBPrefer
    @GeneratedValue
    @JsonView(View.SolutionMetadata.class)
    private Long id;

    @ManyToOne()
    @JsonView(View.Public.class)
    private User user;

    @ManyToOne
    @JsonView(View.Public.class)
    private Test templateTest;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonView(View.Public.class)
    private Set<Answer> answers;

    public void setAnswer(Answer answer) {
        if (this.answers == null) {
            this.answers = new HashSet<>();
        }
        this.answers.add(answer);
    }

    @JsonIgnore
    public Test getTemplateTest() {
        return templateTest;
    }
}
