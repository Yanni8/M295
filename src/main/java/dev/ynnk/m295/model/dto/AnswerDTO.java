package dev.ynnk.m295.model.dto;

import dev.ynnk.m295.model.AnswerPossibilities;
import lombok.Data;

import java.util.Set;

@Data
public class AnswerDTO {

    private Long id;

    private Set<AnswerPossibilities> selected;

    private Set<AnswerPossibilities> notSelected;
}
