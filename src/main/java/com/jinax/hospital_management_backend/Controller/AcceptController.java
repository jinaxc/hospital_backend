package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Service.PatientService;
import com.jinax.hospital_management_backend.Service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author : chara
 */
@Api(tags = "AcceptController")
@RestController
@RequestMapping("/accept")
public class AcceptController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptController.class);

    private final TestService testService;
    private final PatientService patientService;

    public AcceptController(TestService testService, PatientService patientService) {
        this.testService = testService;
        this.patientService = patientService;
    }

    @ApiOperation("收治检测")
    @ResponseBody
    @PostMapping("/naTest")
    @PreAuthorize("hasRole('EMERGENCY_NURSE')")
    public Test naTest(boolean isPositive, int level){
        Test t = new Test();
        t.setResult(isPositive ? Test.Result.POSITIVE : Test.Result.NEGATIVE);
        t.setLevel(Test.Level.getLevel(level));
        return testService.addTest(t);
    }

    @ApiOperation("接收病人")
    @ResponseBody
    @PreAuthorize("hasRole('EMERGENCY_NURSE')")
    @PostMapping("/patient")
    public Patient acceptPatient(Patient patient){
        return patientService.addPatient(patient);
    }
}
