package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Exception.TestNotExistedException;
import com.jinax.hospital_management_backend.Repository.DailyReportRepository;
import com.jinax.hospital_management_backend.Repository.TestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class TestService {
    private final TestRepository testRepository;
    private final DailyReportRepository dailyReportRepository;
    private final PatientService patientService;

    public TestService(TestRepository testRepository, DailyReportRepository dailyReportRepository, PatientService patientService) {
        this.testRepository = testRepository;
        this.dailyReportRepository = dailyReportRepository;
        this.patientService = patientService;
    }

    /**
     *
     * @param test Id and time field should be null(they will be reset by this method )
     * @return the id of the generated test
     */
    @Transactional
    public Test addTest(Test test){
        test.setId(null);
        return testRepository.save(test);
    }

    public Test updateTest(long testId,int level){
        Test t = getTest(testId);
        t.setLevel(Test.Level.getLevel(level));
        return testRepository.save(t);
    }


    public Test getTest(long testId) throws TestNotExistedException {
        Optional<Test> byId = testRepository.findById(testId);
        if(byId.isPresent()){
            return byId.get();
        }else{
            throw new TestNotExistedException("test not exist, id is " + testId);
        }
    }


}
