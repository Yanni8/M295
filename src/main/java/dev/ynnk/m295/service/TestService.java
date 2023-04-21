package dev.ynnk.m295.service;


import dev.ynnk.m295.helper.language.ErrorMessage;
import dev.ynnk.m295.helper.patch.AutoPatch;
import dev.ynnk.m295.model.Group;
import dev.ynnk.m295.model.Test;
import dev.ynnk.m295.repository.TestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    public TestService(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    public Test saveTest(Test test){
        if (test == null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    ErrorMessage.noObjectPresent("test"));
        }
        return this.testRepository.save(test);
    }

    public List<Test> getAllTests(){
        return this.testRepository.findAll();
    }

    public Test getTestById(long id){
        return this.testRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.notFoundById("test", id))
        );
    }

    public Test patchTest(Test test, long id){
        Test dbTest = this.getTestById(id);

        Test patchedTest = AutoPatch.patch(test, dbTest);

        if (patchedTest == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.support(513));
        }

        this.testRepository.save(patchedTest);

        return patchedTest;
    }

    public Test updateTest(Test test, long id){
        test.setId(id);
        this.getTestById(id);
        return this.testRepository.save(test);
    }

    public void deleteTest(long id){
        this.getTestById(id);
        this.testRepository.deleteById(id);
    }

}
