package dev.ynnk.m295.model.dto;

import dev.ynnk.m295.model.Answer;
import lombok.Data;

import java.util.Set;

@Data
public class SolutionDTO {

    private Set<AnswerDTO> answers;

}
