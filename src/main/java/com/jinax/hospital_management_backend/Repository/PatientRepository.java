package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.District;
import com.jinax.hospital_management_backend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : chara
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    public List<Patient> findAllByNurseId(long nurseId);

    @Query(value = "select id from patient_with_state_and_level where IF(ISNULL(?1),1=1,district_id = ?1) and IF(ISNULL(?2),1=1,level =:#{#level==null?null:#level.toString()}) and IF(ISNULL(?3),1=1,state = :#{#state==null?null:#state.toString()})",nativeQuery = true)
//    @Query(value = "select t.id from PatientWithStateAndLevel t where (?1 is null or t.districtId = ?1) and (?2 is null or t.level =:#{#level.toString()}) and (?3 is null or t.state = ?3)")
    public List<Long> findAllByDistrictIdAndLevelAndState(Long districtId,@Param("level") Patient.Level level,@Param("state")DailyReport.State state);

    @Query(value = "select * from patients_with_three_good_temperature",nativeQuery = true)
    public List<Long> findAllWithThreeGoodTemperature();

    @Query(value = "select * from patients_with_two_negative_tests",nativeQuery = true)
    public List<Long> findAllWithTwoNegativeTests();

    @Query(value = "select d.type from patient left join district d on patient.district_id = d.ID",nativeQuery = true)
    public Optional<District.Type> getDistrictOfPatient(long patientId);

    @Query(value = "select patient.ID, name, identification, location, level, district_id, nurse_id, bed_id, initial_test_id " +
            "from patient left join district d on patient.district_id = d.ID " +
            "where d.type = 'ISOLATION' and patient.level = :#{#level.toString()}"
            ,nativeQuery = true)
    public List<Patient> getPatientInIsolationWithLevel(@Param("level") Patient.Level level);

}
