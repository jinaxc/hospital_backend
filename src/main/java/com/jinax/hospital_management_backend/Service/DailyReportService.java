package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.Patient;
import com.jinax.hospital_management_backend.Exception.DailyNotExistedException;
import com.jinax.hospital_management_backend.Exception.ReleasePatientNotInMinorException;
import com.jinax.hospital_management_backend.Repository.DailyReportRepository;
import com.jinax.hospital_management_backend.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class DailyReportService {
    private final DailyReportRepository reportRepository;
    private final PatientRepository patientRepository;
    private final PatientService patientService;

    public DailyReportService(DailyReportRepository reportRepository, PatientRepository patientRepository, PatientService patientService) {
        this.reportRepository = reportRepository;
        this.patientRepository = patientRepository;
        this.patientService = patientService;
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
            // must set the patient info
            Patient patient = patientRepository.findById(patientId).get();
            patient.setNurseId(null);
            patient.setDistrictId(null);
            patient.setBedId(null);
            patientRepository.save(patient);
            return reportRepository.save(d);
        }else if(level != DailyReport.State.OUT.getCode() ){
            d.setState(DailyReport.State.getState(level));
            return reportRepository.save(d);
        }
        else{
            throw new ReleasePatientNotInMinorException("try to release patient not in MINOR");
        }

    }

    public DailyReport addDailyReport(DailyReport dailyReport){
        dailyReport.setId(null);
        return reportRepository.save(dailyReport);
    }
}
