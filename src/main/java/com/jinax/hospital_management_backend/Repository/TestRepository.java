package com.jinax.hospital_management_backend.Repository;

import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chara
 */
@Repository
public interface TestRepository extends JpaRepository<Test,Long> {
    @Query(value = "(select test.id, result, test_time, level,test.patient_id from test left join daily_report on test.Id = daily_report.test_id where daily_report.patient_id = ?1)" +
            "UNION (select test.id, result, test_time, test.level,patient_id from test left join patient on initial_test_id = test.Id where patient.ID = ?1)",nativeQuery = true)
    List<Test> findAllTestsByPatientId(long patientId);
}
