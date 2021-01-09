package com.jinax.hospital_management_backend;

import com.jinax.hospital_management_backend.Entity.DailyReport;

import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Repository.DistrictRepository;
import com.jinax.hospital_management_backend.Repository.PatientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HospitalManagementBackendApplicationTests {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DistrictRepository districtRepository;


    @Test
    void contextLoads() {

//        List<District> allByType = districtRepository.findAllByType(District.Type.MAJOR);
        List<Long> allByDistrictIdAndLevelAndState = patientRepository.findAllByDistrictIdAndLevelAndState((long) 2, Patient.Level.MAJOR,DailyReport.State.IN);
//        List<Long> allByDistrictIdAndLevelAndState = patientRepository.findAllByDistrictIdAndLevelAndState((long) 2, Patient.Level.MAJOR, DailyReport.State.IN);
        System.out.println(allByDistrictIdAndLevelAndState.size());
//        userRepository.findByDistrictIdEqualsAndRoleEquals(1, User.roleType.CHIEF_NURSE);
    }

}
