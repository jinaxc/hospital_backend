package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.Message;
import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Exception.DailyNotExistedException;
import com.jinax.hospital_management_backend.Exception.ReleasePatientNotInMinorException;
import com.jinax.hospital_management_backend.Repository.DailyReportRepository;
import com.jinax.hospital_management_backend.Repository.PatientRepository;
import com.jinax.hospital_management_backend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class DailyReportService {
    private final DailyReportRepository reportRepository;
    private final PatientRepository patientRepository;
    private final PatientService patientService;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public DailyReportService(DailyReportRepository reportRepository, PatientRepository patientRepository, PatientService patientService, UserRepository userRepository, MessageService messageService) {
        this.reportRepository = reportRepository;
        this.patientRepository = patientRepository;
        this.patientService = patientService;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }


    public DailyReport findById(long dailyId){
        Optional<DailyReport> byId = reportRepository.findById(dailyId);
        if(byId.isPresent()){
            return byId.get();
        }else{
            throw new DailyNotExistedException("test not exist, id is " + dailyId);
        }
    }

    /**
     * besides remove patient, this method should automatically introduce patients from other isolation district if the patient is released
     * @param dailyId dailyId
     * @param level level
     * @return the dailyUpdated
     */
    @Transactional
    public DailyReport updateDaily(long dailyId, int level){
        DailyReport d = findById(dailyId);
        Long patientId = d.getPatientId();
        if(level == DailyReport.State.OUT.getCode() && patientService.isPatientInMinorDistrict(patientId)){
            d.setState(DailyReport.State.getState(level));
            Patient patient = patientRepository.findById(patientId).get();
            //move a new patient from isolation
            List<Patient> patientInIsolationWithLevel = patientRepository.getPatientInIsolationWithLevel(Patient.Level.MINOR);
            if(!patientInIsolationWithLevel.isEmpty()){
                Patient pNew = patientInIsolationWithLevel.get(0);
                pNew.setNurseId(patient.getNurseId());
                pNew.setDistrictId(patient.getDistrictId());
                pNew.setBedId(patient.getBedId());
                // must reset the patient info
                patient.setNurseId(null);
                patient.setDistrictId(null);
                patient.setBedId(null);
                patientRepository.save(patient);
                patientRepository.save(pNew);

                //add message
                Optional<User> byDistrictIdEqualsAndRoleChiefNurse = userRepository.findByDistrictIdEqualsAndRoleChiefNurse(patient.getDistrictId());
                Message message = new Message();
                message.setType(Message.Type.PATIENT_COME);
                message.setData("病人 " + pNew.getId() + " 转移进了你负责的区域");
                message.setTargetId(byDistrictIdEqualsAndRoleChiefNurse.get().getId());
                messageService.addMessage(message);
            }else{
                // must reset the patient info
                patient.setNurseId(null);
                patient.setDistrictId(null);
                patient.setBedId(null);
                patientRepository.save(patient);
            }
            return reportRepository.save(d);
        }else if(level != DailyReport.State.OUT.getCode() ){
            d.setState(DailyReport.State.getState(level));
            return reportRepository.save(d);
        }
        else{
            throw new ReleasePatientNotInMinorException("try to release patient not in MINOR");
        }

    }

    /**
     * needs to check if the patient can leave and send message
     * @param dailyReport dailyReport
     * @return DailyReport
     */
    public DailyReport addDailyReport(DailyReport dailyReport){
        dailyReport.setId(null);
        DailyReport save = reportRepository.save(dailyReport);
        List<Long> patientsThatCanLeave = patientService.getPatientsThatCanLeave();
        if(patientsThatCanLeave.contains(save.getPatientId())){
            Patient patient = patientService.getPatient(save.getPatientId());
            Message message = new Message();
            message.setType(Message.Type.PATIENT_CAN_LEAVE);
            message.setData("病人 " + save.getPatientId() + " 可以出院");
            message.setTargetId(userRepository.findByDistrictIdAndRoleEquals(patient.getDistrictId(), User.roleType.DOCTOR).get(0).getId());
            messageService.addMessage(message);
        }
        return save;
    }
}
