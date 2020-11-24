package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : chara
 */
@Service
public class TestService {
    private final TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    /**
     *
     * @param test Id and time field should be null(they will be reset by this method )
     * @return the id of the generated test
     */
    public Test addTest(Test test){
        test.setTestTime(null);
        test.setId(null);
        Test save = testRepository.save(test);
        return save;
    }


}
