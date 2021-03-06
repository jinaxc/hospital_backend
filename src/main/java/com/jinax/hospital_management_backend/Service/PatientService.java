package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.*;
import com.jinax.hospital_management_backend.Exception.AddPatientFailedException;
import com.jinax.hospital_management_backend.Exception.PatientNotExistedException;
import com.jinax.hospital_management_backend.Repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final TestRepository testRepository;
    private final DistrictRepository districtRepository;
    private final BedRepository bedRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;


    public PatientService(PatientRepository patientRepository, TestRepository testRepository, DistrictRepository districtRepository, BedRepository bedRepository, UserRepository userRepository, MessageService messageService) {
        this.patientRepository = patientRepository;
        this.testRepository = testRepository;
        this.districtRepository = districtRepository;
        this.bedRepository = bedRepository;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }

    public Patient getPatient(long patientId) throws PatientNotExistedException {
        Optional<Patient> byId = patientRepository.findById(patientId);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new PatientNotExistedException("patient not exist, id is " + patientId);
    }


    public List<Long> getPatients(Long districtId, Boolean canLeave, Integer state, Integer level){
        List<Long> allByDistrictIdAndLevelAndState = patientRepository.
                findAllByDistrictIdAndLevelAndState(districtId, Patient.Level.getLevel(level), DailyReport.State.getState(state));
        if (canLeave != null && canLeave) {
            List<Long> allWithThreeGoodTemperature = patientRepository.findAllWithThreeGoodTemperature();
            List<Long> allWithTwoNegativeTests = patientRepository.findAllWithTwoNegativeTests();
            allWithThreeGoodTemperature.retainAll(allWithTwoNegativeTests);
            allByDistrictIdAndLevelAndState.retainAll(allWithThreeGoodTemperature);
        }
        return allByDistrictIdAndLevelAndState;
    }

    public List<Long> getPatientsThatCanLeave(){
        return getPatients(null,true,null,null);
    }

    public List<Patient> getPatientsByNurseId(long nurseId){
        return patientRepository.findAllByNurseId(nurseId);
    }

    /**
     * needs to allocate the patient to a correct district
     * @param patient the related fields should be null
     * @return
     */
    @Transactional
    public Patient addPatient(Patient patient) throws AddPatientFailedException {
        if(!testRepository.findById(patient.getInitialTestId()).isPresent()){
            throw new AddPatientFailedException("测试不存在");
        }
        Patient.Level level = patient.getLevel();
        List<District> byType = districtRepository.findAllByType(District.Type.valueOf(level.name()));
        for(District district : byType){
            List<Long> emptyBedInGivenDistrict = bedRepository.findEmptyBedInGivenDistrict(district.getId());
            List<Long> freeWardNurseInGivenDistrict = userRepository.findFreeWardNurseInGivenDistrict(district.getId(), 4 - district.getType().getCode());//TODO
            if(emptyBedInGivenDistrict.isEmpty() || freeWardNurseInGivenDistrict.isEmpty()){
                continue;
            }
            Long bedId = emptyBedInGivenDistrict.get(0);
            Long nurseId = freeWardNurseInGivenDistrict.get(0);
            patient.setBedId(bedId);
            patient.setDistrictId(district.getId());
            patient.setNurseId(nurseId);
            Patient save = patientRepository.save(patient);
            Optional<User> byDistrictIdEqualsAndRoleChiefNurse = userRepository.findByDistrictIdEqualsAndRoleChiefNurse(patient.getDistrictId());
            Message message = new Message();
            message.setType(Message.Type.PATIENT_COME);
            message.setData("病人 " + save.getId() + " 转移进了你负责的区域");
            message.setTargetId(byDistrictIdEqualsAndRoleChiefNurse.get().getId());
            messageService.addMessage(message);
            return save;
        }
        List<District> allByType = districtRepository.findAllByType(District.Type.ISOLATION);
        patient.setDistrictId(allByType.get(0).getId());
        return patientRepository.save(patient);
    }

    public boolean isPatientInMinorDistrict(long patientId){
        Optional<District.Type> districtOfPatient = patientRepository.getDistrictOfPatient(patientId);
        if(districtOfPatient.isPresent()){
            throw new PatientNotExistedException("patient not exist, id is " + patientId);
        }else{
            return districtOfPatient.get() == District.Type.MINOR;
        }
    }
}
