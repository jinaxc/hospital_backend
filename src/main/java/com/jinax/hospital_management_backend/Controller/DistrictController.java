package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Component.MyUserDetails;
import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Service.PatientService;
import com.jinax.hospital_management_backend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : chara
 */
@Api(tags = "DistrictController")
@RestController
@RequestMapping("/district")
public class DistrictController {

    private final UserService userService;
    private final PatientService patientService;

    public DistrictController(UserService userService, PatientService patientService) {
        this.userService = userService;
        this.patientService = patientService;
    }

//    public Map<String, List<Long>>


    @ApiOperation("获取护士长信息")
    @ResponseBody
    @GetMapping("/headNurse")
    public Map<String,Long> getChiefNurse(){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Long> result = new HashMap<>();
        long districtId = details.getDistrictId();
        User chiefNurseByDistrictId = userService.findChiefNurseByDistrictId(districtId);
        result.put("nurseId",chiefNurseByDistrictId.getId());
        return result;
    }

    @ApiOperation("获取病房护士的信息")
    @ResponseBody
    @GetMapping("/roomNurse")
    public Map<String,List<Long>> getRoomNurses(){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,List<Long>> result = new HashMap<>();
        long districtId = details.getDistrictId();
        List<User> chiefNurseByDistrictId = userService.findWardNurseByDistrictId(districtId);
        List<Long> nurseIds = chiefNurseByDistrictId.stream().map(User::getId).collect(Collectors.toList());
        result.put("nurseId",nurseIds);
        return result;
    }

    @ApiOperation("获取病房护士照顾的病人的信息")
    @ResponseBody
    @GetMapping("/{nurseId}")
    @PreAuthorize("hasAnyRole('DOCTOR','CHIEF_NURSE')")
    public Map<String,List<Long>> getPatientIdsByNurseId(@PathVariable("nurseId") long nurseId){
        Map<String,List<Long>> result = new HashMap<>();
        List<Patient> patientsByNurseId = patientService.getPatientsByNurseId(nurseId);
        List<Long> patientIds = patientsByNurseId.stream().map(Patient::getId).collect(Collectors.toList());
        result.put("patientIds",patientIds);
        return result;
    }

    @ApiOperation("获取一个区域内病床对应病人信息")
    @ResponseBody
    @PutMapping("/bedPatient")
    @PreAuthorize("hasRole('CHIEF_NURSE')")
    public Map<String,List<Long>> getBed2Patients(){
//        patientService.getPatientsByNurseId();

        return null;
    }
}
