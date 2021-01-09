package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Entity.Patient;

import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Service.DailyReportService;
import com.jinax.hospital_management_backend.Service.PatientService;
import com.jinax.hospital_management_backend.Service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chara
 */
@Api(tags = "PatientController")
@RestController
@RequestMapping("/patient")
public class PatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);
    private final PatientService patientService;
    private final DailyReportService dailyReportService;
    private final TestService testService;

    public PatientController(PatientService patientService, DailyReportService dailyReportService, TestService testService) {
        this.patientService = patientService;
        this.dailyReportService = dailyReportService;
        this.testService = testService;
    }

    @ApiOperation("获取病人信息")
    @ResponseBody
    @GetMapping("/{id}")
    public Patient getPatientInfo(@PathVariable("id") long patientId){
        return patientService.getPatient(patientId);
    }


    @ApiOperation("获取病人的每日状态信息")
    @ResponseBody
    @GetMapping("/dailyReport/{patient_id}")
    public Map<String, List<Long>> getDailyByPatientId(@PathVariable("patient_id") long patientId){
        Map<String, List<Long>> result = new HashMap<>();
        result.put("data",dailyReportService.findByPatientId(patientId));
        return result;
    }

    @ApiOperation("获取病人的所有检测单信息")
    @ResponseBody
    @GetMapping("/naTest/{patient_id}")
    public List<Test> getTestsByPatientId(@PathVariable("patient_id") long patientId){
        LOGGER.info("get tests by patientId, id is {}",patientId);
        return testService.getAllTestsByPatientId(patientId);
    }

}
