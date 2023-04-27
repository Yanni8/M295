package dev.ynnk.m295.service;

import dev.ynnk.m295.helper.language.ErrorMessage;
import dev.ynnk.m295.model.*;
import dev.ynnk.m295.model.dto.AnswerDTO;
import dev.ynnk.m295.model.dto.SolutionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class SolutionService {

    private final TestService testService;
    private final UserService userService;


    public SolutionService(TestService testService, UserService userService) {
        this.testService = testService;
        this.userService = userService;
    }

    private boolean checkIfTrue(AnswerPossibilities expected, AnswerDTO answerDTO) {
        boolean isSelected = false;
        long searchedId = expected.getId();
        for (AnswerPossibilities answerPossibilities : answerDTO.getSelected()) {
            if (answerPossibilities.getId() == searchedId) {
                isSelected = true;
                break;
            }
        }

        if (expected.isCorrectAnswer()) {
            return isSelected;
        }
        return !isSelected;
    }

    private Answer correctAnswer(AnswerDTO answerDTO, Test test) {
        Long id = answerDTO.getId();

        Question question = test.getQuestions().stream().filter(
                questionFilter -> questionFilter.getId() == id).findFirst().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessage.notFoundById("user", id))
        );

        Answer answer = new Answer();

        answer.setRight(question.getAnswerPossibilities().stream().filter(
                answerPossibilities -> checkIfTrue(answerPossibilities, answerDTO)
        ).collect(Collectors.toSet()));


        answer.setWrong(question.getAnswerPossibilities().stream().filter(
                answerPossibilities -> !checkIfTrue(answerPossibilities, answerDTO)
        ).collect(Collectors.toSet()));

        return answer;
    }

    public Solution correctTest(SolutionDTO solutionDTO, long testId, long userId) {
        Test test = this.testService.getTestById(testId);

        Solution solution = new Solution();

        solution.setUser(this.userService.getUserById(userId));

        solution.setTemplateTest(test);

        solutionDTO.getAnswers().forEach(
                answerDTO -> solution.setAnswer(correctAnswer(answerDTO, test))
        );

        return solution;
    }
}