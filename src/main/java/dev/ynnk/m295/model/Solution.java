package dev.ynnk.m295.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ynnk.m295.helper.patch.DBPrefer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @DBPrefer
    @GeneratedValue
    private Long id;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private transient Test templateTest;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Answer> answers;

    public void setAnswer(Answer answer) {
        if (this.answers == null){
            this.answers = new HashSet<>();
        }
        this.answers.add(answer);
    }

    @JsonIgnore
    public Test getTemplateTest() {
        return templateTest;
    }
}
