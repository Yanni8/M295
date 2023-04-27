package dev.ynnk.m295.service;

import dev.ynnk.m295.helper.language.ErrorMessage;
import dev.ynnk.m295.model.*;
import dev.ynnk.m295.model.dto.AnswerDTO;
import dev.ynnk.m295.model.dto.SolutionDTO;
import dev.ynnk.m295.repository.SolutionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    private final TestService testService;
    private final UserService userService;
    private final SolutionRepository solutionRepository;

    public SolutionService(SolutionRepository solutionRepository, TestService testService, UserService userService) {
        this.solutionRepository = solutionRepository;
        this.testService = testService;
        this.userService = userService;
    }

    public List<Solution> getAllSolutions() {
        return this.solutionRepository.findAll();
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
        answer.setQuestion(question.getQuestion());
        answer.setRight(question.getAnswerPossibilities().stream().filter(
                answerPossibilities -> checkIfTrue(answerPossibilities, answerDTO)
        ).collect(Collectors.toSet()));


        answer.setWrong(question.getAnswerPossibilities().stream().filter(
                answerPossibilities -> !checkIfTrue(answerPossibilities, answerDTO)
        ).collect(Collectors.toSet()));

        return answer;
    }

    public Solution correctTest(SolutionDTO solutionDTO, long testId, long userId, Jwt jwt) {
        Test test = this.testService.getTestById(testId);

        Solution solution = new Solution();

        solution.setUser(this.userService.getUserById(userId));

        if (!solution.getUser().getUsername().equals(jwt.getClaimAsString("preferred_username"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You are only allowed to solve a test for your own user");
        }

        solution.setTemplateTest(test);

        solutionDTO.getAnswers().forEach(
                answerDTO -> solution.setAnswer(correctAnswer(answerDTO, test))
        );

        this.solutionRepository.save(solution);

        return solution;
    }

    public Solution findById(long id) {
        return this.solutionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorMessage.notFoundById("solution", id))
        );
    }


    public void deleteSolution(long id) {
        this.findById(id);
        this.solutionRepository.deleteById(id);
    }
}
