package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Component.MyUserDetails;
import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Service.BedService;
import com.jinax.hospital_management_backend.Service.PatientService;
import com.jinax.hospital_management_backend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictController.class);
    private final UserService userService;
    private final PatientService patientService;
    private final BedService bedService;

    public DistrictController(UserService userService, PatientService patientService, BedService bedService) {
        this.userService = userService;
        this.patientService = patientService;
        this.bedService = bedService;
    }

    @ApiOperation("查找病人信息")
    @ResponseBody
    @GetMapping("/patient")
    public Map<String,List<Long>> getPatients(Long districtId,Boolean canLeave,Integer state,Integer level){
        LOGGER.info("getPatients,district : {},canLeave : {},state : {},level : {}",districtId,canLeave,state,level);
        List<Long> patients = patientService.getPatients(districtId, canLeave, state, level);
        Map<String,List<Long>> result = new HashMap<>();
        result.put("data",patients);
        return result;
    }

    @ApiOperation("获取护士长信息")
    @ResponseBody
    @GetMapping("/headNurse")
    @PreAuthorize("hasAnyAuthority('DOCTOR','CHIEF_NURSE')")
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
    @PreAuthorize("hasAnyAuthority('DOCTOR','CHIEF_NURSE')")
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
    @PreAuthorize("hasAnyAuthority('DOCTOR','CHIEF_NURSE')")
    public Map<String,List<Long>> getPatientIdsByNurseId(@PathVariable("nurseId") long nurseId){
        Map<String,List<Long>> result = new HashMap<>();
        List<Patient> patientsByNurseId = patientService.getPatientsByNurseId(nurseId);
        List<Long> patientIds = patientsByNurseId.stream().map(Patient::getId).collect(Collectors.toList());
        result.put("patientIds",patientIds);
        return result;
    }

    @ApiOperation("获取当前区域内病床对应病人信息")
    @ResponseBody
    @GetMapping("/bedPatient")
    @PreAuthorize("hasAuthority('CHIEF_NURSE')")
    public List<Map<Long,Long>> getBed2Patients(){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long districtId = details.getDistrictId();
        return bedService.findAllBedWithPatientIdInGivenDistrict(districtId);
    }

    @ApiOperation("获取当前区域内病床对应病人名字信息")
    @ResponseBody
    @GetMapping("/bedPatientName")
    @PreAuthorize("hasAuthority('CHIEF_NURSE')")
    public List<Map<Long,String>> getBed2PatientNames(){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long districtId = details.getDistrictId();
        return bedService.findAllBedWithPatientNameInGivenDistrict(districtId);
    }

    @ApiOperation("增加病房护士")
    @ResponseBody
    @PutMapping("/roomNurse/{nurseId}")
    @PreAuthorize("hasAuthority('CHIEF_NURSE')")
    public ResponseEntity<Map<String, Long>> addRoomNurse(@PathVariable("nurseId") Long nurseId){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long districtId = details.getDistrictId();
        if(!userService.setWardNurseToDistrict(nurseId,districtId)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }else{
            Map<String, Long> result = new HashMap<>();
            result.put("data",nurseId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @ApiOperation("删除病房护士")
    @ResponseBody
    @DeleteMapping("/roomNurse/{nurseId}")
    @PreAuthorize("hasAuthority('CHIEF_NURSE')")
    public ResponseEntity<Map<String, Long>> removeRoomNurse(@PathVariable("nurseId") Long nurseId){
        if(!userService.removeWardNurseFromDistrict(nurseId)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }else{
            Map<String, Long> result = new HashMap<>();
            result.put("data",nurseId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

}

