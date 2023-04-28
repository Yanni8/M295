package dev.ynnk.m295.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.serializer.View;
import dev.ynnk.m295.model.AnswerPossibilities;
import lombok.Data;

import java.util.Set;

@Data
public class AnswerDTO {

    @JsonView(View.Public.class)
    private Long id;

    @JsonView(View.Public.class)
    private Set<AnswerPossibilities> selected;

    @JsonView(View.Public.class)
    private Set<AnswerPossibilities> notSelected;
}
