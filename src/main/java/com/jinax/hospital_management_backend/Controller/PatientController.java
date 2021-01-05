package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Entity.Patient;

import com.jinax.hospital_management_backend.Service.DailyReportService;
import com.jinax.hospital_management_backend.Service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    private final PatientService patientService;
    private final DailyReportService dailyReportService;

    public PatientController(PatientService patientService, DailyReportService dailyReportService) {
        this.patientService = patientService;
        this.dailyReportService = dailyReportService;
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

}
