package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Entity.Patient;

import com.jinax.hospital_management_backend.Service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author : chara
 */
@Api(tags = "PatientController")
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation("获取病人信息")
    @ResponseBody
    @GetMapping("/{id}")
    public Patient getPatientInfo(@PathVariable("id") long patientId){
        return patientService.getPatient(patientId);
    }
}
