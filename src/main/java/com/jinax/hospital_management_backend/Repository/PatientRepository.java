package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.District;
import com.jinax.hospital_management_backend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    public List<Patient> findAllByNurseId(long nurseId);

    @Query(value = "select ID from patient_with_state_and_level where if(?1 = '',1=1,district_id = ?1) and if(?2='',1=1,level = ?2) and if(?3 = '',1=1,level = ?3)",nativeQuery = true)
    public List<Long> findAllByDistrictIdAndLevelAndState(long districtId, Patient.Level level, DailyReport.State state);

    @Query(value = "select * from patients_with_three_good_temperature",nativeQuery = true)
    public List<Long> findAllWithThreeGoodTemperature();

    @Query(value = "select * from patients_with_two_negative_tests",nativeQuery = true)
    public List<Long> findAllWithTwoNegativeTests();

    @Query(value = "select d.type from patient left join district d on patient.district_id = d.ID",nativeQuery = true)
    public Optional<District.Type> getDistrictOfPatient(long patientId);

}
