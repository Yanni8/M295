package dev.ynnk.m295.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import dev.ynnk.m295.helper.serializer.View;
import lombok.Data;

import java.util.Set;

@Data
public class SolutionDTO {

    @JsonView(View.Public.class)
    private Set<AnswerDTO> answers;

}
